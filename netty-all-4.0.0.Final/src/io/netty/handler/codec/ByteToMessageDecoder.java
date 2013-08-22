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
package io.netty.handler.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.internal.RecyclableArrayList;
import io.netty.util.internal.StringUtil;

import java.util.List;

/**
 * {@link ChannelInboundHandlerAdapter} which decodes bytes in a stream-like fashion from one {@link ByteBuf} to an
 * other Message type.
 *
 * For example here is an implementation which reads all readable bytes from
 * the input {@link ByteBuf} and create a new {@link ByteBuf}.
 *
 * <pre>
 *     public class SquareDecoder extends {@link ByteToMessageDecoder} {
 *         {@code @Override}
 *         public void decode({@link ChannelHandlerContext} ctx, {@link ByteBuf} in, List&lt;Object&gt; out)
 *                 throws {@link Exception} {
 *             out.add(in.readBytes(in.readableBytes()));
 *         }
 *     }
 * </pre>
 *
 * Be aware that sub-classes of {@link ByteToMessageDecoder} <strong>MUST NOT</strong>
 * annotated with {@link @Sharable}.
 */
public abstract class ByteToMessageDecoder extends ChannelInboundHandlerAdapter {

    ByteBuf cumulation;
    private boolean singleDecode;
    private boolean decodeWasNull;

    protected ByteToMessageDecoder() {
        if (getClass().isAnnotationPresent(Sharable.class)) {
            throw new IllegalStateException("@Sharable annotation is not allowed");
        }
    }

    /**
     * If set then only one message is decoded on each {@link #channelRead(ChannelHandlerContext, Object)}
     * call. This may be useful if you need to do some protocol upgrade and want to make sure nothing is mixed up.
     *
     * Default is {@code false} as this has performance impacts.
     */
    public void setSingleDecode(boolean singleDecode) {
        this.singleDecode = singleDecode;
    }

    /**
     * If {@code true} then only one message is decoded on each
     * {@link #channelRead(ChannelHandlerContext, Object)} call.
     *
     * Default is {@code false} as this has performance impacts.
     */
    public boolean isSingleDecode() {
        return singleDecode;
    }

    /**
     * Returns the actual number of readable bytes in the internal cumulative
     * buffer of this decoder. You usually do not need to rely on this value
     * to write a decoder. Use it only when you must use it at your own risk.
     * This method is a shortcut to {@link #internalBuffer() internalBuffer().readableBytes()}.
     */
    protected int actualReadableBytes() {
        return internalBuffer().readableBytes();
    }

    /**
     * Returns the internal cumulative buffer of this decoder. You usually
     * do not need to access the internal buffer directly to write a decoder.
     * Use it only when you must use it at your own risk.
     */
    protected ByteBuf internalBuffer() {
        if (cumulation != null) {
            return cumulation;
        } else {
            return Unpooled.EMPTY_BUFFER;
        }
    }

    @Override
    public final void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        ByteBuf buf = internalBuffer();
        int readable = buf.readableBytes();
        if (buf.isReadable()) {
            ByteBuf bytes = buf.readBytes(readable);
            buf.release();
            ctx.fireChannelRead(bytes);
        }
        cumulation = null;
        ctx.fireChannelReadComplete();
        handlerRemoved0(ctx);
    }

    /**
     * Gets called after the {@link ByteToMessageDecoder} was removed from the actual context and it doesn't handle
     * events anymore.
     */
    protected void handlerRemoved0(ChannelHandlerContext ctx) throws Exception { }

    /**
     * myOpinion NIOEventloop只负责不断的读取收到的字节，然后出发channelRead，该handler接收数据，使用cumulation基类以前读取的没有使用的byte，
     *           与NIOEventloop相关的线程处理该线程的所有工作，包括接收数据，后面的业务处理，故针对单个channel的数据接收，数据发送，是单线程的,业务处理是否单线程待定。
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        RecyclableArrayList out = RecyclableArrayList.newInstance();
        try {
            if (msg instanceof ByteBuf) {
                ByteBuf data = (ByteBuf) msg;
                if (cumulation == null) {
                    cumulation = data;
                    try {
                        callDecode(ctx, cumulation, out);
                    } finally {
                        if (cumulation != null && !cumulation.isReadable()) {
                            cumulation.release();
                            cumulation = null;
                        }
                    }
                } else {
                    try {
                        if (cumulation.writerIndex() > cumulation.maxCapacity() - data.readableBytes()) {
                            ByteBuf oldCumulation = cumulation;
                            cumulation = ctx.alloc().buffer(oldCumulation.readableBytes() + data.readableBytes());
                            cumulation.writeBytes(oldCumulation);
                            oldCumulation.release();
                        }
                        cumulation.writeBytes(data);
                        callDecode(ctx, cumulation, out);
                    } finally {
                        if (cumulation != null) {
                            if (!cumulation.isReadable()) {
                                cumulation.release();
                                cumulation = null;
                            } else {
                                cumulation.discardSomeReadBytes();
                            }
                        }
                        data.release();
                    }
                }
            } else {
                out.add(msg);
            }
        } catch (DecoderException e) {
            throw e;
        } catch (Throwable t) {
            throw new DecoderException(t);
        } finally {
            if (out.isEmpty()) {
                decodeWasNull = true;
            }

            for (int i = 0; i < out.size(); i ++) {
            	/**
            	 * myOpinion 接收inbound的数据，decode成多条msg，然后每条msg逐步的调用，后面的ctx中的方法
            	 * 			说明：
            	 *               1、每个handler处理一种或者多种事件，如read，inactive
            	 *               2、多个handler组成的事件处理链扩散处理事件，需要看各个handler如何去扩散事件
            	 *               	比如这里，接收一段数据，会产生多个逐条的由后面的handler处理的分支，
            	 *                      如果这里不调用ctx.fireXXX()等方法，事件处理链就断掉，后面的handler就不会处理
            	 *               3、整个事件处理链的数据传递，fire方法的入口就是数据的入口，经过处理后的结果作为下个handler的入口参数来传递数据，
            	 *                   例如这里的处理结果，out，就作为下面的handler的入口参数传递进去
            	 */
                ctx.fireChannelRead(out.get(i));
            }

            out.recycle();
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        if (decodeWasNull) {
            decodeWasNull = false;
            if (!ctx.channel().config().isAutoRead()) {
                ctx.read();
            }
        }
        ctx.fireChannelReadComplete();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        RecyclableArrayList out = RecyclableArrayList.newInstance();
        try {
            if (cumulation != null) {
                callDecode(ctx, cumulation, out);
                decodeLast(ctx, cumulation, out);
            } else {
                decodeLast(ctx, Unpooled.EMPTY_BUFFER, out);
            }
        } catch (DecoderException e) {
            throw e;
        } catch (Exception e) {
            throw new DecoderException(e);
        } finally {
            if (cumulation != null) {
                cumulation.release();
                cumulation = null;
            }

            for (int i = 0; i < out.size(); i ++) {
                ctx.fireChannelRead(out.get(i));
            }
            ctx.fireChannelInactive();
        }
    }

    /**
     * Called once data should be decoded from the given {@link ByteBuf}. This method will call
     * {@link #decode(ChannelHandlerContext, ByteBuf, List)} as long as decoding should take place.
     *
     * @param ctx           the {@link ChannelHandlerContext} which this {@link ByteToMessageDecoder} belongs to
     * @param in            the {@link ByteBuf} from which to read data
     * @param out           the {@link List} to which decoded messages should be added
     */
    protected void callDecode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        try {
            while (in.isReadable()) {
                int outSize = out.size();
                int oldInputLength = in.readableBytes();
                decode(ctx, in, out);
                if (outSize == out.size()) {
                    if (oldInputLength == in.readableBytes()) {
                        break;
                    } else {
                        continue;
                    }
                }

                if (oldInputLength == in.readableBytes()) {
                    throw new DecoderException(
                            StringUtil.simpleClassName(getClass()) +
                            ".decode() did not read anything but decoded a message.");
                }

                if (isSingleDecode()) {
                    break;
                }
            }
        } catch (DecoderException e) {
            throw e;
        } catch (Throwable cause) {
            throw new DecoderException(cause);
        }
    }

    /**
     * Decode the from one {@link ByteBuf} to an other. This method will be called till either the input
     * {@link ByteBuf} has nothing to read anymore, till nothing was read from the input {@link ByteBuf} or till
     * this method returns {@code null}.
     *
     * @param ctx           the {@link ChannelHandlerContext} which this {@link ByteToMessageDecoder} belongs to
     * @param in            the {@link ByteBuf} from which to read data
     * @param out           the {@link List} to which decoded messages should be added

     * @throws Exception    is thrown if an error accour
     */
    protected abstract void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception;

    /**
     * Is called one last time when the {@link ChannelHandlerContext} goes in-active. Which means the
     * {@link #channelInactive(ChannelHandlerContext)} was triggered.
     *
     * By default this will just call {@link #decode(ChannelHandlerContext, ByteBuf, List)} but sub-classes may
     * override this for some special cleanup operation.
     */
    protected void decodeLast(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        decode(ctx, in, out);
    }
}
