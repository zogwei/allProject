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

import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.UnpooledByteBufAllocator;
import io.netty.channel.socket.SocketChannelConfig;

import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Map.Entry;

import static io.netty.channel.ChannelOption.*;

/**
 * The default {@link SocketChannelConfig} implementation.
 */
public class DefaultChannelConfig implements ChannelConfig {

    private static final ByteBufAllocator DEFAULT_ALLOCATOR = UnpooledByteBufAllocator.DEFAULT;
    private static final RecvByteBufAllocator DEFAULT_RCVBUF_ALLOCATOR = AdaptiveRecvByteBufAllocator.DEFAULT;
    private static final int DEFAULT_CONNECT_TIMEOUT = 30000;

    protected final Channel channel;

    private volatile ByteBufAllocator allocator = DEFAULT_ALLOCATOR;
    private volatile RecvByteBufAllocator rcvBufAllocator = DEFAULT_RCVBUF_ALLOCATOR;
    private volatile int connectTimeoutMillis = DEFAULT_CONNECT_TIMEOUT;
    private volatile int maxMessagesPerRead;
    private volatile int writeSpinCount = 16;
    private volatile boolean autoRead = true;
    private volatile int writeBufferHighWaterMark = 64 * 1024;
    private volatile int writeBufferLowWaterMark = 32 * 1024;

    public DefaultChannelConfig(Channel channel) {
        if (channel == null) {
            throw new NullPointerException("channel");
        }
        this.channel = channel;

        if (channel instanceof ServerChannel) {
            // Accept as many incoming connections as possible.
            maxMessagesPerRead = 16;
        } else {
            maxMessagesPerRead = 1;
        }
    }

    @Override
    public Map<ChannelOption<?>, Object> getOptions() {
        return getOptions(
                null,
                CONNECT_TIMEOUT_MILLIS, MAX_MESSAGES_PER_READ, WRITE_SPIN_COUNT,
                ALLOCATOR, AUTO_READ, RCVBUF_ALLOCATOR);
    }

    protected Map<ChannelOption<?>, Object> getOptions(
            Map<ChannelOption<?>, Object> result, ChannelOption<?>... options) {
        if (result == null) {
            result = new IdentityHashMap<ChannelOption<?>, Object>();
        }
        for (ChannelOption<?> o: options) {
            result.put(o, getOption(o));
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean setOptions(Map<ChannelOption<?>, ?> options) {
        if (options == null) {
            throw new NullPointerException("options");
        }

        boolean setAllOptions = true;
        for (Entry<ChannelOption<?>, ?> e: options.entrySet()) {
            if (!setOption((ChannelOption<Object>) e.getKey(), e.getValue())) {
                setAllOptions = false;
            }
        }

        return setAllOptions;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getOption(ChannelOption<T> option) {
        if (option == null) {
            throw new NullPointerException("option");
        }

        if (option == CONNECT_TIMEOUT_MILLIS) {
            return (T) Integer.valueOf(getConnectTimeoutMillis());
        }
        if (option == MAX_MESSAGES_PER_READ) {
            return (T) Integer.valueOf(getMaxMessagesPerRead());
        }
        if (option == WRITE_SPIN_COUNT) {
            return (T) Integer.valueOf(getWriteSpinCount());
        }
        if (option == ALLOCATOR) {
            return (T) getAllocator();
        }
        if (option == RCVBUF_ALLOCATOR) {
            return (T) getRecvByteBufAllocator();
        }
        if (option == AUTO_READ) {
            return (T) Boolean.valueOf(isAutoRead());
        }

        return null;
    }

    @Override
    public <T> boolean setOption(ChannelOption<T> option, T value) {
        validate(option, value);

        if (option == CONNECT_TIMEOUT_MILLIS) {
            setConnectTimeoutMillis((Integer) value);
        } else if (option == MAX_MESSAGES_PER_READ) {
            setMaxMessagesPerRead((Integer) value);
        } else if (option == WRITE_SPIN_COUNT) {
            setWriteSpinCount((Integer) value);
        } else if (option == ALLOCATOR) {
            setAllocator((ByteBufAllocator) value);
        } else if (option == RCVBUF_ALLOCATOR) {
            setRecvByteBufAllocator((RecvByteBufAllocator) value);
        } else if (option == AUTO_READ) {
            setAutoRead((Boolean) value);
        } else {
            return false;
        }

        return true;
    }

    protected <T> void validate(ChannelOption<T> option, T value) {
        if (option == null) {
            throw new NullPointerException("option");
        }
        option.validate(value);
    }

    @Override
    public int getConnectTimeoutMillis() {
        return connectTimeoutMillis;
    }

    @Override
    public ChannelConfig setConnectTimeoutMillis(int connectTimeoutMillis) {
        if (connectTimeoutMillis < 0) {
            throw new IllegalArgumentException(String.format(
                    "connectTimeoutMillis: %d (expected: >= 0)", connectTimeoutMillis));
        }
        this.connectTimeoutMillis = connectTimeoutMillis;
        return this;
    }

    @Override
    public int getMaxMessagesPerRead() {
        return maxMessagesPerRead;
    }

    @Override
    public ChannelConfig setMaxMessagesPerRead(int maxMessagesPerRead) {
        if (maxMessagesPerRead <= 0) {
            throw new IllegalArgumentException("maxMessagesPerRead: " + maxMessagesPerRead + " (expected: > 0)");
        }
        this.maxMessagesPerRead = maxMessagesPerRead;
        return this;
    }

    @Override
    public int getWriteSpinCount() {
        return writeSpinCount;
    }

    @Override
    public ChannelConfig setWriteSpinCount(int writeSpinCount) {
        if (writeSpinCount <= 0) {
            throw new IllegalArgumentException(
                    "writeSpinCount must be a positive integer.");
        }
        this.writeSpinCount = writeSpinCount;
        return this;
    }

    @Override
    public ByteBufAllocator getAllocator() {
        return allocator;
    }

    @Override
    public ChannelConfig setAllocator(ByteBufAllocator allocator) {
        if (allocator == null) {
            throw new NullPointerException("allocator");
        }
        this.allocator = allocator;
        return this;
    }

    @Override
    public RecvByteBufAllocator getRecvByteBufAllocator() {
        return rcvBufAllocator;
    }

    @Override
    public ChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator allocator) {
        if (allocator == null) {
            throw new NullPointerException("allocator");
        }
        rcvBufAllocator = allocator;
        return this;
    }

    @Override
    public boolean isAutoRead() {
        return autoRead;
    }

    @Override
    public ChannelConfig setAutoRead(boolean autoRead) {
        boolean oldAutoRead = this.autoRead;
        this.autoRead = autoRead;
        if (autoRead && !oldAutoRead) {
            channel.read();
        }
        return this;
    }

    @Override
    public int getWriteBufferHighWaterMark() {
        return writeBufferHighWaterMark;
    }

    @Override
    public ChannelConfig setWriteBufferHighWaterMark(int writeBufferHighWaterMark) {
        if (writeBufferHighWaterMark < getWriteBufferLowWaterMark()) {
            throw new IllegalArgumentException(
                    "writeBufferHighWaterMark cannot be less than " +
                            "writeBufferLowWaterMark (" + getWriteBufferLowWaterMark() + "): " +
                            writeBufferHighWaterMark);
        }
        if (writeBufferHighWaterMark < 0) {
            throw new IllegalArgumentException(
                    "writeBufferHighWaterMark must be >= 0");
        }
        this.writeBufferHighWaterMark = writeBufferHighWaterMark;
        return this;
    }

    @Override
    public int getWriteBufferLowWaterMark() {
        return writeBufferLowWaterMark;
    }

    @Override
    public ChannelConfig setWriteBufferLowWaterMark(int writeBufferLowWaterMark) {
        if (writeBufferLowWaterMark > getWriteBufferHighWaterMark()) {
            throw new IllegalArgumentException(
                    "writeBufferLowWaterMark cannot be greater than " +
                            "writeBufferHighWaterMark (" + getWriteBufferHighWaterMark() + "): " +
                            writeBufferLowWaterMark);
        }
        if (writeBufferLowWaterMark < 0) {
            throw new IllegalArgumentException(
                    "writeBufferLowWaterMark must be >= 0");
        }
        this.writeBufferLowWaterMark = writeBufferLowWaterMark;
        return this;
    }
}
