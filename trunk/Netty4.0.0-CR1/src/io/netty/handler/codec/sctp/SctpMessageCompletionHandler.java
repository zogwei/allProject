/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package io.netty.handler.codec.sctp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundMessageHandler;
import io.netty.channel.ChannelInboundMessageHandlerAdapter;
import io.netty.channel.sctp.SctpMessage;

import java.util.HashMap;
import java.util.Map;

/**
 * {@link ChannelInboundMessageHandlerAdapter} which will take care of handle fragmented {@link SctpMessage}s, so
 * only <strong>complete</strong> {@link SctpMessage}s will be forwarded to the next
 * {@link ChannelInboundMessageHandler}.
 */
public class SctpMessageCompletionHandler extends ChannelInboundMessageHandlerAdapter<SctpMessage> {
    private final Map<Integer, ByteBuf> fragments = new HashMap<Integer, ByteBuf>();
    private boolean assembled;

    @Override
    public boolean beginMessageReceived(ChannelHandlerContext ctx) throws Exception {
        assembled = false;
        return super.beginMessageReceived(ctx);
    }

    @Override
    public void endMessageReceived(ChannelHandlerContext ctx) throws Exception {
        if (assembled) {
            assembled = false;
            ctx.fireInboundBufferUpdated();
        }
        super.endMessageReceived(ctx);
    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx, SctpMessage msg) throws Exception {

        final ByteBuf byteBuf = msg.data();
        final int protocolIdentifier = msg.protocolIdentifier();
        final int streamIdentifier = msg.streamIdentifier();
        final boolean isComplete = msg.isComplete();

        ByteBuf frag;

        if (fragments.containsKey(streamIdentifier)) {
            frag = fragments.remove(streamIdentifier);
        } else {
            frag = Unpooled.EMPTY_BUFFER;
        }

        if (isComplete && !frag.isReadable()) {
            //data chunk is not fragmented
            handleAssembledMessage(ctx, msg);
        } else if (!isComplete && frag.isReadable()) {
            //more message to complete
            fragments.put(streamIdentifier, Unpooled.wrappedBuffer(frag, byteBuf));
        } else if (isComplete && frag.isReadable()) {
            //last message to complete
            fragments.remove(streamIdentifier);
            SctpMessage assembledMsg = new SctpMessage(
                    protocolIdentifier,
                    streamIdentifier,
                    Unpooled.wrappedBuffer(frag, byteBuf));
            handleAssembledMessage(ctx, assembledMsg);
        } else {
            //first incomplete message
            fragments.put(streamIdentifier, byteBuf);
        }

        byteBuf.retain();
    }

    private void handleAssembledMessage(ChannelHandlerContext ctx, SctpMessage assembledMsg) {
        ctx.nextInboundMessageBuffer().add(assembledMsg);
        assembled = true;
    }
}
