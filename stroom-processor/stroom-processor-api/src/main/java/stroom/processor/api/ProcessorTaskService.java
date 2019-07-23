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
 *
 */

package stroom.processor.api;

import stroom.processor.shared.FindProcessorTaskCriteria;
import stroom.processor.shared.ProcessorTask;
import stroom.processor.shared.ProcessorTaskSummary;
import stroom.util.shared.BaseResultList;

public interface ProcessorTaskService {
    BaseResultList<ProcessorTask> find(final FindProcessorTaskCriteria criteria);

    BaseResultList<ProcessorTaskSummary> findSummary(final FindProcessorTaskCriteria criteria);
}