/*
 * Copyright 2016 Crown Copyright
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package stroom.task.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class TaskProducer implements Comparable<TaskProducer> {
    private static final Logger LOGGER = LoggerFactory.getLogger(TaskProducer.class);

    private final long now = System.currentTimeMillis();

    private final AtomicInteger threadsUsed = new AtomicInteger();

    private final TaskExecutor taskExecutor;
    private final int maxThreadsPerTask;
    private final TaskContext taskContext;

    private final AtomicBoolean finishedAddingTasks = new AtomicBoolean();
    private final AtomicInteger tasksTotal = new AtomicInteger();
    private final AtomicInteger tasksCompleted = new AtomicInteger();

    public TaskProducer(final TaskExecutor taskExecutor,
                        final int maxThreadsPerTask,
                        final TaskContext taskContext) {
        this.taskExecutor = taskExecutor;
        this.maxThreadsPerTask = maxThreadsPerTask;
        this.taskContext = taskContext;
    }

    /**
     * Get the next task to execute or null if the producer has reached a concurrent execution limit or no further tasks
     * are available.
     *
     * @return The next task to execute or null if no tasks are available at this time.
     */
    final Runnable next() {
        Runnable runnable = null;

        final int count = threadsUsed.incrementAndGet();
        if (count > maxThreadsPerTask) {
            threadsUsed.decrementAndGet();
        } else {
            final Runnable task = getNext();
            if (task == null) {
                threadsUsed.decrementAndGet();

                // Auto detach if we are complete.
                if (isComplete()) {
                    detach();
                }
            } else {
                runnable = () -> {
                    try {
                        task.run();
                    } catch (final Throwable e) {
                        LOGGER.debug(e.getMessage(), e);
                    } finally {
                        threadsUsed.decrementAndGet();
                        incrementTasksCompleted();
                    }
                };
            }
        }

        // Wrap the runnable so that we get task info and execute with the right permissions etc.
        if (runnable != null) {
            runnable = taskContext.subTask(runnable);
        }

        return runnable;
    }

    protected abstract Runnable getNext();

    /**
     * Test if this task producer will not issue any further tasks and that all of the tasks it has issued have completed processing.
     *
     * @return True if this producer will not issue any further tasks and that all of the tasks it has issued have completed processing.
     */
    protected boolean isComplete() {
        return finishedAddingTasks.get() && getRemainingTasks() == 0;
    }

    protected void attach() {
        taskExecutor.addProducer(this);
    }

    private void detach() {
        taskExecutor.removeProducer(this);
    }

    protected void signalAvailable() {
        taskExecutor.signalAll();
    }

    protected void finishedAddingTasks() {
        this.finishedAddingTasks.set(true);
    }

    protected final int getTasksTotal() {
        return tasksTotal.get();
    }

    protected final void setTasksTotal(int tasksTotal) {
        this.tasksTotal.set(tasksTotal);
    }

    protected void incrementTasksTotal() {
        tasksTotal.incrementAndGet();
    }

    final int getTasksCompleted() {
        return tasksCompleted.get();
    }

    protected void incrementTasksCompleted() {
        tasksCompleted.incrementAndGet();
    }

    public final int getRemainingTasks() {
        return tasksTotal.get() - tasksCompleted.get();
    }

    @Override
    public int compareTo(final TaskProducer o) {
        return Long.compare(now, o.now);
    }
}
