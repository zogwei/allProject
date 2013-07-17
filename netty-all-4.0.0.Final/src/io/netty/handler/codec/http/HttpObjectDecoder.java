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

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.ReplayingDecoder;
import io.netty.handler.codec.TooLongFrameException;

import java.util.List;

/**
 * Decodes {@link ByteBuf}s into {@link HttpMessage}s and
 * {@link HttpContent}s.
 *
 * <h3>Parameters that prevents excessive memory consumption</h3>
 * <table border="1">
 * <tr>
 * <th>Name</th><th>Meaning</th>
 * </tr>
 * <tr>
 * <td>{@code maxInitialLineLength}</td>
 * <td>The maximum length of the initial line
 *     (e.g. {@code "GET / HTTP/1.0"} or {@code "HTTP/1.0 200 OK"})
 *     If the length of the initial line exceeds this value, a
 *     {@link TooLongFrameException} will be raised.</td>
 * </tr>
 * <tr>
 * <td>{@code maxHeaderSize}</td>
 * <td>The maximum length of all headers.  If the sum of the length of each
 *     header exceeds this value, a {@link TooLongFrameException} will be raised.</td>
 * </tr>
 * <tr>
 * <td>{@code maxChunkSize}</td>
 * <td>The maximum length of the content or each chunk.  If the content length
 *     (or the length of each chunk) exceeds this value, the content or chunk
 *     will be split into multiple {@link HttpContent}s whose length is
 *     {@code maxChunkSize} at maximum.</td>
 * </tr>
 * </table>
 *
 * <h3>Chunked Content</h3>
 *
 * If the content of an HTTP message is greater than {@code maxChunkSize} or
 * the transfer encoding of the HTTP message is 'chunked', this decoder
 * generates one {@link HttpMessage} instance and its following
 * {@link HttpContent}s per single HTTP message to avoid excessive memory
 * consumption. For example, the following HTTP message:
 * <pre>
 * GET / HTTP/1.1
 * Transfer-Encoding: chunked
 *
 * 1a
 * abcdefghijklmnopqrstuvwxyz
 * 10
 * 1234567890abcdef
 * 0
 * Content-MD5: ...
 * <i>[blank line]</i>
 * </pre>
 * triggers {@link HttpRequestDecoder} to generate 3 objects:
 * <ol>
 * <li>An {@link HttpRequest},</li>
 * <li>The first {@link HttpContent} whose content is {@code 'abcdefghijklmnopqrstuvwxyz'},</li>
 * <li>The second {@link LastHttpContent} whose content is {@code '1234567890abcdef'}, which marks
 * the end of the content.</li>
 * </ol>
 *
 * If you prefer not to handle {@link HttpContent}s by yourself for your
 * convenience, insert {@link HttpObjectAggregator} after this decoder in the
 * {@link ChannelPipeline}.  However, please note that your server might not
 * be as memory efficient as without the aggregator.
 *
 * <h3>Extensibility</h3>
 *
 * Please note that this decoder is designed to be extended to implement
 * a protocol derived from HTTP, such as
 * <a href="http://en.wikipedia.org/wiki/Real_Time_Streaming_Protocol">RTSP</a> and
 * <a href="http://en.wikipedia.org/wiki/Internet_Content_Adaptation_Protocol">ICAP</a>.
 * To implement the decoder of such a derived protocol, extend this class and
 * implement all abstract methods properly.
 */
public abstract class HttpObjectDecoder extends ReplayingDecoder<HttpObjectDecoder.State> {

    private final int maxInitialLineLength;
    private final int maxHeaderSize;
    private final int maxChunkSize;
    private final boolean chunkedSupported;
    private ByteBuf content;
    private HttpMessage message;
    private long chunkSize;
    private int headerSize;
    private int contentRead;

    /**
     * The internal state of {@link HttpObjectDecoder}.
     * <em>Internal use only</em>.
     */
    enum State {
        SKIP_CONTROL_CHARS,
        READ_INITIAL,
        READ_HEADER,
        READ_VARIABLE_LENGTH_CONTENT,
        READ_VARIABLE_LENGTH_CONTENT_AS_CHUNKS,
        READ_FIXED_LENGTH_CONTENT,
        READ_FIXED_LENGTH_CONTENT_AS_CHUNKS,
        READ_CHUNK_SIZE,
        READ_CHUNKED_CONTENT,
        READ_CHUNKED_CONTENT_AS_CHUNKS,
        READ_CHUNK_DELIMITER,
        READ_CHUNK_FOOTER,
        BAD_MESSAGE
    }

    /**
     * Creates a new instance with the default
     * {@code maxInitialLineLength (4096}}, {@code maxHeaderSize (8192)}, and
     * {@code maxChunkSize (8192)}.
     */
    protected HttpObjectDecoder() {
        this(4096, 8192, 8192, true);
    }

    /**
     * Creates a new instance with the specified parameters.
     */
    protected HttpObjectDecoder(
            int maxInitialLineLength, int maxHeaderSize, int maxChunkSize, boolean chunkedSupported) {

        super(State.SKIP_CONTROL_CHARS);

        if (maxInitialLineLength <= 0) {
            throw new IllegalArgumentException(
                    "maxInitialLineLength must be a positive integer: " +
                    maxInitialLineLength);
        }
        if (maxHeaderSize <= 0) {
            throw new IllegalArgumentException(
                    "maxHeaderSize must be a positive integer: " +
                    maxHeaderSize);
        }
        if (maxChunkSize < 0) {
            throw new IllegalArgumentException(
                    "maxChunkSize must be a positive integer: " +
                    maxChunkSize);
        }
        this.maxInitialLineLength = maxInitialLineLength;
        this.maxHeaderSize = maxHeaderSize;
        this.maxChunkSize = maxChunkSize;
        this.chunkedSupported = chunkedSupported;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buffer, List<Object> out) throws Exception {
        switch (state()) {
        case SKIP_CONTROL_CHARS: {
            try {
                skipControlCharacters(buffer);
                checkpoint(State.READ_INITIAL);
            } finally {
                checkpoint();
            }
        }
        case READ_INITIAL: try {
            String[] initialLine = splitInitialLine(readLine(buffer, maxInitialLineLength));
            if (initialLine.length < 3) {
                // Invalid initial line - ignore.
                checkpoint(State.SKIP_CONTROL_CHARS);
                return;
            }

            message = createMessage(initialLine);
            checkpoint(State.READ_HEADER);

        } catch (Exception e) {
            out.add(invalidMessage(e));
            return;
        }
        case READ_HEADER: try {
            State nextState = readHeaders(buffer);
            checkpoint(nextState);
            if (nextState == State.READ_CHUNK_SIZE) {
                if (!chunkedSupported) {
                    throw new IllegalArgumentException("Chunked messages not supported");
                }
                // Chunked encoding - generate HttpMessage first.  HttpChunks will follow.
                out.add(message);
                return;
            }
            if (nextState == State.SKIP_CONTROL_CHARS) {
                // No content is expected.
                reset(out);
                return;
            }
            long contentLength = HttpHeaders.getContentLength(message, -1);
            if (contentLength == 0 || contentLength == -1 && isDecodingRequest()) {
                content = Unpooled.EMPTY_BUFFER;
                reset(out);
                return;
            }

            switch (nextState) {
            case READ_FIXED_LENGTH_CONTENT:
                if (contentLength > maxChunkSize || HttpHeaders.is100ContinueExpected(message)) {
                    // Generate FullHttpMessage first.  HttpChunks will follow.
                    checkpoint(State.READ_FIXED_LENGTH_CONTENT_AS_CHUNKS);
                    // chunkSize will be decreased as the READ_FIXED_LENGTH_CONTENT_AS_CHUNKS
                    // state reads data chunk by chunk.
                    chunkSize = HttpHeaders.getContentLength(message, -1);
                    out.add(message);
                    return;
                }
                break;
            case READ_VARIABLE_LENGTH_CONTENT:
                if (buffer.readableBytes() > maxChunkSize || HttpHeaders.is100ContinueExpected(message)) {
                    // Generate FullHttpMessage first.  HttpChunks will follow.
                    checkpoint(State.READ_VARIABLE_LENGTH_CONTENT_AS_CHUNKS);
                    out.add(message);
                    return;
                }
                break;
            default:
                throw new IllegalStateException("Unexpected state: " + nextState);
            }
            // We return here, this forces decode to be called again where we will decode the content
            return;
        } catch (Exception e) {
            out.add(invalidMessage(e));
            return;
        }
        case READ_VARIABLE_LENGTH_CONTENT: {
            int toRead = actualReadableBytes();
            if (toRead > maxChunkSize) {
                toRead = maxChunkSize;
            }
            out.add(message);
            out.add(new DefaultHttpContent(buffer.readBytes(toRead)));
            return;
        }
        case READ_VARIABLE_LENGTH_CONTENT_AS_CHUNKS: {
            // Keep reading data as a chunk until the end of connection is reached.
            int toRead = actualReadableBytes();
            if (toRead > maxChunkSize) {
                toRead = maxChunkSize;
            }
            ByteBuf content = buffer.readBytes(toRead);
            if (!buffer.isReadable()) {
                reset();
                out.add(new DefaultLastHttpContent(content));
                return;
            }
            out.add(new DefaultHttpContent(content));
            return;
        }
        case READ_FIXED_LENGTH_CONTENT: {
            readFixedLengthContent(buffer, out);
            return;
        }
        case READ_FIXED_LENGTH_CONTENT_AS_CHUNKS: {
            long chunkSize = this.chunkSize;
            int readLimit = actualReadableBytes();

            // Check if the buffer is readable first as we use the readable byte count
            // to create the HttpChunk. This is needed as otherwise we may end up with
            // create a HttpChunk instance that contains an empty buffer and so is
            // handled like it is the last HttpChunk.
            //
            // See https://github.com/netty/netty/issues/433
            if (readLimit == 0) {
                return;
            }

            int toRead = readLimit;
            if (toRead > maxChunkSize) {
                toRead = maxChunkSize;
            }
            if (toRead > chunkSize) {
                toRead = (int) chunkSize;
            }
            ByteBuf content = buffer.readBytes(toRead);
            if (chunkSize > toRead) {
                chunkSize -= toRead;
            } else {
                chunkSize = 0;
            }
            this.chunkSize = chunkSize;

            if (chunkSize == 0) {
                // Read all content.
                reset();
                out.add(new DefaultLastHttpContent(content));
                return;
            }
            out.add(new DefaultHttpContent(content));
            return;
        }
        /**
         * everything else after this point takes care of reading chunked content. basically, read chunk size,
         * read chunk, read and ignore the CRLF and repeat until 0
         */
        case READ_CHUNK_SIZE: try {
            String line = readLine(buffer, maxInitialLineLength);
            int chunkSize = getChunkSize(line);
            this.chunkSize = chunkSize;
            if (chunkSize == 0) {
                checkpoint(State.READ_CHUNK_FOOTER);
                return;
            } else if (chunkSize > maxChunkSize) {
                // A chunk is too large. Split them into multiple chunks again.
                checkpoint(State.READ_CHUNKED_CONTENT_AS_CHUNKS);
            } else {
                checkpoint(State.READ_CHUNKED_CONTENT);
            }
        } catch (Exception e) {
            out.add(invalidChunk(e));
            return;
        }
        case READ_CHUNKED_CONTENT: {
            assert chunkSize <= Integer.MAX_VALUE;
            HttpContent chunk = new DefaultHttpContent(buffer.readBytes((int) chunkSize));
            checkpoint(State.READ_CHUNK_DELIMITER);
            out.add(chunk);
            return;
        }
        case READ_CHUNKED_CONTENT_AS_CHUNKS: {
            assert chunkSize <= Integer.MAX_VALUE;
            int chunkSize = (int) this.chunkSize;
            int readLimit = actualReadableBytes();

            // Check if the buffer is readable first as we use the readable byte count
            // to create the HttpChunk. This is needed as otherwise we may end up with
            // create a HttpChunk instance that contains an empty buffer and so is
            // handled like it is the last HttpChunk.
            //
            // See https://github.com/netty/netty/issues/433
            if (readLimit == 0) {
                return;
            }

            int toRead = chunkSize;
            if (toRead > maxChunkSize) {
                toRead = maxChunkSize;
            }
            if (toRead > readLimit) {
                toRead = readLimit;
            }
            HttpContent chunk = new DefaultHttpContent(buffer.readBytes(toRead));
            if (chunkSize > toRead) {
                chunkSize -= toRead;
            } else {
                chunkSize = 0;
            }
            this.chunkSize = chunkSize;

            if (chunkSize == 0) {
                // Read all content.
                checkpoint(State.READ_CHUNK_DELIMITER);
            }

            out.add(chunk);
            return;
        }
        case READ_CHUNK_DELIMITER: {
            for (;;) {
                byte next = buffer.readByte();
                if (next == HttpConstants.CR) {
                    if (buffer.readByte() == HttpConstants.LF) {
                        checkpoint(State.READ_CHUNK_SIZE);
                        return;
                    }
                } else if (next == HttpConstants.LF) {
                    checkpoint(State.READ_CHUNK_SIZE);
                    return;
                } else {
                    checkpoint();
                }
            }
        }
        case READ_CHUNK_FOOTER: try {
            LastHttpContent trailer = readTrailingHeaders(buffer);
            if (maxChunkSize == 0) {
                // Chunked encoding disabled.
                reset(out);
                return;
            } else {
                reset();
                // The last chunk, which is empty
                out.add(trailer);
                return;
            }
        } catch (Exception e) {
            out.add(invalidChunk(e));
            return;
        }
        case BAD_MESSAGE: {
            // Keep discarding until disconnection.
            buffer.skipBytes(actualReadableBytes());
            return;
        }
        default: {
            throw new Error("Shouldn't reach here.");
        }
        }
    }

    @Override
    protected void decodeLast(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        decode(ctx, in, out);

        // Handle the last unfinished message.
        if (message != null) {
            // Get the length of the content received so far for the last message.
            HttpMessage message = this.message;
            int actualContentLength;
            if (content != null) {
                actualContentLength = content.readableBytes();
            } else {
                actualContentLength = 0;
            }

            // Check if the closure of the connection signifies the end of the content.
            boolean prematureClosure;
            if (isDecodingRequest()) {
                // The last request did not wait for a response.
                prematureClosure = true;
            } else {
                // Compare the length of the received content and the 'Content-Length' header.
                // If the 'Content-Length' header is absent, the length of the content is determined by the end of the
                // connection, so it is perfectly fine.
                long expectedContentLength = HttpHeaders.getContentLength(message, -1);
                prematureClosure = expectedContentLength >= 0 && actualContentLength != expectedContentLength;
            }

            if (!prematureClosure) {
                if (actualContentLength == 0) {
                    out.add(LastHttpContent.EMPTY_LAST_CONTENT);
                } else {
                    out.add(new DefaultLastHttpContent(content));
                }
            }
        }
    }

    protected boolean isContentAlwaysEmpty(HttpMessage msg) {
        if (msg instanceof HttpResponse) {
            HttpResponse res = (HttpResponse) msg;
            int code = res.getStatus().code();

            // Correctly handle return codes of 1xx.
            //
            // See:
            //     - http://www.w3.org/Protocols/rfc2616/rfc2616-sec4.html Section 4.4
            //     - https://github.com/netty/netty/issues/222
            if (code >= 100 && code < 200) {
                if (code == 101 && !res.headers().contains(HttpHeaders.Names.SEC_WEBSOCKET_ACCEPT)) {
                    // It's Hixie 76 websocket handshake response
                    return false;
                }
                return true;
            }

            switch (code) {
            case 204: case 205: case 304:
                return true;
            }
        }
        return false;
    }

    private void reset() {
        reset(null);
    }

    private void reset(List<Object> out) {
        if (out != null) {
            HttpMessage message = this.message;
            ByteBuf content = this.content;
            LastHttpContent httpContent;

            if (content == null || !content.isReadable()) {
                httpContent = LastHttpContent.EMPTY_LAST_CONTENT;
            } else {
                httpContent = new DefaultLastHttpContent(content);
            }

            out.add(message);
            out.add(httpContent);
        }

        content = null;
        message = null;

        checkpoint(State.SKIP_CONTROL_CHARS);
    }

    private HttpMessage invalidMessage(Exception cause) {
        checkpoint(State.BAD_MESSAGE);
        if (message != null) {
            message.setDecoderResult(DecoderResult.failure(cause));
        } else {
            message = createInvalidMessage();
            message.setDecoderResult(DecoderResult.failure(cause));
        }
        return message;
    }

    private HttpContent invalidChunk(Exception cause) {
        checkpoint(State.BAD_MESSAGE);
        HttpContent chunk = new DefaultHttpContent(Unpooled.EMPTY_BUFFER);
        chunk.setDecoderResult(DecoderResult.failure(cause));
        return chunk;
    }

    private static void skipControlCharacters(ByteBuf buffer) {
        for (;;) {
            char c = (char) buffer.readUnsignedByte();
            if (!Character.isISOControl(c) &&
                !Character.isWhitespace(c)) {
                buffer.readerIndex(buffer.readerIndex() - 1);
                break;
            }
        }
    }

    private void readFixedLengthContent(ByteBuf buffer, List<Object> out) {
        //we have a content-length so we just read the correct number of bytes
        long length = HttpHeaders.getContentLength(message, -1);
        assert length <= Integer.MAX_VALUE;
        int toRead = (int) length - contentRead;
        if (toRead > actualReadableBytes()) {
            toRead = actualReadableBytes();
        }
        contentRead += toRead;
        if (length < contentRead) {
            out.add(message);
            out.add(new DefaultHttpContent(buffer.readBytes(toRead)));
            return;
        }
        if (content == null) {
            content = buffer.readBytes((int) length);
        } else {
            content.writeBytes(buffer, (int) length);
        }
        reset(out);
    }

    private State readHeaders(ByteBuf buffer) {
        headerSize = 0;
        final HttpMessage message = this.message;
        final HttpHeaders headers = message.headers();

        String line = readHeader(buffer);
        String name = null;
        String value = null;
        if (!line.isEmpty()) {
            headers.clear();
            do {
                char firstChar = line.charAt(0);
                if (name != null && (firstChar == ' ' || firstChar == '\t')) {
                    value = value + ' ' + line.trim();
                } else {
                    if (name != null) {
                        headers.add(name, value);
                    }
                    String[] header = splitHeader(line);
                    name = header[0];
                    value = header[1];
                }

                line = readHeader(buffer);
            } while (!line.isEmpty());

            // Add the last header.
            if (name != null) {
                headers.add(name, value);
            }
        }

        State nextState;

        if (isContentAlwaysEmpty(message)) {
            HttpHeaders.removeTransferEncodingChunked(message);
            nextState = State.SKIP_CONTROL_CHARS;
        } else if (HttpHeaders.isTransferEncodingChunked(message)) {
            nextState = State.READ_CHUNK_SIZE;
        } else if (HttpHeaders.getContentLength(message, -1) >= 0) {
            nextState = State.READ_FIXED_LENGTH_CONTENT;
        } else {
            nextState = State.READ_VARIABLE_LENGTH_CONTENT;
        }
        return nextState;
    }

    private LastHttpContent readTrailingHeaders(ByteBuf buffer) {
        headerSize = 0;
        String line = readHeader(buffer);
        String lastHeader = null;
        if (!line.isEmpty()) {
            LastHttpContent trailer = new DefaultLastHttpContent(Unpooled.EMPTY_BUFFER);
            do {
                char firstChar = line.charAt(0);
                if (lastHeader != null && (firstChar == ' ' || firstChar == '\t')) {
                    List<String> current = trailer.trailingHeaders().getAll(lastHeader);
                    if (!current.isEmpty()) {
                        int lastPos = current.size() - 1;
                        String newString = current.get(lastPos) + line.trim();
                        current.set(lastPos, newString);
                    } else {
                        // Content-Length, Transfer-Encoding, or Trailer
                    }
                } else {
                    String[] header = splitHeader(line);
                    String name = header[0];
                    if (!name.equalsIgnoreCase(HttpHeaders.Names.CONTENT_LENGTH) &&
                        !name.equalsIgnoreCase(HttpHeaders.Names.TRANSFER_ENCODING) &&
                        !name.equalsIgnoreCase(HttpHeaders.Names.TRAILER)) {
                        trailer.trailingHeaders().add(name, header[1]);
                    }
                    lastHeader = name;
                }

                line = readHeader(buffer);
            } while (!line.isEmpty());

            return trailer;
        }

        return LastHttpContent.EMPTY_LAST_CONTENT;
    }

    private String readHeader(ByteBuf buffer) {
        StringBuilder sb = new StringBuilder(64);
        int headerSize = this.headerSize;

        loop:
        for (;;) {
            char nextByte = (char) buffer.readByte();
            headerSize ++;

            switch (nextByte) {
            case HttpConstants.CR:
                nextByte = (char) buffer.readByte();
                headerSize ++;
                if (nextByte == HttpConstants.LF) {
                    break loop;
                }
                break;
            case HttpConstants.LF:
                break loop;
            }

            // Abort decoding if the header part is too large.
            if (headerSize >= maxHeaderSize) {
                // TODO: Respond with Bad Request and discard the traffic
                //    or close the connection.
                //       No need to notify the upstream handlers - just log.
                //       If decoding a response, just throw an exception.
                throw new TooLongFrameException(
                        "HTTP header is larger than " +
                        maxHeaderSize + " bytes.");
            }

            sb.append(nextByte);
        }

        this.headerSize = headerSize;
        return sb.toString();
    }

    protected abstract boolean isDecodingRequest();
    protected abstract HttpMessage createMessage(String[] initialLine) throws Exception;
    protected abstract HttpMessage createInvalidMessage();

    private static int getChunkSize(String hex) {
        hex = hex.trim();
        for (int i = 0; i < hex.length(); i ++) {
            char c = hex.charAt(i);
            if (c == ';' || Character.isWhitespace(c) || Character.isISOControl(c)) {
                hex = hex.substring(0, i);
                break;
            }
        }

        return Integer.parseInt(hex, 16);
    }

    private static String readLine(ByteBuf buffer, int maxLineLength) {
        StringBuilder sb = new StringBuilder(64);
        int lineLength = 0;
        while (true) {
            byte nextByte = buffer.readByte();
            if (nextByte == HttpConstants.CR) {
                nextByte = buffer.readByte();
                if (nextByte == HttpConstants.LF) {
                    return sb.toString();
                }
            } else if (nextByte == HttpConstants.LF) {
                return sb.toString();
            } else {
                if (lineLength >= maxLineLength) {
                    // TODO: Respond with Bad Request and discard the traffic
                    //    or close the connection.
                    //       No need to notify the upstream handlers - just log.
                    //       If decoding a response, just throw an exception.
                    throw new TooLongFrameException(
                            "An HTTP line is larger than " + maxLineLength +
                            " bytes.");
                }
                lineLength ++;
                sb.append((char) nextByte);
            }
        }
    }

    private static String[] splitInitialLine(String sb) {
        int aStart;
        int aEnd;
        int bStart;
        int bEnd;
        int cStart;
        int cEnd;

        aStart = findNonWhitespace(sb, 0);
        aEnd = findWhitespace(sb, aStart);

        bStart = findNonWhitespace(sb, aEnd);
        bEnd = findWhitespace(sb, bStart);

        cStart = findNonWhitespace(sb, bEnd);
        cEnd = findEndOfString(sb);

        return new String[] {
                sb.substring(aStart, aEnd),
                sb.substring(bStart, bEnd),
                cStart < cEnd? sb.substring(cStart, cEnd) : "" };
    }

    private static String[] splitHeader(String sb) {
        final int length = sb.length();
        int nameStart;
        int nameEnd;
        int colonEnd;
        int valueStart;
        int valueEnd;

        nameStart = findNonWhitespace(sb, 0);
        for (nameEnd = nameStart; nameEnd < length; nameEnd ++) {
            char ch = sb.charAt(nameEnd);
            if (ch == ':' || Character.isWhitespace(ch)) {
                break;
            }
        }

        for (colonEnd = nameEnd; colonEnd < length; colonEnd ++) {
            if (sb.charAt(colonEnd) == ':') {
                colonEnd ++;
                break;
            }
        }

        valueStart = findNonWhitespace(sb, colonEnd);
        if (valueStart == length) {
            return new String[] {
                    sb.substring(nameStart, nameEnd),
                    ""
            };
        }

        valueEnd = findEndOfString(sb);
        return new String[] {
                sb.substring(nameStart, nameEnd),
                sb.substring(valueStart, valueEnd)
        };
    }

    private static int findNonWhitespace(String sb, int offset) {
        int result;
        for (result = offset; result < sb.length(); result ++) {
            if (!Character.isWhitespace(sb.charAt(result))) {
                break;
            }
        }
        return result;
    }

    private static int findWhitespace(String sb, int offset) {
        int result;
        for (result = offset; result < sb.length(); result ++) {
            if (Character.isWhitespace(sb.charAt(result))) {
                break;
            }
        }
        return result;
    }

    private static int findEndOfString(String sb) {
        int result;
        for (result = sb.length(); result > 0; result --) {
            if (!Character.isWhitespace(sb.charAt(result - 1))) {
                break;
            }
        }
        return result;
    }
}
