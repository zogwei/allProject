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
package io.netty.buffer;

import java.util.Collection;
import java.util.Queue;

/**
 * Buf which operates on messages.
 *
 * @param <T>   the type of the messages that are hold by this {@link MessageBuf}
 */
public interface MessageBuf<T> extends Buf, Queue<T> {

    /**
     * Unfold the specified object if necessary, and then add the unfolded objects (or the specified object if
     * unfonding was not necessary) to this buffer. If the specified object is an object array ({@code Object[]}),
     * this method adds the elements of the array to this buffer until {@code null} is encountered.  If the specified
     * object is {@code null}, nothing is added to this buffer.  Otherwise, the specified object is added to this
     * buffer as-is.
     *
     * @return {@code true} if one or more messages were added to this buffer. {@code false} otherwise.
     */
    boolean unfoldAndAdd(Object o);

    /**
     * Drain the content of te {@link MessageBuf} to the given {@link Collection}.
     *
     * @param c         the {@link Collection} to drain the content to
     * @return number   the number of objects which was transfered
     */
    int drainTo(Collection<? super T> c);

    /**
     * Drain the content of te {@link MessageBuf} to the given {@link Collection}.
     *
     * @param c             the {@link Collection} to drain the content to
     * @param maxElements   the max number of elements to drain
     * @return number       the number of objects which was transfered
     */
    int drainTo(Collection<? super T> c, int maxElements);

    @Override
    MessageBuf<T> retain(int increment);

    @Override
    MessageBuf<T> retain();
}
