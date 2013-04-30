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

import java.net.SocketAddress;

/**
 * {@link ChannelHandler} implementation which represents a combination out of a {@link ChannelStateHandler} and
 * the {@link ChannelOperationHandler}.
 *
 * It is a good starting point if your {@link ChannelHandler} implementation needs to intercept operations and also
 * state updates.
 */
public abstract class ChannelDuplexHandler extends ChannelStateHandlerAdapter implements ChannelOperationHandler {

    /**
     * Calls {@link ChannelHandlerContext#bind(SocketAddress, ChannelPromise)} to forward
     * to the next {@link ChannelOperationHandler} in the {@link ChannelPipeline}.
     *
     * Sub-classes may override this method to change behavior.
     */
    @Override
    public void bind(ChannelHandlerContext ctx, SocketAddress localAddress,
                     ChannelPromise future) throws Exception {
        ctx.bind(localAddress, future);
    }

    /**
     * Calls {@link ChannelHandlerContext#connect(SocketAddress, SocketAddress, ChannelPromise)} to forward
     * to the next {@link ChannelOperationHandler} in the {@link ChannelPipeline}.
     *
     * Sub-classes may override this method to change behavior.
     */
    @Override
    public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress,
                        SocketAddress localAddress, ChannelPromise future) throws Exception {
        ctx.connect(remoteAddress, localAddress, future);
    }

    /**
     * Calls {@link ChannelHandlerContext#disconnect(ChannelPromise)} to forward
     * to the next {@link ChannelOperationHandler} in the {@link ChannelPipeline}.
     *
     * Sub-classes may override this method to change behavior.
     */
    @Override
    public void disconnect(ChannelHandlerContext ctx, ChannelPromise future)
            throws Exception {
        ctx.disconnect(future);
    }

    /**
     * Calls {@link ChannelHandlerContext#close(ChannelPromise)} to forward
     * to the next {@link ChannelOperationHandler} in the {@link ChannelPipeline}.
     *
     * Sub-classes may override this method to change behavior.
     */
    @Override
    public void close(ChannelHandlerContext ctx, ChannelPromise future)
            throws Exception {
        ctx.close(future);
    }

    /**
     * Calls {@link ChannelHandlerContext#close(ChannelPromise)} to forward
     * to the next {@link ChannelOperationHandler} in the {@link ChannelPipeline}.
     *
     * Sub-classes may override this method to change behavior.
     */
    @Override
    public void deregister(ChannelHandlerContext ctx, ChannelPromise future)
            throws Exception {
        ctx.deregister(future);
    }

    @Override
    public void read(ChannelHandlerContext ctx) {
        ctx.read();
    }

    /**
     * Calls {@link ChannelHandlerContext#sendFile(FileRegion, ChannelPromise)} to forward
     * to the next {@link ChannelOperationHandler} in the {@link ChannelPipeline}.
     *
     * Sub-classes may override this method to change behavior.
     */
    @Override
    public void sendFile(ChannelHandlerContext ctx, FileRegion region, ChannelPromise future) throws Exception {
        ctx.sendFile(region, future);
    }
}
