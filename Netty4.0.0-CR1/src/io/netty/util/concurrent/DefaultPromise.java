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
package io.netty.util.concurrent;

import io.netty.util.Signal;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.*;


public class DefaultPromise<V> extends AbstractFuture<V> implements Promise<V> {

    private static final InternalLogger logger =
        InternalLoggerFactory.getInstance(DefaultPromise.class);

    private static final int MAX_LISTENER_STACK_DEPTH = 8;
    private static final ThreadLocal<Integer> LISTENER_STACK_DEPTH = new ThreadLocal<Integer>() {
        @Override
        protected Integer initialValue() {
            return 0;
        }
    };
    private static final Signal SUCCESS = new Signal(DefaultPromise.class.getName() + ".SUCCESS");
    private final EventExecutor executor;

    private volatile Object result;
    private Object listeners; // Can be ChannelFutureListener or DefaultChannelPromiseListeners

    /**
     * The the most significant 24 bits of this field represents the number of waiter threads waiting for this promise
     * with await*() and sync*().  Subclasses can use the other 40 bits of this field to represents its own state, and
     * are responsible for retaining the most significant 24 bits as is when modifying this field.
     */
    protected long state;

    /**
     * Creates a new instance.
     *
     * It is preferable to use {@link EventExecutor#newPromise()} to create a new promise
     *
     * @param executor
     *        the {@link EventExecutor} which is used to notify the promise once it is complete
     */
    public DefaultPromise(EventExecutor executor) {
        if (executor == null) {
            throw new NullPointerException("executor");
        }
        this.executor = executor;
    }

    protected DefaultPromise() {
        // only for subclasses
        executor = null;
    }

    protected EventExecutor executor() {
        return executor;
    }

    @Override
    public boolean isDone() {
        return result != null;
    }

    @Override
    public boolean isSuccess() {
        Object result = this.result;
        if (result == null) {
            return false;
        }
        return !(result instanceof CauseHolder);
    }

    @Override
    public Throwable cause() {
        Object cause = result;
        if (cause instanceof CauseHolder) {
            return ((CauseHolder) cause).cause;
        }
        return null;
    }

    @Override
    public Promise<V> addListener(GenericFutureListener<? extends Future<V>> listener) {
        if (listener == null) {
            throw new NullPointerException("listener");
        }

        if (isDone()) {
            notifyListener(executor(), this, listener);
            return this;
        }

        synchronized (this) {
            if (!isDone()) {
                if (listeners == null) {
                    listeners = listener;
                } else {
                    if (listeners instanceof DefaultPromiseListeners) {
                        ((DefaultPromiseListeners) listeners).add(listener);
                    } else {
                        listeners = new DefaultPromiseListeners(
                                (GenericFutureListener<? extends Future<V>>) listeners, listener);
                    }
                }
                return this;
            }
        }

        notifyListener(executor(), this, listener);
        return this;
    }

    @Override
    public Promise<V> addListeners(GenericFutureListener<? extends Future<V>>... listeners) {
        if (listeners == null) {
            throw new NullPointerException("listeners");
        }

        for (GenericFutureListener<? extends Future<V>> l: listeners) {
            if (l == null) {
                break;
            }
            addListener(l);
        }
        return this;
    }

    @Override
    public Promise<V> removeListener(GenericFutureListener<? extends Future<V>> listener) {
        if (listener == null) {
            throw new NullPointerException("listener");
        }

        if (isDone()) {
            return this;
        }

        synchronized (this) {
            if (!isDone()) {
                if (listeners instanceof DefaultPromiseListeners) {
                    ((DefaultPromiseListeners) listeners).remove(listener);
                } else if (listeners == listener) {
                    listeners = null;
                }
            }
        }

        return this;
    }

    @Override
    public Promise<V> removeListeners(GenericFutureListener<? extends Future<V>>... listeners) {
        if (listeners == null) {
            throw new NullPointerException("listeners");
        }

        for (GenericFutureListener<? extends Future<V>> l: listeners) {
            if (l == null) {
                break;
            }
            removeListener(l);
        }
        return this;
    }

    @Override
    public Promise<V> sync() throws InterruptedException {
        await();
        rethrowIfFailed();
        return this;
    }

    @Override
    public Promise<V> syncUninterruptibly() {
        awaitUninterruptibly();
        rethrowIfFailed();
        return this;
    }

    private void rethrowIfFailed() {
        Throwable cause = cause();
        if (cause == null) {
            return;
        }

        PlatformDependent.throwException(cause);
    }

    @Override
    public Promise<V> await() throws InterruptedException {
        if (isDone()) {
            return this;
        }

        if (Thread.interrupted()) {
            throw new InterruptedException();
        }

        synchronized (this) {
            while (!isDone()) {
                checkDeadLock();
                incWaiters();
                try {
                    wait();
                } finally {
                    decWaiters();
                }
            }
        }
        return this;
    }

    @Override
    public boolean await(long timeout, TimeUnit unit)
            throws InterruptedException {
        return await0(unit.toNanos(timeout), true);
    }

    @Override
    public boolean await(long timeoutMillis) throws InterruptedException {
        return await0(MILLISECONDS.toNanos(timeoutMillis), true);
    }

    @Override
    public Promise<V> awaitUninterruptibly() {
        if (isDone()) {
            return this;
        }

        boolean interrupted = false;
        synchronized (this) {
            while (!isDone()) {
                checkDeadLock();
                incWaiters();
                try {
                    wait();
                } catch (InterruptedException e) {
                    interrupted = true;
                } finally {
                    decWaiters();
                }
            }
        }

        if (interrupted) {
            Thread.currentThread().interrupt();
        }

        return this;
    }

    @Override
    public boolean awaitUninterruptibly(long timeout, TimeUnit unit) {
        try {
            return await0(unit.toNanos(timeout), false);
        } catch (InterruptedException e) {
            throw new InternalError();
        }
    }

    @Override
    public boolean awaitUninterruptibly(long timeoutMillis) {
        try {
            return await0(MILLISECONDS.toNanos(timeoutMillis), false);
        } catch (InterruptedException e) {
            throw new InternalError();
        }
    }

    private boolean await0(long timeoutNanos, boolean interruptable) throws InterruptedException {
        if (isDone()) {
            return true;
        }

        if (timeoutNanos <= 0) {
            return isDone();
        }

        if (interruptable && Thread.interrupted()) {
            throw new InterruptedException();
        }

        long startTime = timeoutNanos <= 0 ? 0 : System.nanoTime();
        long waitTime = timeoutNanos;
        boolean interrupted = false;

        try {
            synchronized (this) {
                if (isDone()) {
                    return true;
                }

                if (waitTime <= 0) {
                    return isDone();
                }

                checkDeadLock();
                incWaiters();
                try {
                    for (;;) {
                        try {
                            wait(waitTime / 1000000, (int) (waitTime % 1000000));
                        } catch (InterruptedException e) {
                            if (interruptable) {
                                throw e;
                            } else {
                                interrupted = true;
                            }
                        }

                        if (isDone()) {
                            return true;
                        } else {
                            waitTime = timeoutNanos - (System.nanoTime() - startTime);
                            if (waitTime <= 0) {
                                return isDone();
                            }
                        }
                    }
                } finally {
                    decWaiters();
                }
            }
        } finally {
            if (interrupted) {
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * Do deadlock checks
     */
    protected void checkDeadLock() {
        EventExecutor e = executor();
        if (e != null && e.inEventLoop()) {
            throw new BlockingOperationException();
        }
    }

    @Override
    public Promise<V> setSuccess(V result) {
        if (setSuccess0(result)) {
            notifyListeners();
            return this;
        }
        throw new IllegalStateException("complete already");
    }

    @Override
    public boolean trySuccess(V result) {
        if (setSuccess0(result)) {
            notifyListeners();
            return true;
        }
        return false;
    }

    @Override
    public Promise<V> setFailure(Throwable cause) {
        if (setFailure0(cause)) {
            notifyListeners();
            return this;
        }
        throw new IllegalStateException("complete already", cause);
    }

    @Override
    public boolean tryFailure(Throwable cause) {
        if (setFailure0(cause)) {
            notifyListeners();
            return true;
        }
        return false;
    }

    private boolean setFailure0(Throwable cause) {
        if (isDone()) {
            return false;
        }

        synchronized (this) {
            // Allow only once.
            if (isDone()) {
                return false;
            }

            result = new CauseHolder(cause);
            if (hasWaiters()) {
                notifyAll();
            }
        }
        return true;
    }

    private boolean setSuccess0(V result) {
        if (isDone()) {
            return false;
        }

        synchronized (this) {
            // Allow only once.
            if (isDone()) {
                return false;
            }
            if (result == null) {
                this.result = SUCCESS;
            } else {
                this.result = result;
            }
            if (hasWaiters()) {
                notifyAll();
            }
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public V getNow() {
        Object result = this.result;
        if (result instanceof CauseHolder || result == SUCCESS) {
            return null;
        }
        return (V) result;
    }

    private boolean hasWaiters() {
        return (state & 0xFFFFFF0000000000L) != 0;
    }

    private void incWaiters() {
        long newState = state + 0x10000000000L;
        if ((newState & 0xFFFFFF0000000000L) == 0) {
            throw new IllegalStateException("too many waiters");
        }
        state = newState;
    }

    private void decWaiters() {
        state -= 0x10000000000L;
    }

    private void notifyListeners() {
        // This method doesn't need synchronization because:
        // 1) This method is always called after synchronized (this) block.
        //    Hence any listener list modification happens-before this method.
        // 2) This method is called only when 'done' is true.  Once 'done'
        //    becomes true, the listener list is never modified - see add/removeListener()

        if (listeners == null) {
            return;
        }

        EventExecutor executor = executor();
        if (executor.inEventLoop()) {
            if (listeners instanceof DefaultPromiseListeners) {
                notifyListeners0(this, (DefaultPromiseListeners) listeners);
            } else {
                notifyListener0(this, (GenericFutureListener<? extends Future<V>>) listeners);
            }
            listeners = null;
        } else {
            final Object listeners = this.listeners;
            this.listeners = null;
            try {
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        if (listeners instanceof DefaultPromiseListeners) {
                            notifyListeners0(DefaultPromise.this, (DefaultPromiseListeners) listeners);
                        } else {
                            notifyListener0(
                                    DefaultPromise.this, (GenericFutureListener<? extends Future<V>>) listeners);
                        }
                    }
                });
            } catch (Throwable t) {
                logger.error("Failed to notify listener(s). Event loop terminated?", t);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private static void notifyListeners0(final Future<?> future,
                                         DefaultPromiseListeners listeners) {
        final GenericFutureListener<? extends Future<?>>[] a = listeners.listeners();
        final int size = listeners.size();
        for (int i = 0; i < size; i ++) {
            notifyListener0(future, a[i]);
        }
    }

    @SuppressWarnings("unchecked")
    protected static void notifyListener(
            final EventExecutor eventExecutor, final Future<?> future,
            final GenericFutureListener<? extends Future<?>> l) {

        if (eventExecutor.inEventLoop()) {
            final Integer stackDepth = LISTENER_STACK_DEPTH.get();
            if (stackDepth < MAX_LISTENER_STACK_DEPTH) {
                LISTENER_STACK_DEPTH.set(stackDepth + 1);
                try {
                    notifyListener0(future, l);
                } finally {
                    LISTENER_STACK_DEPTH.set(stackDepth);
                }
                return;
            }
        }

        try {
            eventExecutor.execute(new Runnable() {
            @Override
            public void run() {
                notifyListener(eventExecutor, future, l);
            }
        });
        } catch (Throwable t) {
            logger.error("Failed to notify a listener. Event loop terminated?", t);
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private static void notifyListener0(Future future, GenericFutureListener l) {
        try {
            l.operationComplete(future);
        } catch (Throwable t) {
            if (logger.isWarnEnabled()) {
                logger.warn(
                        "An exception was thrown by " +
                                GenericFutureListener.class.getSimpleName() + '.', t);
            }
        }
    }

    private static final class CauseHolder {
        final Throwable cause;
        private CauseHolder(Throwable cause) {
            this.cause = cause;
        }
    }
}
