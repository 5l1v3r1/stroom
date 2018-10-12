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

package stroom.pipeline.server.reader;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import stroom.pipeline.server.task.RecordDetector;
import stroom.pipeline.server.task.SteppingController;

import java.io.Reader;

@Component
@Scope("prototype")
public class ReaderRecordDetectorElement extends AbstractReaderElement implements RecordDetector {
    private SteppingController controller;

    @Override
    protected Reader insertFilter(final Reader reader) {
        if (controller == null) {
            return reader;
        }
        return new ReaderRecordDetector(reader, controller);
    }

    @Override
    public void setController(final SteppingController controller) {
        this.controller = controller;
    }
}
