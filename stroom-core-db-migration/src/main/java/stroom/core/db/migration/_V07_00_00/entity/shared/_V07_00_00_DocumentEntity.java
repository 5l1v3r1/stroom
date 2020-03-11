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

package stroom.core.db.migration._V07_00_00.entity.shared;


import stroom.core.db.migration._V07_00_00.util.shared._V07_00_00_Document;

public abstract class _V07_00_00_DocumentEntity extends _V07_00_00_NamedEntity implements _V07_00_00_Document {
    public static final String UUID = _V07_00_00_SQLNameConstants.UUID;

    private static final long serialVersionUID = -6752797140242673318L;

    private String uuid;

    @Override
    public String getUuid() {
        return uuid;
    }

    public void setUuid(final String uuid) {
        this.uuid = uuid;
    }

    @Override
    public void clearPersistence() {
        super.clearPersistence();
        uuid = null;
    }

    protected void copyFrom(final _V07_00_00_DocumentEntity t) {
        super.copyFrom(t);
    }
}
