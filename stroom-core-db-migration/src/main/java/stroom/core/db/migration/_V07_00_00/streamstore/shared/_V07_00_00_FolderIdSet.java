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

package stroom.core.db.migration._V07_00_00.streamstore.shared;

import stroom.util.shared.EqualsBuilder;
import stroom.util.shared.HashCodeBuilder;

@Deprecated
public class _V07_00_00_FolderIdSet extends _V07_00_00_EntityIdSet<_V07_00_00_Folder> {
    private static final long serialVersionUID = -470516763097779743L;
    private boolean deep = true;

    public _V07_00_00_FolderIdSet() {
    }

    public _V07_00_00_FolderIdSet(final _V07_00_00_Folder folder) {
        add(folder);
    }

    public boolean isDeep() {
        return deep;
    }

    public void setDeep(final boolean deep) {
        this.deep = deep;
    }

    public void setRootOnly(final _V07_00_00_Folder folder) {
        clear();
        add(folder);
        setDeep(false);
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || !(obj instanceof _V07_00_00_FolderIdSet)) {
            return false;
        }

        final _V07_00_00_FolderIdSet idSet = (_V07_00_00_FolderIdSet) obj;

        final EqualsBuilder builder = new EqualsBuilder();
        builder.appendSuper(super.equals(obj));
        builder.append(this.deep, idSet.deep);
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.appendSuper(super.hashCode());
        builder.append(deep);
        return builder.toHashCode();
    }
}
