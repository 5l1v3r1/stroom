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

package stroom.cluster.lock.impl.db;

import stroom.task.api.ServerTask;

public class ClusterLockTask extends ServerTask<Boolean> {
    private final ClusterLockKey key;
    private final ClusterLockStyle lockStyle;

    public ClusterLockTask(final ClusterLockKey key, final ClusterLockStyle lockStyle) {
        this.key = key;
        this.lockStyle = lockStyle;
    }

    public ClusterLockKey getKey() {
        return key;
    }

    public ClusterLockStyle getLockStyle() {
        return lockStyle;
    }
}
