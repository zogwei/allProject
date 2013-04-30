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
package io.netty.channel.nio;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelPromise;
import io.netty.channel.FileRegion;
import io.netty.channel.socket.ChannelInputShutdownEvent;

import java.io.IOException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.WritableByteChannel;

/**
 * {@link AbstractNioChannel} base class for {@link Channel}s that operate on bytes.
 */
public abstract class AbstractNioByteChannel extends AbstractNioChannel {

    /**
     * Create a new instance
     *
     * @param parent            the parent {@link Channel} by which this instance was created. May be {@code null}
     * @param id                the id of this instance or {@code null} if one should be generated
     * @param ch                the underlying {@link SelectableChannel} on which it operates
     */
    protected AbstractNioByteChannel(
            Channel parent, Integer id, SelectableChannel ch) {
        super(parent, id, ch, SelectionKey.OP_READ);
    }

    @Override
    protected AbstractNioUnsafe newUnsafe() {
        return new NioByteUnsafe();
    }

    private final class NioByteUnsafe extends AbstractNioUnsafe {
        @Override
        public void read() {
            assert eventLoop().inEventLoop();
            final SelectionKey key = selectionKey();
            if (!config().isAutoRead()) {
                // only remove readInterestOp if needed
                key.interestOps(key.interestOps() & ~readInterestOp);
            }

            final ChannelPipeline pipeline = pipeline();
            final ByteBuf byteBuf = pipeline.inboundByteBuffer();
            boolean closed = false;
            boolean read = false;
            boolean firedChannelReadSuspended = false;
            try {
                expandReadBuffer(byteBuf);
                loop: for (;;) {
                    int localReadAmount = doReadBytes(byteBuf);
                    if (localReadAmount > 0) {
                        read = true;
                    } else if (localReadAmount < 0) {
                        closed = true;
                        break;
                    }

                    switch (expandReadBuffer(byteBuf)) {
                    case 0:
                        // Read all - stop reading.
                        break loop;
                    case 1:
                        // Keep reading until everything is read.
                        break;
                    case 2:
                        // Let the inbound handler drain the buffer and continue reading.
                        if (read) {
                            read = false;
                            pipeline.fireInboundBufferUpdated();
                            if (!byteBuf.isWritable()) {
                                throw new IllegalStateException(
                                        "an inbound handler whose buffer is full must consume at " +
                                        "least one byte.");
                            }
                        }
                    }
                }
            } catch (Throwable t) {
                if (read) {
                    read = false;
                    pipeline.fireInboundBufferUpdated();
                }

                if (t instanceof IOException) {
                    closed = true;
                } else if (!closed) {
                    firedChannelReadSuspended = true;
                    pipeline.fireChannelReadSuspended();
                }
                pipeline().fireExceptionCaught(t);
            } finally {
                if (read) {
                    pipeline.fireInboundBufferUpdated();
                }

                if (closed) {
                    setInputShutdown();
                    if (isOpen()) {
                        if (Boolean.TRUE.equals(config().getOption(ChannelOption.ALLOW_HALF_CLOSURE))) {
                            key.interestOps(key.interestOps() & ~readInterestOp);
                            pipeline.fireUserEventTriggered(ChannelInputShutdownEvent.INSTANCE);
                        } else {
                            close(voidFuture());
                        }
                    }
                } else if (!firedChannelReadSuspended) {
                    pipeline.fireChannelReadSuspended();
                }
            }
        }
    }

    @Override
    protected void doFlushByteBuffer(ByteBuf buf) throws Exception {
        if (!buf.isReadable()) {
            // Reset reader/writerIndex to 0 if the buffer is empty.
            buf.clear();
            return;
        }

        for (int i = config().getWriteSpinCount() - 1; i >= 0; i --) {
            int localFlushedAmount = doWriteBytes(buf, i == 0);
            if (localFlushedAmount > 0) {
                break;
            }
            if (!buf.isReadable()) {
                // Reset reader/writerIndex to 0 if the buffer is empty.
                buf.clear();
                break;
            }
        }
    }

    @Override
    protected void doFlushFileRegion(final FileRegion region, final ChannelPromise promise) throws Exception {
        if (javaChannel() instanceof WritableByteChannel) {
            TransferTask transferTask = new TransferTask(region, (WritableByteChannel) javaChannel(), promise);
            transferTask.transfer();
        } else {
            throw new UnsupportedOperationException("Underlying Channel is not of instance "
                    + WritableByteChannel.class);
        }
    }

    private final class TransferTask implements NioTask<SelectableChannel> {
        private long writtenBytes;
        private final FileRegion region;
        private final WritableByteChannel wch;
        private final ChannelPromise promise;

        TransferTask(FileRegion region, WritableByteChannel wch, ChannelPromise promise) {
            this.region = region;
            this.wch = wch;
            this.promise = promise;
        }

        void transfer() {
            try {
                for (;;) {
                    long localWrittenBytes = region.transferTo(wch, writtenBytes);
                    if (localWrittenBytes == 0) {
                        // reschedule for write once the channel is writable again
                        eventLoop().executeWhenWritable(
                                AbstractNioByteChannel.this, this);
                        return;
                    } else if (localWrittenBytes == -1) {
                        checkEOF(region, writtenBytes);
                        promise.setSuccess();
                        return;
                    } else {
                        writtenBytes += localWrittenBytes;
                        if (writtenBytes >= region.count()) {
                            region.release();
                            promise.setSuccess();
                            return;
                        }
                    }
                }
            } catch (Throwable cause) {
                region.release();
                promise.setFailure(cause);
            }
        }

        @Override
        public void channelReady(SelectableChannel ch, SelectionKey key) throws Exception {
            transfer();
        }

        @Override
        public void channelUnregistered(SelectableChannel ch, Throwable cause) throws Exception {
            if (cause != null) {
                promise.setFailure(cause);
                return;
            }

            if (writtenBytes < region.count()) {
                region.release();
                if (!isOpen()) {
                    promise.setFailure(new ClosedChannelException());
                } else {
                    promise.setFailure(new IllegalStateException(
                            "Channel was unregistered before the region could be fully written"));
                }
            }
        }
    }

    /**
     * Read bytes into the given {@link ByteBuf} and return the amount.
     */
    protected abstract int doReadBytes(ByteBuf buf) throws Exception;

    /**
     * Write bytes form the given {@link ByteBuf} to the underlying {@link java.nio.channels.Channel}.
     * @param buf           the {@link ByteBuf} from which the bytes should be written
     * @param lastSpin      {@code true} if this is the last write try
     * @return amount       the amount of written bytes
     * @throws Exception    thrown if an error accour
     */
    protected abstract int doWriteBytes(ByteBuf buf, boolean lastSpin) throws Exception;

}
