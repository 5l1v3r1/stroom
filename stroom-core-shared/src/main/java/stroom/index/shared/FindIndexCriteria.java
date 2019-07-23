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

package stroom.index.shared;

import stroom.util.shared.FindDocumentEntityCriteria;

public class FindIndexCriteria extends FindDocumentEntityCriteria {
    private static final long serialVersionUID = -4421720204507720754L;

    public FindIndexCriteria() {
        // Default constructor necessary for GWT serialisation.
    }

    public FindIndexCriteria(final String name) {
        super(name);
    }
}