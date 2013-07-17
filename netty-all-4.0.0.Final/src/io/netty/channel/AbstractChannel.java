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
package io.netty.channel;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufHolder;
import io.netty.util.DefaultAttributeMap;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.ThreadLocalRandom;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

import java.io.EOFException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.NotYetConnectedException;

/**
 * A skeletal {@link Channel} implementation.
 */
public abstract class AbstractChannel extends DefaultAttributeMap implements Channel {

    private static final InternalLogger logger = InternalLoggerFactory.getInstance(AbstractChannel.class);

    private final Channel parent;
    private final long hashCode = ThreadLocalRandom.current().nextLong();
    private final Unsafe unsafe;
    private final DefaultChannelPipeline pipeline;
    private final ChannelOutboundBuffer outboundBuffer = new ChannelOutboundBuffer(this);
    private final ChannelFuture succeededFuture = new SucceededChannelFuture(this, null);
    private final VoidChannelPromise voidPromise = new VoidChannelPromise(this, true);
    private final VoidChannelPromise unsafeVoidPromise = new VoidChannelPromise(this, false);
    private final CloseFuture closeFuture = new CloseFuture(this);

    private volatile SocketAddress localAddress;
    private volatile SocketAddress remoteAddress;
    private volatile EventLoop eventLoop;
    private volatile boolean registered;

    private ClosedChannelException closedChannelException;
    private boolean inFlush0;

    /** Cache for the string representation of this channel */
    private boolean strValActive;
    private String strVal;

    /**
     * Creates a new instance.
     *
     * @param parent
     *        the parent of this channel. {@code null} if there's no parent.
     */
    protected AbstractChannel(Channel parent) {
        this.parent = parent;
        unsafe = newUnsafe();
        pipeline = new DefaultChannelPipeline(this);
    }

    @Override
    public boolean isWritable() {
        return outboundBuffer.getWritable();
    }

    @Override
    public Channel parent() {
        return parent;
    }

    @Override
    public ChannelPipeline pipeline() {
        return pipeline;
    }

    @Override
    public ByteBufAllocator alloc() {
        return config().getAllocator();
    }

    @Override
    public EventLoop eventLoop() {
        EventLoop eventLoop = this.eventLoop;
        if (eventLoop == null) {
            throw new IllegalStateException("channel not registered to an event loop");
        }
        return eventLoop;
    }

    @Override
    public SocketAddress localAddress() {
        SocketAddress localAddress = this.localAddress;
        if (localAddress == null) {
            try {
                this.localAddress = localAddress = unsafe().localAddress();
            } catch (Throwable t) {
                // Sometimes fails on a closed socket in Windows.
                return null;
            }
        }
        return localAddress;
    }

    protected void invalidateLocalAddress() {
        localAddress = null;
    }

    @Override
    public SocketAddress remoteAddress() {
        SocketAddress remoteAddress = this.remoteAddress;
        if (remoteAddress == null) {
            try {
                this.remoteAddress = remoteAddress = unsafe().remoteAddress();
            } catch (Throwable t) {
                // Sometimes fails on a closed socket in Windows.
                return null;
            }
        }
        return remoteAddress;
    }

    /**
     * Reset the stored remoteAddress
     */
    protected void invalidateRemoteAddress() {
        remoteAddress = null;
    }

    @Override
    public boolean isRegistered() {
        return registered;
    }

    @Override
    public ChannelFuture bind(SocketAddress localAddress) {
        return pipeline.bind(localAddress);
    }

    @Override
    public ChannelFuture connect(SocketAddress remoteAddress) {
        return pipeline.connect(remoteAddress);
    }

    @Override
    public ChannelFuture connect(SocketAddress remoteAddress, SocketAddress localAddress) {
        return pipeline.connect(remoteAddress, localAddress);
    }

    @Override
    public ChannelFuture disconnect() {
        return pipeline.disconnect();
    }

    @Override
    public ChannelFuture close() {
        return pipeline.close();
    }

    @Override
    public ChannelFuture deregister() {
        return pipeline.deregister();
    }

    @Override
    public Channel flush() {
        pipeline.flush();
        return this;
    }

    @Override
    public ChannelFuture bind(SocketAddress localAddress, ChannelPromise promise) {
        return pipeline.bind(localAddress, promise);
    }

    @Override
    public ChannelFuture connect(SocketAddress remoteAddress, ChannelPromise promise) {
        return pipeline.connect(remoteAddress, promise);
    }

    @Override
    public ChannelFuture connect(SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) {
        return pipeline.connect(remoteAddress, localAddress, promise);
    }

    @Override
    public ChannelFuture disconnect(ChannelPromise promise) {
        return pipeline.disconnect(promise);
    }

    @Override
    public ChannelFuture close(ChannelPromise promise) {
        return pipeline.close(promise);
    }

    @Override
    public ChannelFuture deregister(ChannelPromise promise) {
        return pipeline.deregister(promise);
    }

    @Override
    public Channel read() {
        pipeline.read();
        return this;
    }

    @Override
    public ChannelFuture write(Object msg) {
        return pipeline.write(msg);
    }

    @Override
    public ChannelFuture write(Object msg, ChannelPromise promise) {
        return pipeline.write(msg, promise);
    }

    @Override
    public ChannelFuture writeAndFlush(Object msg) {
        return pipeline.writeAndFlush(msg);
    }

    @Override
    public ChannelFuture writeAndFlush(Object msg, ChannelPromise promise) {
        return pipeline.writeAndFlush(msg, promise);
    }

    @Override
    public ChannelPromise newPromise() {
        return new DefaultChannelPromise(this);
    }

    @Override
    public ChannelProgressivePromise newProgressivePromise() {
        return new DefaultChannelProgressivePromise(this);
    }

    @Override
    public ChannelFuture newSucceededFuture() {
        return succeededFuture;
    }

    @Override
    public ChannelFuture newFailedFuture(Throwable cause) {
        return new FailedChannelFuture(this, null, cause);
    }

    @Override
    public ChannelFuture closeFuture() {
        return closeFuture;
    }

    @Override
    public Unsafe unsafe() {
        return unsafe;
    }

    /**
     * Create a new {@link AbstractUnsafe} instance which will be used for the life-time of the {@link Channel}
     */
    protected abstract AbstractUnsafe newUnsafe();

    /**
     * Returns the ID of this channel.
     */
    @Override
    public final int hashCode() {
        return (int) hashCode;
    }

    /**
     * Returns {@code true} if and only if the specified object is identical
     * with this channel (i.e: {@code this == o}).
     */
    @Override
    public final boolean equals(Object o) {
        return this == o;
    }

    @Override
    public final int compareTo(Channel o) {
        if (this == o) {
            return 0;
        }

        long ret = hashCode - o.hashCode();
        if (ret > 0) {
            return 1;
        }
        if (ret < 0) {
            return -1;
        }

        ret = System.identityHashCode(this) - System.identityHashCode(o);
        if (ret != 0) {
            return (int) ret;
        }

        // Jackpot! - different objects with same hashes
        throw new Error();
    }

    /**
     * Returns the {@link String} representation of this channel.  The returned
     * string contains the {@linkplain #hashCode()} ID}, {@linkplain #localAddress() local address},
     * and {@linkplain #remoteAddress() remote address} of this channel for
     * easier identification.
     */
    @Override
    public String toString() {
        boolean active = isActive();
        if (strValActive == active && strVal != null) {
            return strVal;
        }

        SocketAddress remoteAddr = remoteAddress();
        SocketAddress localAddr = localAddress();
        if (remoteAddr != null) {
            SocketAddress srcAddr;
            SocketAddress dstAddr;
            if (parent == null) {
                srcAddr = localAddr;
                dstAddr = remoteAddr;
            } else {
                srcAddr = remoteAddr;
                dstAddr = localAddr;
            }
            strVal = String.format("[id: 0x%08x, %s %s %s]", (int) hashCode, srcAddr, active? "=>" : ":>", dstAddr);
        } else if (localAddr != null) {
            strVal = String.format("[id: 0x%08x, %s]", (int) hashCode, localAddr);
        } else {
            strVal = String.format("[id: 0x%08x]", (int) hashCode);
        }

        strValActive = active;
        return strVal;
    }

    @Override
    public final ChannelPromise voidPromise() {
        return voidPromise;
    }

    /**
     * {@link Unsafe} implementation which sub-classes must extend and use.
     */
    protected abstract class AbstractUnsafe implements Unsafe {

        @Override
        public final SocketAddress localAddress() {
            return localAddress0();
        }

        @Override
        public final SocketAddress remoteAddress() {
            return remoteAddress0();
        }

        @Override
        public final void register(EventLoop eventLoop, final ChannelPromise promise) {
            if (eventLoop == null) {
                throw new NullPointerException("eventLoop");
            }
            if (isRegistered()) {
                promise.setFailure(new IllegalStateException("registered to an event loop already"));
                return;
            }
            if (!isCompatible(eventLoop)) {
                promise.setFailure(
                        new IllegalStateException("incompatible event loop type: " + eventLoop.getClass().getName()));
                return;
            }

            AbstractChannel.this.eventLoop = eventLoop;

            if (eventLoop.inEventLoop()) {
                register0(promise);
            } else {
                try {
                    eventLoop.execute(new Runnable() {
                        @Override
                        public void run() {
                            register0(promise);
                        }
                    });
                } catch (Throwable t) {
                    logger.warn(
                            "Force-closing a channel whose registration task was unaccepted by an event loop: {}",
                            AbstractChannel.this, t);
                    closeForcibly();
                    promise.setFailure(t);
                }
            }
        }

        private void register0(ChannelPromise promise) {
            try {
                // check if the channel is still open as it could be closed in the mean time when the register
                // call was outside of the eventLoop
                if (!ensureOpen(promise)) {
                    return;
                }
                Runnable postRegisterTask = doRegister();
                registered = true;
                promise.setSuccess();
                pipeline.fireChannelRegistered();
                if (postRegisterTask != null) {
                    postRegisterTask.run();
                }
                if (isActive()) {
                    pipeline.fireChannelActive();
                }
            } catch (Throwable t) {
                // Close the channel directly to avoid FD leak.
                closeForcibly();
                if (!promise.tryFailure(t)) {
                    logger.warn(
                            "Tried to fail the registration promise, but it is complete already. " +
                                    "Swallowing the cause of the registration failure:", t);
                }
                closeFuture.setClosed();
            }
        }

        @Override
        public final void bind(final SocketAddress localAddress, final ChannelPromise promise) {
            if (!ensureOpen(promise)) {
                return;
            }

            try {
                boolean wasActive = isActive();

                // See: https://github.com/netty/netty/issues/576
                if (!PlatformDependent.isWindows() && !PlatformDependent.isRoot() &&
                    Boolean.TRUE.equals(config().getOption(ChannelOption.SO_BROADCAST)) &&
                    localAddress instanceof InetSocketAddress &&
                    !((InetSocketAddress) localAddress).getAddress().isAnyLocalAddress()) {
                    // Warn a user about the fact that a non-root user can't receive a
                    // broadcast packet on *nix if the socket is bound on non-wildcard address.
                    logger.warn(
                            "A non-root user can't receive a broadcast packet if the socket " +
                            "is not bound to a wildcard address; binding to a non-wildcard " +
                            "address (" + localAddress + ") anyway as requested.");
                }

                doBind(localAddress);
                promise.setSuccess();
                if (!wasActive && isActive()) {
                    pipeline.fireChannelActive();
                }
            } catch (Throwable t) {
                promise.setFailure(t);
                closeIfClosed();
            }
        }

        @Override
        public final void disconnect(final ChannelPromise promise) {
            try {
                boolean wasActive = isActive();
                doDisconnect();
                promise.setSuccess();
                if (wasActive && !isActive()) {
                    invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            pipeline.fireChannelInactive();
                        }
                    });
                }
            } catch (Throwable t) {
                promise.setFailure(t);
                closeIfClosed();
            }
        }

        @Override
        public final void close(final ChannelPromise promise) {
            boolean wasActive = isActive();
            if (closeFuture.setClosed()) {
                try {
                    doClose();
                    promise.setSuccess();
                } catch (Throwable t) {
                    promise.setFailure(t);
                }

                if (closedChannelException == null) {
                    closedChannelException = new ClosedChannelException();
                }

                // fail all queued messages
                if (outboundBuffer.next()) {
                    outboundBuffer.fail(closedChannelException);
                }

                if (wasActive && !isActive()) {
                    invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            pipeline.fireChannelInactive();
                        }
                    });
                }

                deregister(voidPromise());
            } else {
                // Closed already.
                promise.setSuccess();
            }
        }

        @Override
        public final void closeForcibly() {
            try {
                doClose();
            } catch (Exception e) {
                logger.warn("Failed to close a channel.", e);
            }
        }

        @Override
        public final void deregister(final ChannelPromise promise) {
            if (!registered) {
                promise.setSuccess();
                return;
            }

            Runnable postTask = null;
            try {
                postTask = doDeregister();
            } catch (Throwable t) {
                logger.warn("Unexpected exception occurred while deregistering a channel.", t);
            } finally {
                if (registered) {
                    registered = false;
                    promise.setSuccess();
                    invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            pipeline.fireChannelUnregistered();
                        }
                    });
                } else {
                    // Some transports like local and AIO does not allow the deregistration of
                    // an open channel.  Their doDeregister() calls close().  Consequently,
                    // close() calls deregister() again - no need to fire channelUnregistered.
                    promise.setSuccess();
                }

                if (postTask != null) {
                    postTask.run();
                }
            }
        }

        @Override
        public void beginRead() {
            if (!isActive()) {
                return;
            }

            try {
                doBeginRead();
            } catch (final Exception e) {
                invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        pipeline.fireExceptionCaught(e);
                    }
                });
                close(voidPromise());
            }
        }

        @Override
        public void write(Object msg, ChannelPromise promise) {
            outboundBuffer.addMessage(msg, promise);
        }

        @Override
        public void flush() {
            outboundBuffer.addFlush();
            flush0();
        }

        protected void flush0() {
            if (inFlush0) {
                // Avoid re-entrance
                return;
            }

            inFlush0 = true;

            final ChannelOutboundBuffer outboundBuffer = AbstractChannel.this.outboundBuffer;

            // Mark all pending write requests as failure if the channel is inactive.
            if (!isActive()) {
                if (isOpen()) {
                    outboundBuffer.fail(new NotYetConnectedException());
                } else {
                    outboundBuffer.fail(new ClosedChannelException());
                }
                inFlush0 = false;
                return;
            }

            try {
                for (;;) {
                    MessageList messages = outboundBuffer.currentMessages;
                    if (messages == null) {
                        if (!outboundBuffer.next()) {
                            break;
                        }
                        messages = outboundBuffer.currentMessages;
                    }

                    int messageIndex = outboundBuffer.currentMessageIndex;
                    int messageCount = messages.size();
                    Object[] messageArray = messages.messages();
                    ChannelPromise[] promiseArray = messages.promises();

                    // Write the messages.
                    final int writtenMessages = doWrite(messageArray, messageCount, messageIndex);

                    // Notify the promises.
                    final int newMessageIndex = messageIndex + writtenMessages;
                    for (int i = messageIndex; i < newMessageIndex; i ++) {
                        promiseArray[i].trySuccess();
                    }

                    // Update the index variable and decide what to do next.
                    outboundBuffer.currentMessageIndex = messageIndex = newMessageIndex;
                    if (messageIndex >= messageCount) {
                        messages.recycle();
                        if (!outboundBuffer.next()) {
                            break;
                        }
                    } else {
                        // Could not flush the current write request completely. Try again later.
                        break;
                    }
                }
            } catch (Throwable t) {
                outboundBuffer.fail(t);
                if (t instanceof IOException) {
                    close(voidPromise());
                }
            } finally {
                inFlush0 = false;
            }
        }

        @Override
        public ChannelPromise voidPromise() {
            return unsafeVoidPromise;
        }

        protected final boolean ensureOpen(ChannelPromise promise) {
            if (isOpen()) {
                return true;
            }

            Exception e = new ClosedChannelException();
            promise.setFailure(e);
            return false;
        }

        protected final void closeIfClosed() {
            if (isOpen()) {
                return;
            }
            close(voidPromise());
        }

        private void invokeLater(Runnable task) {
            // This method is used by outbound operation implementations to trigger an inbound event later.
            // They do not trigger an inbound event immediately because an outbound operation might have been
            // triggered by another inbound event handler method.  If fired immediately, the call stack
            // will look like this for example:
            //
            //   handlerA.inboundBufferUpdated() - (1) an inbound handler method closes a connection.
            //   -> handlerA.ctx.close()
            //      -> channel.unsafe.close()
            //         -> handlerA.channelInactive() - (2) another inbound handler method called while in (1) yet
            //
            // which means the execution of two inbound handler methods of the same handler overlap undesirably.
            eventLoop().execute(task);
        }
    }

    /**
     * Return {@code true} if the given {@link EventLoop} is compatible with this instance.
     */
    protected abstract boolean isCompatible(EventLoop loop);

    /**
     * Returns the {@link SocketAddress} which is bound locally.
     */
    protected abstract SocketAddress localAddress0();

    /**
     * Return the {@link SocketAddress} which the {@link Channel} is connected to.
     */
    protected abstract SocketAddress remoteAddress0();

    /**
     * Is called after the {@link Channel} is registered with its {@link EventLoop} as part of the register process.
     * You can return a {@link Runnable} which will be run as post-task of the registration process.
     *
     * Sub-classes may override this method as it will just return {@code null}
     */
    protected Runnable doRegister() throws Exception {
        return null;
    }

    /**
     * Bind the {@link Channel} to the {@link SocketAddress}
     */
    protected abstract void doBind(SocketAddress localAddress) throws Exception;

    /**
     * Disconnect this {@link Channel} from its remote peer
     */
    protected abstract void doDisconnect() throws Exception;

    /**
     * Will be called before the actual close operation will be performed. Sub-classes may override this as the default
     * is to do nothing.
     */
    protected void doPreClose() throws Exception {
        // NOOP by default
    }

    /**
     * Close the {@link Channel}
     */
    protected abstract void doClose() throws Exception;

    /**
     * Deregister the {@link Channel} from its {@link EventLoop}.
     * You can return a {@link Runnable} which will be run as post-task of the registration process.
     *
     * Sub-classes may override this method
     */
    protected Runnable doDeregister() throws Exception {
        return null;
    }

    /**
     * Schedule a read operation.
     */
    protected abstract void doBeginRead() throws Exception;

    /**
     * Flush the content of the given {@link ByteBuf} to the remote peer.
     *
     * Sub-classes may override this as this implementation will just thrown an {@link UnsupportedOperationException}
     *
     * @return the number of written messages
     */
    protected abstract int doWrite(Object[] msgs, int msgsLength, int startIndex) throws Exception;

    protected static void checkEOF(FileRegion region) throws IOException {
        if (region.transfered() < region.count()) {
            throw new EOFException("Expected to be able to write "
                    + region.count() + " bytes, but only wrote "
                    + region.transfered());
        }
    }

    /**
     * Calculate the number of bytes a message takes up in memory. Sub-classes may override this if they use different
     * messages then {@link ByteBuf} or {@link ByteBufHolder}. If the size can not be calculated 0 should be returned.
     */
    protected int calculateMessageSize(Object message) {
        if (message instanceof ByteBuf) {
            return ((ByteBuf) message).readableBytes();
        }
        if (message instanceof ByteBufHolder) {
            return ((ByteBufHolder) message).content().readableBytes();
        }
        return 0;
    }

    final class CloseFuture extends DefaultChannelPromise {

        CloseFuture(AbstractChannel ch) {
            super(ch);
        }

        @Override
        public ChannelPromise setSuccess() {
            throw new IllegalStateException();
        }

        @Override
        public ChannelPromise setFailure(Throwable cause) {
            throw new IllegalStateException();
        }

        @Override
        public boolean trySuccess() {
            throw new IllegalStateException();
        }

        @Override
        public boolean tryFailure(Throwable cause) {
            throw new IllegalStateException();
        }

        boolean setClosed() {
            try {
                doPreClose();
            } catch (Exception e) {
                logger.warn("doPreClose() raised an exception.", e);
            }
            return super.trySuccess();
        }
    }
}
