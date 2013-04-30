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

import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.Delayed;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Abstract base class for {@link EventExecutor}'s that execute all its submitted tasks in a single thread.
 *
 */
public abstract class SingleThreadEventExecutor extends AbstractEventExecutor {

    private static final InternalLogger logger =
            InternalLoggerFactory.getInstance(SingleThreadEventExecutor.class);

    /**
     * Wait at least 2 seconds after shutdown() until there are no pending tasks anymore.
     * @see #confirmShutdown()
     */
    private static final long SHUTDOWN_DELAY_NANOS = TimeUnit.SECONDS.toNanos(2);

    static final ThreadLocal<SingleThreadEventExecutor> CURRENT_EVENT_LOOP =
            new ThreadLocal<SingleThreadEventExecutor>();

    private static final int ST_NOT_STARTED = 1;
    private static final int ST_STARTED = 2;
    private static final int ST_SHUTDOWN = 3;
    private static final int ST_TERMINATED = 4;

    private static final Runnable WAKEUP_TASK = new Runnable() {
        @Override
        public void run() {
            // Do nothing.
        }
    };

    /**
     * Return the {@link SingleThreadEventExecutor} which belongs the current {@link Thread}.
     */
    public static SingleThreadEventExecutor currentEventLoop() {
        return CURRENT_EVENT_LOOP.get();
    }

    private final EventExecutorGroup parent;
    private final Queue<Runnable> taskQueue;
    final Queue<ScheduledFutureTask<?>> delayedTaskQueue = new PriorityQueue<ScheduledFutureTask<?>>();

    private final Thread thread;
    private final Object stateLock = new Object();
    private final Semaphore threadLock = new Semaphore(0);
    private final Set<Runnable> shutdownHooks = new LinkedHashSet<Runnable>();
    private volatile int state = ST_NOT_STARTED;
    private long lastAccessTimeNanos;

    /**
     * Create a new instance
     *
     * @param parent            the {@link EventExecutorGroup} which is the parent of this instance and belongs to it
     * @param threadFactory     the {@link ThreadFactory} which will be used for the used {@link Thread}
     */
    protected SingleThreadEventExecutor(EventExecutorGroup parent, ThreadFactory threadFactory) {
        if (threadFactory == null) {
            throw new NullPointerException("threadFactory");
        }

        this.parent = parent;

        thread = threadFactory.newThread(new Runnable() {
            @Override
            public void run() {
                CURRENT_EVENT_LOOP.set(SingleThreadEventExecutor.this);
                boolean success = false;
                try {
                    SingleThreadEventExecutor.this.run();
                    success = true;
                } catch (Throwable t) {
                    logger.warn("Unexpected exception from an event executor: ", t);
                    shutdown();
                } finally {
                    // Check if confirmShutdown() was called at the end of the loop.
                    if (success && lastAccessTimeNanos == 0) {
                        logger.error(
                                "Buggy " + EventExecutor.class.getSimpleName() + " implementation; " +
                                SingleThreadEventExecutor.class.getSimpleName() + ".confirmShutdown() must be called " +
                                "before run() implementation terminates.");
                    }

                    try {
                        // Run all remaining tasks and shutdown hooks.
                        for (;;) {
                            if (confirmShutdown()) {
                                break;
                            }
                        }
                        synchronized (stateLock) {
                            state = ST_TERMINATED;
                        }
                    } finally {
                        try {
                            cleanup();
                        } finally {
                            threadLock.release();
                            if (!taskQueue.isEmpty()) {
                                logger.warn(
                                        "An event executor terminated with " +
                                        "non-empty task queue (" + taskQueue.size() + ')');
                            }
                        }
                    }
                }
            }
        });

        taskQueue = newTaskQueue();
    }

    /**
     * Create a new {@link Queue} which will holds the tasks to execute. This default implementation will return a
     * {@link LinkedBlockingQueue} but if your sub-class of {@link SingleThreadEventExecutor} will not do any blocking
     * calls on the this {@link Queue} it may make sense to {@code @Override} this and return some more performant
     * implementation that does not support blocking operations at all.
     */
    protected Queue<Runnable> newTaskQueue() {
        return new LinkedBlockingQueue<Runnable>();
    }

    @Override
    public EventExecutorGroup parent() {
        return parent;
    }

    /**
     * Interrupt the current running {@link Thread}.
     */
    protected void interruptThread() {
        thread.interrupt();
    }

    /**
     * @see {@link Queue#poll()}
     */
    protected Runnable pollTask() {
        assert inEventLoop();
        for (;;) {
            Runnable task = taskQueue.poll();
            if (task == WAKEUP_TASK) {
                continue;
            }
            return task;
        }
    }

    /**
     * Take the next {@link Runnable} from the task queue and so will block if no task is currently present.
     *
     * Be aware that this method will throw an {@link UnsupportedOperationException} if the task queue, which was
     * created via {@link #newTaskQueue()}, does not implement {@link BlockingQueue}.
     */
    protected Runnable takeTask() throws InterruptedException {
        assert inEventLoop();
        if (!(taskQueue instanceof BlockingQueue)) {
            throw new UnsupportedOperationException();
        }

        BlockingQueue<Runnable> taskQueue = (BlockingQueue<Runnable>) this.taskQueue;
        for (;;) {
            ScheduledFutureTask<?> delayedTask = delayedTaskQueue.peek();
            if (delayedTask == null) {
                return taskQueue.take();
            } else {
                long delayNanos = delayedTask.delayNanos();
                Runnable task;
                if (delayNanos > 0) {
                    task = taskQueue.poll(delayNanos, TimeUnit.NANOSECONDS);
                } else {
                    task = taskQueue.poll();
                }

                if (task == null) {
                    fetchFromDelayedQueue();
                    task = taskQueue.poll();
                }

                if (task != null) {
                    return task;
                }
            }
        }
    }

    private void fetchFromDelayedQueue() {
        long nanoTime = 0L;
        for (;;) {
            ScheduledFutureTask<?> delayedTask = delayedTaskQueue.peek();
            if (delayedTask == null) {
                break;
            }

            if (nanoTime == 0L) {
                nanoTime = nanoTime();
            }

            if (delayedTask.deadlineNanos() <= nanoTime) {
                delayedTaskQueue.remove();
                taskQueue.add(delayedTask);
            } else {
                break;
            }
        }
    }

    /**
     * @see {@link Queue#peek()}
     */
    protected Runnable peekTask() {
        assert inEventLoop();
        return taskQueue.peek();
    }

    /**
     * @see {@link Queue#isEmpty()}
     */
    protected boolean hasTasks() {
        assert inEventLoop();
        return !taskQueue.isEmpty();
    }

    /**
     * Add a task to the task queue, or throws a {@link RejectedExecutionException} if this instance was shutdown
     * before.
     */
    protected void addTask(Runnable task) {
        if (task == null) {
            throw new NullPointerException("task");
        }
        if (isTerminated()) {
            reject();
        }
        taskQueue.add(task);
    }

    /**
     * @see {@link Queue#remove(Object)}
     */
    protected boolean removeTask(Runnable task) {
        if (task == null) {
            throw new NullPointerException("task");
        }
        return taskQueue.remove(task);
    }

    /**
     * Poll all tasks from the task queue and run them via {@link Runnable#run()} method.
     *
     * @return {@code true} if and only if at least one task was run
     */
    protected boolean runAllTasks() {
        fetchFromDelayedQueue();
        Runnable task = pollTask();
        if (task == null) {
            return false;
        }

        for (;;) {
            try {
                task.run();
            } catch (Throwable t) {
                logger.warn("A task raised an exception.", t);
            }

            task = pollTask();
            if (task == null) {
                return true;
            }
        }
    }

    /**
     * Poll all tasks from the task queue and run them via {@link Runnable#run()} method.  This method stops running
     * the tasks in the task queue and returns if it ran longer than {@code timeoutNanos}.
     */
    protected boolean runAllTasks(long timeoutNanos) {
        fetchFromDelayedQueue();
        Runnable task = pollTask();
        if (task == null) {
            return false;
        }

        final long deadline = System.nanoTime() + timeoutNanos;
        long runTasks = 0;
        for (;;) {
            try {
                task.run();
            } catch (Throwable t) {
                logger.warn("A task raised an exception.", t);
            }

            runTasks ++;

            // Check timeout every 64 tasks because System.nanoTime() is relatively expensive.
            // XXX: Hard-coded value - will make it configurable if it is really a problem.
            if ((runTasks & 0x3F) == 0) {
                if (System.nanoTime() >= deadline) {
                    break;
                }
            }

            task = pollTask();
            if (task == null) {
                break;
            }
        }

        return true;
    }

    /**
     * Returns the ammount of time left until the scheduled task with the closest dead line is executed.
     */
    protected long delayNanos(long currentTimeNanos) {
        ScheduledFutureTask<?> delayedTask = delayedTaskQueue.peek();
        if (delayedTask == null) {
            return SCHEDULE_PURGE_INTERVAL;
        }

        return delayedTask.delayNanos(currentTimeNanos);
    }

    /**
     *
     */
    protected abstract void run();

    /**
     * Do nothing, sub-classes may override
     */
    protected void cleanup() {
        // NOOP
    }

    protected void wakeup(boolean inEventLoop) {
        if (!inEventLoop || state == ST_SHUTDOWN) {
            addTask(WAKEUP_TASK);
        }
    }

    @Override
    public boolean inEventLoop() {
        return inEventLoop(Thread.currentThread());
    }

    @Override
    public boolean inEventLoop(Thread thread) {
        return thread == this.thread;
    }

    /**
     * Add a {@link Runnable} which will be executed on shutdown of this instance
     */
    public void addShutdownHook(final Runnable task) {
        if (inEventLoop()) {
            shutdownHooks.add(task);
        } else {
            execute(new Runnable() {
                @Override
                public void run() {
                    shutdownHooks.add(task);
                }
            });
        }
    }

    /**
     * Remove a previous added {@link Runnable} as a shutdown hook
     */
    public void removeShutdownHook(final Runnable task) {
        if (inEventLoop()) {
            shutdownHooks.remove(task);
        } else {
            execute(new Runnable() {
                @Override
                public void run() {
                    shutdownHooks.remove(task);
                }
            });
        }
    }

    private boolean runShutdownHooks() {
        boolean ran = false;
        // Note shutdown hooks can add / remove shutdown hooks.
        while (!shutdownHooks.isEmpty()) {
            List<Runnable> copy = new ArrayList<Runnable>(shutdownHooks);
            shutdownHooks.clear();
            for (Runnable task: copy) {
                try {
                    task.run();
                    ran = true;
                } catch (Throwable t) {
                    logger.warn("Shutdown hook raised an exception.", t);
                }
            }
        }
        return ran;
    }

    @Override
    public void shutdown() {
        if (isShutdown()) {
            return;
        }

        boolean inEventLoop = inEventLoop();
        boolean wakeup = true;

        if (inEventLoop) {
            synchronized (stateLock) {
                assert state == ST_STARTED;
                state = ST_SHUTDOWN;
            }
        } else {
            synchronized (stateLock) {
                switch (state) {
                case ST_NOT_STARTED:
                    state = ST_SHUTDOWN;
                    thread.start();
                    break;
                case ST_STARTED:
                    state = ST_SHUTDOWN;
                    break;
                default:
                    wakeup = false;
                }
            }
        }

        if (wakeup) {
            wakeup(inEventLoop);
        }
    }

    @Override
    public List<Runnable> shutdownNow() {
        shutdown();
        return Collections.emptyList();
    }

    @Override
    public boolean isShutdown() {
        return state >= ST_SHUTDOWN;
    }

    @Override
    public boolean isTerminated() {
        return state == ST_TERMINATED;
    }

    /**
     * Confirm that the shutdown if the instance should be done now!
     */
    protected boolean confirmShutdown() {
        if (!isShutdown()) {
            throw new IllegalStateException("must be invoked after shutdown()");
        }
        if (!inEventLoop()) {
            throw new IllegalStateException("must be invoked from an event loop");
        }

        cancelDelayedTasks();

        if (runAllTasks() || runShutdownHooks()) {
            // There were tasks in the queue. Wait a little bit more until no tasks are queued for SHUTDOWN_DELAY_NANOS.
            lastAccessTimeNanos = 0;
            wakeup(true);
            return false;
        }

        if (lastAccessTimeNanos == 0 || System.nanoTime() - lastAccessTimeNanos < SHUTDOWN_DELAY_NANOS) {
            if (lastAccessTimeNanos == 0) {
                lastAccessTimeNanos = System.nanoTime();
            }

            // Check if any tasks were added to the queue every 100ms.
            // TODO: Change the behavior of takeTask() so that it returns on timeout.
            wakeup(true);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                // Ignore
            }

            return false;
        }

        // No tasks were added for last SHUTDOWN_DELAY_NANOS - hopefully safe to shut down.
        // (Hopefully because we really cannot make a guarantee that there will be no execute() calls by a user.)
        return true;
    }

    private void cancelDelayedTasks() {
        if (delayedTaskQueue.isEmpty()) {
            return;
        }

        final ScheduledFutureTask<?>[] delayedTasks =
                delayedTaskQueue.toArray(new ScheduledFutureTask<?>[delayedTaskQueue.size()]);

        for (ScheduledFutureTask<?> task: delayedTasks) {
            task.cancel(false);
        }

        delayedTaskQueue.clear();
    }

    @Override
    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        if (unit == null) {
            throw new NullPointerException("unit");
        }

        if (inEventLoop()) {
            throw new IllegalStateException("cannot await termination of the current thread");
        }

        if (threadLock.tryAcquire(timeout, unit)) {
            threadLock.release();
        }

        return isTerminated();
    }

    @Override
    public void execute(Runnable task) {
        if (task == null) {
            throw new NullPointerException("task");
        }

        if (inEventLoop()) {
            addTask(task);
            wakeup(true);
        } else {
            startThread();
            addTask(task);
            if (isTerminated() && removeTask(task)) {
                reject();
            }
            wakeup(false);
        }
    }

    protected static void reject() {
        throw new RejectedExecutionException("event executor terminated");
    }

    // ScheduledExecutorService implementation

    private static final long SCHEDULE_PURGE_INTERVAL = TimeUnit.SECONDS.toNanos(1);
    private static final long START_TIME = System.nanoTime();
    private static final AtomicLong nextTaskId = new AtomicLong();
    private static long nanoTime() {
        return System.nanoTime() - START_TIME;
    }

    private static long deadlineNanos(long delay) {
        return nanoTime() + delay;
    }

    @Override
    public ScheduledFuture<?> schedule(Runnable command, long delay, TimeUnit unit) {
        if (command == null) {
            throw new NullPointerException("command");
        }
        if (unit == null) {
            throw new NullPointerException("unit");
        }
        if (delay < 0) {
            throw new IllegalArgumentException(
                    String.format("delay: %d (expected: >= 0)", delay));
        }
        return schedule(new ScheduledFutureTask<Void>(this, command, null, deadlineNanos(unit.toNanos(delay))));
    }

    @Override
    public <V> ScheduledFuture<V> schedule(Callable<V> callable, long delay, TimeUnit unit) {
        if (callable == null) {
            throw new NullPointerException("callable");
        }
        if (unit == null) {
            throw new NullPointerException("unit");
        }
        if (delay < 0) {
            throw new IllegalArgumentException(
                    String.format("delay: %d (expected: >= 0)", delay));
        }
        return schedule(new ScheduledFutureTask<V>(this, callable, deadlineNanos(unit.toNanos(delay))));
    }

    @Override
    public ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit) {
        if (command == null) {
            throw new NullPointerException("command");
        }
        if (unit == null) {
            throw new NullPointerException("unit");
        }
        if (initialDelay < 0) {
            throw new IllegalArgumentException(
                    String.format("initialDelay: %d (expected: >= 0)", initialDelay));
        }
        if (period <= 0) {
            throw new IllegalArgumentException(
                    String.format("period: %d (expected: > 0)", period));
        }

        return schedule(new ScheduledFutureTask<Void>(
                this, Executors.<Void>callable(command, null),
                deadlineNanos(unit.toNanos(initialDelay)), unit.toNanos(period)));
    }

    @Override
    public ScheduledFuture<?> scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit) {
        if (command == null) {
            throw new NullPointerException("command");
        }
        if (unit == null) {
            throw new NullPointerException("unit");
        }
        if (initialDelay < 0) {
            throw new IllegalArgumentException(
                    String.format("initialDelay: %d (expected: >= 0)", initialDelay));
        }
        if (delay <= 0) {
            throw new IllegalArgumentException(
                    String.format("delay: %d (expected: > 0)", delay));
        }

        return schedule(new ScheduledFutureTask<Void>(
                this, Executors.<Void>callable(command, null),
                deadlineNanos(unit.toNanos(initialDelay)), -unit.toNanos(delay)));
    }

    private <V> ScheduledFuture<V> schedule(final ScheduledFutureTask<V> task) {
        if (task == null) {
            throw new NullPointerException("task");
        }

        if (inEventLoop()) {
            delayedTaskQueue.add(task);
        } else {
            execute(new Runnable() {
                @Override
                public void run() {
                    delayedTaskQueue.add(task);
                }
            });
        }

        return task;
    }

    private void startThread() {
        synchronized (stateLock) {
            if (state == ST_NOT_STARTED) {
                state = ST_STARTED;
                delayedTaskQueue.add(new ScheduledFutureTask<Void>(
                        this, Executors.<Void>callable(new PurgeTask(), null),
                        deadlineNanos(SCHEDULE_PURGE_INTERVAL), -SCHEDULE_PURGE_INTERVAL));
                thread.start();
            }
        }
    }

    private static final class ScheduledFutureTask<V> extends PromiseTask<V> implements ScheduledFuture<V> {

        @SuppressWarnings("rawtypes")
        private static final AtomicIntegerFieldUpdater<ScheduledFutureTask> uncancellableUpdater =
                AtomicIntegerFieldUpdater.newUpdater(ScheduledFutureTask.class, "uncancellable");

        private final long id = nextTaskId.getAndIncrement();
        private long deadlineNanos;
        /* 0 - no repeat, >0 - repeat at fixed rate, <0 - repeat with fixed delay */
        private final long periodNanos;
        @SuppressWarnings("UnusedDeclaration")
        private volatile int uncancellable;

        ScheduledFutureTask(SingleThreadEventExecutor executor, Runnable runnable, V result, long nanoTime) {
            this(executor, Executors.callable(runnable, result), nanoTime);
        }

        ScheduledFutureTask(SingleThreadEventExecutor executor, Callable<V> callable, long nanoTime, long period) {
            super(executor, callable);
            if (period == 0) {
                throw new IllegalArgumentException("period: 0 (expected: != 0)");
            }
            deadlineNanos = nanoTime;
            periodNanos = period;
        }

        ScheduledFutureTask(SingleThreadEventExecutor executor, Callable<V> callable, long nanoTime) {
            super(executor, callable);
            deadlineNanos = nanoTime;
            periodNanos = 0;
        }

        @Override
        protected SingleThreadEventExecutor executor() {
            return (SingleThreadEventExecutor) super.executor();
        }

        public long deadlineNanos() {
            return deadlineNanos;
        }

        public long delayNanos() {
            return Math.max(0, deadlineNanos() - nanoTime());
        }

        public long delayNanos(long currentTimeNanos) {
            return Math.max(0, deadlineNanos() - (currentTimeNanos - START_TIME));
        }

        @Override
        public long getDelay(TimeUnit unit) {
            return unit.convert(delayNanos(), TimeUnit.NANOSECONDS);
        }

        @Override
        public int compareTo(Delayed o) {
            if (this == o) {
                return 0;
            }

            ScheduledFutureTask<?> that = (ScheduledFutureTask<?>) o;
            long d = deadlineNanos() - that.deadlineNanos();
            if (d < 0) {
                return -1;
            } else if (d > 0) {
                return 1;
            } else if (id < that.id) {
                return -1;
            } else if (id == that.id) {
                throw new Error();
            } else {
                return 1;
            }
        }

        @Override
        public int hashCode() {
            return super.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            return super.equals(obj);
        }

        @Override
        public void run() {
            assert executor().inEventLoop();
            try {
                if (periodNanos == 0) {
                    if (setUncancellable()) {
                        V result = task.call();
                        setSuccessInternal(result);
                    }
                } else {
                    // check if is done as it may was cancelled
                    if (!isDone()) {
                        task.call();
                        if (!executor().isShutdown()) {
                            long p = periodNanos;
                            if (p > 0) {
                                deadlineNanos += p;
                            } else {
                                deadlineNanos = nanoTime() - p;
                            }
                            if (!isDone()) {
                                executor().delayedTaskQueue.add(this);
                            }
                        }
                    }
                }
            } catch (Throwable cause) {
                setFailureInternal(cause);
            }
        }

        @Override
        public boolean isCancelled() {
            if (cause() instanceof CancellationException) {
                return true;
            }
            return false;
        }

        @Override
        public  boolean cancel(boolean mayInterruptIfRunning) {
            if (!isDone()) {
                if (setUncancellable()) {
                    return tryFailureInternal(new CancellationException());
                }
            }
            return false;
        }

        private boolean setUncancellable() {
            return uncancellableUpdater.compareAndSet(this, 0, 1);
        }
    }

    private final class PurgeTask implements Runnable {
        @Override
        public void run() {
            Iterator<ScheduledFutureTask<?>> i = delayedTaskQueue.iterator();
            while (i.hasNext()) {
                ScheduledFutureTask<?> task = i.next();
                if (task.isCancelled()) {
                    i.remove();
                }
            }
        }
    }
}
