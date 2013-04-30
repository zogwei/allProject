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
package io.netty.channel.socket.oio;

import io.netty.buffer.BufType;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.MessageBuf;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelMetadata;
import io.netty.channel.ChannelPromise;
import io.netty.channel.oio.AbstractOioMessageChannel;
import io.netty.channel.socket.DatagramChannel;
import io.netty.channel.socket.DatagramChannelConfig;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.DefaultDatagramChannelConfig;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Locale;

/**
 * {@link DatagramChannel} implementation which use Old-Blocking-IO. It can be used to read and write
 * {@link DatagramPacket}s via UDP.
 */
public class OioDatagramChannel extends AbstractOioMessageChannel
                                implements DatagramChannel {

    private static final InternalLogger logger = InternalLoggerFactory.getInstance(OioDatagramChannel.class);

    private static final ChannelMetadata METADATA = new ChannelMetadata(BufType.MESSAGE, true);

    private static final byte[] EMPTY_DATA = new byte[0];

    private final MulticastSocket socket;
    private final DatagramChannelConfig config;
    private final java.net.DatagramPacket tmpPacket = new java.net.DatagramPacket(EMPTY_DATA, 0);

    private static MulticastSocket newSocket() {
        try {
            return new MulticastSocket(null);
        } catch (Exception e) {
            throw new ChannelException("failed to create a new socket", e);
        }
    }

    /**
     * Create a new instance with an new {@link MulticastSocket}.
     */
    public OioDatagramChannel() {
        this(newSocket());
    }

    /**
     * Create a new instance from the given {@link MulticastSocket}.
     *
     * @param socket    the {@link MulticastSocket} which is used by this instance
     */
    public OioDatagramChannel(MulticastSocket socket) {
        this(null, socket);
    }

    /**
     * Create a new instance from the given {@link MulticastSocket}.
     *
     * @param id        the id which should be used for this instance or {@code null} if a new one should be generated
     * @param socket    the {@link MulticastSocket} which is used by this instance
     */
    public OioDatagramChannel(Integer id, MulticastSocket socket) {
        super(null, id);

        boolean success = false;
        try {
            socket.setSoTimeout(SO_TIMEOUT);
            socket.setBroadcast(false);
            success = true;
        } catch (SocketException e) {
            throw new ChannelException(
                    "Failed to configure the datagram socket timeout.", e);
        } finally {
            if (!success) {
                socket.close();
            }
        }

        this.socket = socket;
        config = new DefaultDatagramChannelConfig(this, socket);
    }

    @Override
    public ChannelMetadata metadata() {
        return METADATA;
    }

    @Override
    public DatagramChannelConfig config() {
        return config;
    }

    @Override
    public boolean isOpen() {
        return !socket.isClosed();
    }

    @Override
    public boolean isActive() {
        return isOpen() && socket.isBound();
    }

    @Override
    public boolean isConnected() {
        return socket.isConnected();
    }

    @Override
    protected SocketAddress localAddress0() {
        return socket.getLocalSocketAddress();
    }

    @Override
    protected SocketAddress remoteAddress0() {
        return socket.getRemoteSocketAddress();
    }

    @Override
    protected void doBind(SocketAddress localAddress) throws Exception {
        socket.bind(localAddress);
    }

    @Override
    public InetSocketAddress localAddress() {
        return (InetSocketAddress) super.localAddress();
    }

    @Override
    public InetSocketAddress remoteAddress() {
        return (InetSocketAddress) super.remoteAddress();
    }

    @Override
    protected void doConnect(SocketAddress remoteAddress,
            SocketAddress localAddress) throws Exception {
        if (localAddress != null) {
            socket.bind(localAddress);
        }

        boolean success = false;
        try {
            socket.connect(remoteAddress);
            success = true;
        } finally {
            if (!success) {
                try {
                    socket.close();
                } catch (Throwable t) {
                    logger.warn("Failed to close a socket.", t);
                }
            }
        }
    }

    @Override
    protected void doDisconnect() throws Exception {
        socket.disconnect();
    }

    @Override
    protected void doClose() throws Exception {
        socket.close();
    }

    @Override
    protected int doReadMessages(MessageBuf<Object> buf) throws Exception {
        int packetSize = config().getReceivePacketSize();
        ByteBuf buffer = alloc().heapBuffer(packetSize);
        boolean free = true;

        try {
            tmpPacket.setData(buffer.array(), buffer.arrayOffset(), packetSize);
            socket.receive(tmpPacket);

            InetSocketAddress remoteAddr = (InetSocketAddress) tmpPacket.getSocketAddress();
            if (remoteAddr == null) {
                remoteAddr = remoteAddress();
            }

            DatagramPacket packet = new DatagramPacket(buffer.writerIndex(tmpPacket.getLength()), remoteAddr);
            buf.add(packet);
            free = false;
            return 1;
        } catch (SocketTimeoutException e) {
            // Expected
            return 0;
        } catch (SocketException e) {
            if (!e.getMessage().toLowerCase(Locale.US).contains("socket closed")) {
                throw e;
            }
            return -1;
        } catch (Throwable cause) {
            PlatformDependent.throwException(cause);
            return -1;
        } finally {
            if (free) {
                buffer.release();
            }
        }
    }

    @Override
    protected void doWriteMessages(MessageBuf<Object> buf) throws Exception {
        DatagramPacket p = (DatagramPacket) buf.poll();

        try {
            ByteBuf data = p.data();
            int length = data.readableBytes();
            InetSocketAddress remote = p.remoteAddress();
            if (remote != null) {
                tmpPacket.setSocketAddress(remote);
            }
            if (data.hasArray()) {
                tmpPacket.setData(data.array(), data.arrayOffset() + data.readerIndex(), length);
            } else {
                byte[] tmp = new byte[length];
                data.getBytes(data.readerIndex(), tmp);
                tmpPacket.setData(tmp);
            }
            socket.send(tmpPacket);
        } finally {
            p.release();
        }
    }

    @Override
    public ChannelFuture joinGroup(InetAddress multicastAddress) {
        return joinGroup(multicastAddress, newPromise());
    }

    @Override
    public ChannelFuture joinGroup(InetAddress multicastAddress, ChannelPromise promise) {
        ensureBound();
        try {
            socket.joinGroup(multicastAddress);
            promise.setSuccess();
        } catch (IOException e) {
            promise.setFailure(e);
        }
        return promise;
    }

    @Override
    public ChannelFuture joinGroup(InetSocketAddress multicastAddress, NetworkInterface networkInterface) {
        return joinGroup(multicastAddress, networkInterface, newPromise());
    }

    @Override
    public ChannelFuture joinGroup(
            InetSocketAddress multicastAddress, NetworkInterface networkInterface,
            ChannelPromise promise) {
        ensureBound();
        try {
            socket.joinGroup(multicastAddress, networkInterface);
            promise.setSuccess();
        } catch (IOException e) {
            promise.setFailure(e);
        }
        return promise;
    }

    @Override
    public ChannelFuture joinGroup(
            InetAddress multicastAddress, NetworkInterface networkInterface, InetAddress source) {
        return newFailedFuture(new UnsupportedOperationException());
    }

    @Override
    public ChannelFuture joinGroup(
            InetAddress multicastAddress, NetworkInterface networkInterface, InetAddress source,
            ChannelPromise promise) {
        promise.setFailure(new UnsupportedOperationException());
        return promise;
    }

    private void ensureBound() {
        if (!isActive()) {
            throw new IllegalStateException(
                    DatagramChannel.class.getName() +
                    " must be bound to join a group.");
        }
    }

    @Override
    public ChannelFuture leaveGroup(InetAddress multicastAddress) {
        return leaveGroup(multicastAddress, newPromise());
    }

    @Override
    public ChannelFuture leaveGroup(InetAddress multicastAddress, ChannelPromise promise) {
        try {
            socket.leaveGroup(multicastAddress);
            promise.setSuccess();
        } catch (IOException e) {
            promise.setFailure(e);
        }
        return promise;
    }

    @Override
    public ChannelFuture leaveGroup(
            InetSocketAddress multicastAddress, NetworkInterface networkInterface) {
        return leaveGroup(multicastAddress, networkInterface, newPromise());
    }

    @Override
    public ChannelFuture leaveGroup(
            InetSocketAddress multicastAddress, NetworkInterface networkInterface,
            ChannelPromise promise) {
        try {
            socket.leaveGroup(multicastAddress, networkInterface);
            promise.setSuccess();
        } catch (IOException e) {
            promise.setFailure(e);
        }
        return promise;
    }

    @Override
    public ChannelFuture leaveGroup(
            InetAddress multicastAddress, NetworkInterface networkInterface, InetAddress source) {
        return newFailedFuture(new UnsupportedOperationException());
    }

    @Override
    public ChannelFuture leaveGroup(
            InetAddress multicastAddress, NetworkInterface networkInterface, InetAddress source,
            ChannelPromise promise) {
        promise.setFailure(new UnsupportedOperationException());
        return promise;
    }

    @Override
    public ChannelFuture block(InetAddress multicastAddress,
            NetworkInterface networkInterface, InetAddress sourceToBlock) {
        return newFailedFuture(new UnsupportedOperationException());
    }

    @Override
    public ChannelFuture block(InetAddress multicastAddress,
            NetworkInterface networkInterface, InetAddress sourceToBlock,
            ChannelPromise promise) {
        promise.setFailure(new UnsupportedOperationException());
        return promise;
    }

    @Override
    public ChannelFuture block(InetAddress multicastAddress,
            InetAddress sourceToBlock) {
        return newFailedFuture(new UnsupportedOperationException());
    }

    @Override
    public ChannelFuture block(InetAddress multicastAddress,
            InetAddress sourceToBlock, ChannelPromise promise) {
        promise.setFailure(new UnsupportedOperationException());
        return promise;
    }
}
