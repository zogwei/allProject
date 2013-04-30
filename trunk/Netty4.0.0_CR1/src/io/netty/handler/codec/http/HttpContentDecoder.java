/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package io.netty.handler.codec.http;

import io.netty.buffer.BufUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.embedded.EmbeddedByteChannel;
import io.netty.handler.codec.MessageToMessageDecoder;

/**
 * Decodes the content of the received {@link HttpRequest} and {@link HttpContent}.
 * The original content is replaced with the new content decoded by the
 * {@link EmbeddedByteChannel}, which is created by {@link #newContentDecoder(String)}.
 * Once decoding is finished, the value of the <tt>'Content-Encoding'</tt>
 * header is set to the target content encoding, as returned by {@link #getTargetContentEncoding(String)}.
 * Also, the <tt>'Content-Length'</tt> header is updated to the length of the
 * decoded content.  If the content encoding of the original is not supported
 * by the decoder, {@link #newContentDecoder(String)} should return {@code null}
 * so that no decoding occurs (i.e. pass-through).
 * <p>
 * Please note that this is an abstract class.  You have to extend this class
 * and implement {@link #newContentDecoder(String)} properly to make this class
 * functional.  For example, refer to the source code of {@link HttpContentDecompressor}.
 * <p>
 * This handler must be placed after {@link HttpObjectDecoder} in the pipeline
 * so that this handler can intercept HTTP requests after {@link HttpObjectDecoder}
 * converts {@link ByteBuf}s into HTTP requests.
 */
public abstract class HttpContentDecoder extends MessageToMessageDecoder<HttpObject> {

    private EmbeddedByteChannel decoder;
    private HttpMessage message;
    private boolean decodeStarted;
    private boolean continueResponse;

    @Override
    protected Object decode(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        if (msg instanceof HttpResponse && ((HttpResponse) msg).getStatus().code() == 100) {
            // 100-continue response must be passed through.
            BufUtil.retain(msg);
            if (!(msg instanceof LastHttpContent)) {
                continueResponse = true;
            }
            return msg;
        }

        if (continueResponse) {
            if (msg instanceof LastHttpContent) {
                continueResponse = false;
            }
            // 100-continue response must be passed through.
            BufUtil.retain(msg);
            return msg;
        }

        if (msg instanceof HttpMessage) {
            assert message == null;
            message = (HttpMessage) msg;
            decodeStarted = false;
            cleanup();
        }

        if (msg instanceof HttpContent) {
            final HttpContent c = (HttpContent) msg;

            if (!decodeStarted) {
                decodeStarted = true;
                HttpMessage message = this.message;
                HttpHeaders headers = message.headers();
                this.message = null;

                // Determine the content encoding.
                String contentEncoding = headers.get(HttpHeaders.Names.CONTENT_ENCODING);
                if (contentEncoding != null) {
                    contentEncoding = contentEncoding.trim();
                } else {
                    contentEncoding = HttpHeaders.Values.IDENTITY;
                }

                if ((decoder = newContentDecoder(contentEncoding)) != null) {
                    // Decode the content and remove or replace the existing headers
                    // so that the message looks like a decoded message.
                    String targetContentEncoding = getTargetContentEncoding(contentEncoding);
                    if (HttpHeaders.Values.IDENTITY.equals(targetContentEncoding)) {
                        // Do NOT set the 'Content-Encoding' header if the target encoding is 'identity'
                        // as per: http://tools.ietf.org/html/rfc2616#section-14.11
                        headers.remove(HttpHeaders.Names.CONTENT_ENCODING);
                    } else {
                        headers.set(HttpHeaders.Names.CONTENT_ENCODING, targetContentEncoding);
                    }
                    Object[] decoded = decodeContent(message, c);

                    // Replace the content.
                    if (headers.contains(HttpHeaders.Names.CONTENT_LENGTH)) {
                        headers.set(
                                HttpHeaders.Names.CONTENT_LENGTH,
                                Integer.toString(((ByteBufHolder) decoded[1]).data().readableBytes()));
                    }
                    return decoded;
                }

                return new Object[] { message, c.retain() };
            }

            if (decoder != null) {
                return decodeContent(null, c);
            } else {
                return c.retain();
            }
        }

        return null;
    }

    private Object[] decodeContent(HttpMessage header, HttpContent c) {
        ByteBuf newContent = Unpooled.buffer();
        ByteBuf content = c.data();
        decode(content, newContent);

        if (c instanceof LastHttpContent) {
            ByteBuf lastProduct = Unpooled.buffer();
            finishDecode(lastProduct);

            // Generate an additional chunk if the decoder produced
            // the last product on closure,
            if (lastProduct.isReadable()) {
                if (header == null) {
                    return new Object[] { new DefaultHttpContent(newContent), new DefaultLastHttpContent(lastProduct)};
                } else {
                    return new Object[] { header,  new DefaultHttpContent(newContent),
                            new DefaultLastHttpContent(lastProduct)};
                }
            } else {
                if (header == null) {
                    return new Object[] { new DefaultLastHttpContent(newContent) };
                } else {
                    return new Object[] { header, new DefaultLastHttpContent(newContent) };
                }
            }
        }
        if (header == null) {
            return new Object[] { new DefaultHttpContent(newContent) };
        } else {
            return new Object[] { header, new DefaultHttpContent(newContent) };
        }
    }

    /**
     * Returns a new {@link EmbeddedByteChannel} that decodes the HTTP message
     * content encoded in the specified <tt>contentEncoding</tt>.
     *
     * @param contentEncoding the value of the {@code "Content-Encoding"} header
     * @return a new {@link EmbeddedByteChannel} if the specified encoding is supported.
     *         {@code null} otherwise (alternatively, you can throw an exception
     *         to block unknown encoding).
     */
    protected abstract EmbeddedByteChannel newContentDecoder(String contentEncoding) throws Exception;

    /**
     * Returns the expected content encoding of the decoded content.
     * This getMethod returns {@code "identity"} by default, which is the case for
     * most decoders.
     *
     * @param contentEncoding the value of the {@code "Content-Encoding"} header
     * @return the expected content encoding of the new content
     */
    @SuppressWarnings("unused")
    protected String getTargetContentEncoding(String contentEncoding) throws Exception {
        return HttpHeaders.Values.IDENTITY;
    }

    @Override
    public void afterRemove(ChannelHandlerContext ctx) throws Exception {
        cleanup();
        super.afterRemove(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        cleanup();
        super.channelInactive(ctx);
    }

    private void cleanup() {
        if (decoder != null) {
            // Clean-up the previous decoder if not cleaned up correctly.
            finishDecode(Unpooled.buffer());
        }
    }

    private void decode(ByteBuf in, ByteBuf out) {
        decoder.writeInbound(in);
        fetchDecoderOutput(out);
    }

    private void finishDecode(ByteBuf out) {
        if (decoder.finish()) {
            fetchDecoderOutput(out);
        }
        decoder = null;
    }

    private void fetchDecoderOutput(ByteBuf out) {
        for (;;) {
            ByteBuf buf = (ByteBuf) decoder.readInbound();
            if (buf == null) {
                break;
            }
            out.writeBytes(buf);
        }
    }
}
