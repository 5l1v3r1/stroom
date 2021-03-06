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

import com.codahale.metrics.health.HealthCheck.Result;
import stroom.event.logging.api.DocumentEventLog;
import stroom.processor.api.ProcessorService;
import stroom.processor.shared.ProcessorResource;
import stroom.util.HasHealthCheck;
import stroom.util.logging.LambdaLogger;
import stroom.util.logging.LambdaLoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.ServerErrorException;
import javax.ws.rs.core.Response.Status;

// TODO : @66 add event logging
class ProcessorResourceImpl implements ProcessorResource, HasHealthCheck {
    private static final LambdaLogger LOGGER = LambdaLoggerFactory.getLogger(ProcessorResourceImpl.class);

    private final ProcessorService processorService;
    private final DocumentEventLog documentEventLog;

    @Inject
    ProcessorResourceImpl(final ProcessorService processorService,
                          final DocumentEventLog documentEventLog) {
        this.processorService = processorService;
        this.documentEventLog = documentEventLog;
    }

    @Override
    public void delete(final Integer id) {
        try {
            processorService.delete(id);
        } catch (final RuntimeException e) {
            throw new ServerErrorException(Status.INTERNAL_SERVER_ERROR, e);
        }
    }

    @Override
    public void setEnabled(final Integer id, final Boolean enabled) {
        try {
            processorService.setEnabled(id, enabled);
        } catch (final RuntimeException e) {
            throw new ServerErrorException(Status.INTERNAL_SERVER_ERROR, e);
        }
    }

    @Override
    public Result getHealth() {
        return Result.healthy();
    }
}