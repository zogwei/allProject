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
package io.netty.handler.codec.spdy;


/**
 * A SPDY Name/Value Header Block which provides common properties for
 * {@link SpdySynStreamFrame}, {@link SpdySynReplyFrame}, and
 * {@link SpdyHeadersFrame}.
 * @see SpdyHeaders
 */
public interface SpdyHeaderBlock {

    /**
     * Returns {@code true} if this header block is invalid.
     * A RST_STREAM frame with code PROTOCOL_ERROR should be sent.
     */
    boolean isInvalid();

    /**
     * Marks this header block as invalid.
     */
    SpdyHeaderBlock setInvalid();

    /**
     * Returns the {@link SpdyHeaders}.
     */
    SpdyHeaders headers();
}
