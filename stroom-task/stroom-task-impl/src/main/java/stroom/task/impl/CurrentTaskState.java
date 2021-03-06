/*
 * Copyright 2017 Crown Copyright
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

package stroom.task.impl;

import stroom.task.shared.Task;
import stroom.task.shared.TaskId;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.function.Supplier;

public final class CurrentTaskState {
    private static final ThreadLocal<Deque<TaskState>> THREAD_LOCAL = ThreadLocal.withInitial(ArrayDeque::new);

    private CurrentTaskState() {
        // Utility.
    }

    static void pushState(final TaskState taskState) {
        final Deque<TaskState> deque = THREAD_LOCAL.get();
        deque.push(taskState);
    }

    static TaskState popState() {
        final Deque<TaskState> deque = THREAD_LOCAL.get();
        return deque.pop();
    }

    static TaskState currentState() {
        final Deque<TaskState> deque = THREAD_LOCAL.get();
        return deque.peek();
    }

    public static TaskId currentTaskId() {
        final TaskState taskState = currentState();
        if (taskState != null) {
            return taskState.getTaskId();
        }
        return null;
    }

    public static String currentName() {
        final TaskState taskState = currentState();
        if (taskState != null) {
            return taskState.getName();
        }
        return null;
    }

    static void setName(final String name) {
        final TaskState taskState = currentState();
        if (taskState != null) {
            taskState.setName(name);
        }
    }

    static void info(final Supplier<String> messageSupplier) {
        final TaskState taskState = currentState();
        if (taskState != null) {
            taskState.info(messageSupplier);
        }
    }

    static void terminate() {
        final TaskState taskState = currentState();
        if (taskState != null) {
            taskState.terminate();
        }
    }
}