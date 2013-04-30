/*
 * Copyright 2013 The Netty Project
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

/**
 * Special {@link ChannelException} which will be used by {@link ChannelOutboundInvoker#flush(ChannelPromise)},
 * {@link ChannelOutboundInvoker#flush()}, {@link ChannelOutboundInvoker#write(Object)} and
 * {@link ChannelOutboundInvoker#write(Object, ChannelPromise)} if the operation was only partial successful.
 */
public class IncompleteFlushException extends ChannelException {

    private static final long serialVersionUID = -9049491093800487565L;

    public IncompleteFlushException() { }

    public IncompleteFlushException(String message) {
        super(message);
    }

    public IncompleteFlushException(String message, Throwable cause) {
        super(message, cause);
    }

    public IncompleteFlushException(Throwable cause) {
        super(cause);
    }
}
