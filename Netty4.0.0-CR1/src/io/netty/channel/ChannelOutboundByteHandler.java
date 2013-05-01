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

/**
 * {@link ChannelOutboundHandler} which operates on bytes which are hold in a {@link ByteBuf}.
 */
public interface ChannelOutboundByteHandler extends ChannelOutboundHandler {
    /**
     * Return the {@link ByteBuf} which will be used for outbound data for the given {@link ChannelHandlerContext}.
     * Implementations should take {@link ChannelConfig#getDefaultHandlerByteBufType()} into account.
     * <p>
     * Use of {@link ChannelHandlerUtil#allocate(ChannelHandlerContext)} is adviced.
     */
    @Override
    ByteBuf newOutboundBuffer(ChannelHandlerContext ctx) throws Exception;

    /**
     * Discards the read bytes of the outbound buffer and optionally trims its unused portion to reduce memory
     * consumption. The most common implementation of this method will look like the following:
     * <pre>
     *     ctx.outboundByteBuffer().discardSomeReadBytes();
     * </pre>
     */
    void discardOutboundReadBytes(ChannelHandlerContext ctx) throws Exception;
}
