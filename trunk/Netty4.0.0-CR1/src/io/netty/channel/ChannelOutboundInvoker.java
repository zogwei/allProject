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

import io.netty.util.concurrent.EventExecutor;

import java.net.ConnectException;
import java.net.SocketAddress;
/**
 * Interface which is shared by others which need to execute outbound logic.
 */
interface ChannelOutboundInvoker {

    /**
     * Request to bind to the given {@link SocketAddress} and notify the {@link ChannelFuture} once the operation
     * completes, either because the operation was successful or because of an error.
     * <p>
     * This will result in having the
     * {@link ChannelOperationHandler#bind(ChannelHandlerContext, SocketAddress, ChannelPromise)} method
     * called of the next {@link ChannelOperationHandler} contained in the  {@link ChannelPipeline} of the
     * {@link Channel}.
     */
    ChannelFuture bind(SocketAddress localAddress);

    /**
     * Request to connect to the given {@link SocketAddress} and notify the {@link ChannelFuture} once the operation
     * completes, either because the operation was successful or because of an error.
     * <p>
     * If the connection fails because of a connection timeout, the {@link ChannelFuture} will get failed with
     * a {@link ConnectTimeoutException}. If it fails because of connection refused a {@link ConnectException}
     * will be used.
     * <p>
     * This will result in having the
     * {@link ChannelOperationHandler#connect(ChannelHandlerContext, SocketAddress, SocketAddress, ChannelPromise)}
     * method called of the next {@link ChannelOperationHandler} contained in the  {@link ChannelPipeline} of the
     * {@link Channel}.
     */
    ChannelFuture connect(SocketAddress remoteAddress);

    /**
     * Request to connect to the given {@link SocketAddress} while bind to the localAddress and notify the
     * {@link ChannelFuture} once the operation completes, either because the operation was successful or because of
     * an error.
     * <p>
     * This will result in having the
     * {@link ChannelOperationHandler#connect(ChannelHandlerContext, SocketAddress, SocketAddress, ChannelPromise)}
     * method called of the next {@link ChannelOperationHandler} contained in the  {@link ChannelPipeline} of the
     * {@link Channel}.
     */
    ChannelFuture connect(SocketAddress remoteAddress, SocketAddress localAddress);

    /**
     * Request to discconect from the remote peer and notify the {@link ChannelFuture} once the operation completes,
     * either because the operation was successful or because of an error.
     * <p>
     * This will result in having the
     * {@link ChannelOperationHandler#disconnect(ChannelHandlerContext, ChannelPromise)}
     * method called of the next {@link ChannelOperationHandler} contained in the  {@link ChannelPipeline} of the
     * {@link Channel}.
     */
    ChannelFuture disconnect();

    /**
     * Request to close this ChannelOutboundInvoker and notify the {@link ChannelFuture} once the operation completes,
     * either because the operation was successful or because of
     * an error.
     *
     * After it is closed it is not possible to reuse it again.
     * <p>
     * This will result in having the
     * {@link ChannelOperationHandler#close(ChannelHandlerContext, ChannelPromise)}
     * method called of the next {@link ChannelOperationHandler} contained in the  {@link ChannelPipeline} of the
     * {@link Channel}.
     */
    ChannelFuture close();

    /**
     * Request to deregister this ChannelOutboundInvoker from the previous assigned {@link EventExecutor} and notify the
     * {@link ChannelFuture} once the operation completes, either because the operation was successful or because of
     * an error.
     * <p>
     * This will result in having the
     * {@link ChannelOperationHandler#deregister(ChannelHandlerContext, ChannelPromise)}
     * method called of the next {@link ChannelOperationHandler} contained in the  {@link ChannelPipeline} of the
     * {@link Channel}.
     */
    ChannelFuture deregister();

    /**
     * Request to flush all pending data which belongs to this ChannelOutboundInvoker and notify the
     * {@link ChannelFuture} once the operation completes, either because the operation was successful or because of
     * an error.
     * <p>
     * Be aware that the flush could be only partially successful. In such cases the {@link ChannelFuture} will be
     * failed with an {@link IncompleteFlushException}. So if you are interested to know if it was partial successful
     * you need to check if the returned {@link ChannelFuture#cause()} returns an instance of
     * {@link IncompleteFlushException}. In such cases you may want to call {@link #flush(ChannelPromise)} or
     * {@link #flush()} to flush the rest of the data or just close the connection via {@link #close(ChannelPromise)} or
     * {@link #close()}  if it is not possible to recover.
     * <p>
     * This will result in having the
     * {@link ChannelOperationHandler#flush(ChannelHandlerContext, ChannelPromise)}
     * method called of the next {@link ChannelOperationHandler} contained in the  {@link ChannelPipeline} of the
     * {@link Channel}.
     */
    ChannelFuture flush();

    /**
     * Request to write a message via this ChannelOutboundInvoker and notify the {@link ChannelFuture}
     * once the operation completes, either because the operation was successful or because of an error.
     *
     * If you want to write a {@link FileRegion} use {@link #sendFile(FileRegion)}.
     * <p>
     * Be aware that the write could be only partially successful as the message may need to get encoded before write it
     * to the remote peer. In such cases the {@link ChannelFuture} will be failed with a
     * {@link IncompleteFlushException}. In such cases you may want to call {@link #flush(ChannelPromise)} or
     * {@link #flush()} to flush the rest of the data or just close the connection via {@link #close(ChannelPromise)}
     * or {@link #close()} if it is not possible to recover.
     * <p>
     * This will result in having the message added to the outbound buffer of the next {@link ChannelOutboundHandler}
     * and the {@link ChannelOperationHandler#flush(ChannelHandlerContext, ChannelPromise)}
     * method called of the next {@link ChannelOperationHandler} contained in the  {@link ChannelPipeline} of the
     * {@link Channel}.
     */
    ChannelFuture write(Object message);

    /**
     * Request to send a {@link FileRegion} via this ChannelOutboundInvoker and notify the {@link ChannelFuture}
     * once the operation completes, either because the operation was successful or because of an error.
     * <p>
     * This will result in having the
     * {@link ChannelOperationHandler#sendFile(ChannelHandlerContext, FileRegion, ChannelPromise)}
     * method called of the next {@link ChannelOperationHandler} contained in the  {@link ChannelPipeline} of the
     * {@link Channel}.
     */
    ChannelFuture sendFile(FileRegion region);

    /**
     * Request to bind to the given {@link SocketAddress} and notify the {@link ChannelFuture} once the operation
     * completes, either because the operation was successful or because of an error.
     *
     * The given {@link ChannelPromise} will be notified.
     * <p>
     * This will result in having the
     * {@link ChannelOperationHandler#bind(ChannelHandlerContext, SocketAddress, ChannelPromise)} method
     * called of the next {@link ChannelOperationHandler} contained in the  {@link ChannelPipeline} of the
     * {@link Channel}.
     */
    ChannelFuture bind(SocketAddress localAddress, ChannelPromise promise);

    /**
     * Request to connect to the given {@link SocketAddress} and notify the {@link ChannelFuture} once the operation
     * completes, either because the operation was successful or because of an error.
     *
     * The given {@link ChannelFuture} will be notified.
     *
     * <p>
     * If the connection fails because of a connection timeout, the {@link ChannelFuture} will get failed with
     * a {@link ConnectTimeoutException}. If it fails because of connection refused a {@link ConnectException}
     * will be used.
     * <p>
     * This will result in having the
     * {@link ChannelOperationHandler#connect(ChannelHandlerContext, SocketAddress, SocketAddress, ChannelPromise)}
     * method called of the next {@link ChannelOperationHandler} contained in the  {@link ChannelPipeline} of the
     * {@link Channel}.
     */
    ChannelFuture connect(SocketAddress remoteAddress, ChannelPromise promise);

    /**
     * Request to connect to the given {@link SocketAddress} while bind to the localAddress and notify the
     * {@link ChannelFuture} once the operation completes, either because the operation was successful or because of
     * an error.
     *
     * The given {@link ChannelPromise} will be notified and also returned.
     * <p>
     * This will result in having the
     * {@link ChannelOperationHandler#connect(ChannelHandlerContext, SocketAddress, SocketAddress, ChannelPromise)}
     * method called of the next {@link ChannelOperationHandler} contained in the  {@link ChannelPipeline} of the
     * {@link Channel}.
     */
    ChannelFuture connect(SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise);

    /**
     * Request to discconect from the remote peer and notify the {@link ChannelFuture} once the operation completes,
     * either because the operation was successful or because of an error.
     *
     * The given {@link ChannelPromise} will be notified.
     * <p>
     * This will result in having the
     * {@link ChannelOperationHandler#disconnect(ChannelHandlerContext, ChannelPromise)}
     * method called of the next {@link ChannelOperationHandler} contained in the  {@link ChannelPipeline} of the
     * {@link Channel}.
     */
    ChannelFuture disconnect(ChannelPromise promise);

    /**
     * Request to close this ChannelOutboundInvoker and notify the {@link ChannelFuture} once the operation completes,
     * either because the operation was successful or because of
     * an error.
     *
     * After it is closed it is not possible to reuse it again.
     * The given {@link ChannelPromise} will be notified.
     * <p>
     * This will result in having the
     * {@link ChannelOperationHandler#close(ChannelHandlerContext, ChannelPromise)}
     * method called of the next {@link ChannelOperationHandler} contained in the  {@link ChannelPipeline} of the
     * {@link Channel}.
     */
    ChannelFuture close(ChannelPromise promise);

    /**
     * Request to deregister this ChannelOutboundInvoker from the previous assigned {@link EventExecutor} and notify the
     * {@link ChannelFuture} once the operation completes, either because the operation was successful or because of
     * an error.
     *
     * The given {@link ChannelPromise} will be notified.
     * <p>
     * This will result in having the
     * {@link ChannelOperationHandler#deregister(ChannelHandlerContext, ChannelPromise)}
     * method called of the next {@link ChannelOperationHandler} contained in the  {@link ChannelPipeline} of the
     * {@link Channel}.
     */
    ChannelFuture deregister(ChannelPromise promise);

    /**
     * Request to Read data from the {@link Channel} into the first inbound buffer, triggers an
     * {@link ChannelStateHandler#inboundBufferUpdated(ChannelHandlerContext) inboundBufferUpdated} event if data was
     * read, and triggers an
     * {@link ChannelStateHandler#channelReadSuspended(ChannelHandlerContext) channelReadSuspended} event so the
     * handler can decide to continue reading.  If there's a pending read operation already, this method does nothing.
     * <p>
     * This will result in having the
     * {@link ChannelOperationHandler#read(ChannelHandlerContext)}
     * method called of the next {@link ChannelOperationHandler} contained in the  {@link ChannelPipeline} of the
     * {@link Channel}.
     */
    void read();

    /**
     * Request to flush all pending data which belongs to this ChannelOutboundInvoker and notify the
     * {@link ChannelFuture} once the operation completes, either because the operation was successful or because of
     * an error.
     * <p>
     * Be aware that the flush could be only partially successful. In such cases the {@link ChannelFuture} will be
     * failed with an {@link IncompleteFlushException}. So if you are interested to know if it was partial successful
     * you need to check if the returned {@link ChannelFuture#cause()} returns an instance of
     * {@link IncompleteFlushException}. In such cases you may want to call {@link #flush(ChannelPromise)} or
     * {@link #flush()} to flush the rest of the data or just close the connection via {@link #close(ChannelPromise)} or
     * {@link #close()}  if it is not possible to recover.
     *
     * The given {@link ChannelPromise} will be notified.
     * <p>
     * This will result in having the
     * {@link ChannelOperationHandler#flush(ChannelHandlerContext, ChannelPromise)}
     * method called of the next {@link ChannelOperationHandler} contained in the  {@link ChannelPipeline} of the
     * {@link Channel}.
     *
     */
    ChannelFuture flush(ChannelPromise promise);

    /**
     * Request to write a message via this ChannelOutboundInvoker and notify the {@link ChannelFuture}
     * once the operation completes, either because the operation was successful or because of an error.
     *
     * If you want to write a {@link FileRegion} use {@link #sendFile(FileRegion)}.
     * <p>
     * Be aware that the write could be only partially successful as the message may need to get encoded before write it
     * to the remote peer. In such cases the {@link ChannelFuture} will be failed with a
     * {@link IncompleteFlushException}. In such cases you may want to call {@link #flush(ChannelPromise)} or
     * {@link #flush()} to flush the rest of the data or just close the connection via {@link #close(ChannelPromise)}
     * or {@link #close()} if it is not possible to recover.
     *
     * The given {@link ChannelPromise} will be notified.
     * <p>
     * This will result in having the message added to the outbound buffer of the next {@link ChannelOutboundHandler}
     * and the {@link ChannelOperationHandler#flush(ChannelHandlerContext, ChannelPromise)}
     * method called of the next {@link ChannelOperationHandler} contained in the  {@link ChannelPipeline} of the
     * {@link Channel}.
     */
    ChannelFuture write(Object message, ChannelPromise promise);

    /**
     * Request to send a {@link FileRegion} via this ChannelOutboundInvoker and notify the {@link ChannelFuture}
     * once the operation completes, either because the operation was successful or because of an error.
     *
     * The given {@link ChannelPromise} will be notified.
     * <p>
     * This will result in having the
     * {@link ChannelOperationHandler#sendFile(ChannelHandlerContext, FileRegion, ChannelPromise)}
     * method called of the next {@link ChannelOperationHandler} contained in the  {@link ChannelPipeline} of the
     * {@link Channel}.
     */
    ChannelFuture sendFile(FileRegion region, ChannelPromise promise);
}
