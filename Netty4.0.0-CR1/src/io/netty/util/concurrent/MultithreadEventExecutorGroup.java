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
package io.netty.util.concurrent;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Abstract base class for {@link EventExecutorGroup} implementations that handles their tasks with multiple threads at
 * the same time.
 */
public abstract class MultithreadEventExecutorGroup implements EventExecutorGroup {

    public static final int DEFAULT_POOL_SIZE = Runtime.getRuntime().availableProcessors() * 2;
    private static final AtomicInteger poolId = new AtomicInteger();

    private final EventExecutor[] children;
    private final AtomicInteger childIndex = new AtomicInteger();

    /**
     * Create a new instance.
     *
     * @param nThreads          the number of threads that will be used by this instance. Use 0 for the default number
     *                          of {@link #DEFAULT_POOL_SIZE}
     * @param threadFactory     the ThreadFactory to use, or {@code null} if the default should be used.
     * @param args              arguments which will passed to each {@link #newChild(ThreadFactory, Object...)} call
     */
    protected MultithreadEventExecutorGroup(int nThreads, ThreadFactory threadFactory, Object... args) {
        if (nThreads < 0) {
            throw new IllegalArgumentException(String.format(
                    "nThreads: %d (expected: >= 0)", nThreads));
        }

        if (nThreads == 0) {
            nThreads = DEFAULT_POOL_SIZE;
        }
        if (threadFactory == null) {
            threadFactory = new DefaultThreadFactory();
        }

        children = new SingleThreadEventExecutor[nThreads];
        for (int i = 0; i < nThreads; i ++) {
            boolean success = false;
            try {
                children[i] = newChild(threadFactory, args);
                success = true;
            } catch (Exception e) {
                // TODO: Think about if this is a good exception type
                throw new IllegalStateException("failed to create a child event loop", e);
            } finally {
                if (!success) {
                    for (int j = 0; j < i; j ++) {
                        children[j].shutdown();
                    }
                }
            }
        }
    }

    @Override
    public EventExecutor next() {
        return children[Math.abs(childIndex.getAndIncrement() % children.length)];
    }

    /**
     * Return a safe-copy of all of the children of this group.
     */
    protected Set<EventExecutor> children() {
        Set<EventExecutor> children = Collections.newSetFromMap(new LinkedHashMap<EventExecutor, Boolean>());
        Collections.addAll(children, this.children);
        return children;
    }

    /**
     * Create a new EventExecutor which will later then accessable via the {@link #next()}  method. This method will be
     * called for each thread that will serve this {@link MultithreadEventExecutorGroup}.
     *
     */
    protected abstract EventExecutor newChild(
            ThreadFactory threadFactory, Object... args) throws Exception;

    @Override
    public void shutdown() {
        if (isShutdown()) {
            return;
        }

        for (EventExecutor l: children) {
            l.shutdown();
        }
    }

    @Override
    public boolean isShutdown() {
        for (EventExecutor l: children) {
            if (!l.isShutdown()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isTerminated() {
        for (EventExecutor l: children) {
            if (!l.isTerminated()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean awaitTermination(long timeout, TimeUnit unit)
            throws InterruptedException {
        long deadline = System.nanoTime() + unit.toNanos(timeout);
        loop: for (EventExecutor l: children) {
            for (;;) {
                long timeLeft = deadline - System.nanoTime();
                if (timeLeft <= 0) {
                    break loop;
                }
                if (l.awaitTermination(timeLeft, TimeUnit.NANOSECONDS)) {
                    break;
                }
            }
        }
        return isTerminated();
    }

    private final class DefaultThreadFactory implements ThreadFactory {
        private final AtomicInteger nextId = new AtomicInteger();
        private final String prefix;

        DefaultThreadFactory() {
            String typeName = MultithreadEventExecutorGroup.this.getClass().getSimpleName();
            typeName = Character.toLowerCase(typeName.charAt(0)) + typeName.substring(1);
            prefix = typeName + '-' + poolId.incrementAndGet() + '-';
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r, prefix + nextId.incrementAndGet());
            try {
                if (t.isDaemon()) {
                    t.setDaemon(false);
                }
                if (t.getPriority() != Thread.MAX_PRIORITY) {
                    t.setPriority(Thread.MAX_PRIORITY);
                }
            } catch (Exception ignored) {
                // Doesn't matter even if failed to set.
            }
            return t;
        }
    }
}
