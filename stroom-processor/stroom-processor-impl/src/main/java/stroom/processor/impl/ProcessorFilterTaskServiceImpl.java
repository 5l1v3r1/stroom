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

package stroom.processor.impl;


import stroom.processor.api.ProcessorFilterTaskService;
import stroom.processor.shared.FindProcessorFilterTaskCriteria;
import stroom.processor.shared.ProcessorFilterTask;
import stroom.processor.shared.ProcessorFilterTaskSummaryRow;
import stroom.security.Security;
import stroom.security.shared.PermissionNames;
import stroom.util.shared.BaseResultList;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
class ProcessorFilterTaskServiceImpl implements ProcessorFilterTaskService {
    private static final String PERMISSION = PermissionNames.MANAGE_PROCESSORS_PERMISSION;

    private final ProcessorFilterTaskDao processorFilterTaskDao;
    private final Security security;

    @Inject
    ProcessorFilterTaskServiceImpl(final ProcessorFilterTaskDao processorFilterTaskDao,
                                   final Security security) {
        this.processorFilterTaskDao = processorFilterTaskDao;
        this.security = security;
    }

    @Override
    public BaseResultList<ProcessorFilterTask> find(final FindProcessorFilterTaskCriteria criteria) {
        return security.secureResult(PERMISSION, () ->
                processorFilterTaskDao.find(criteria));
    }

    @Override
    public BaseResultList<ProcessorFilterTaskSummaryRow> findSummary(final FindProcessorFilterTaskCriteria criteria) {
        return security.secureResult(PERMISSION, () ->
                processorFilterTaskDao.findSummary(criteria));
    }
}
