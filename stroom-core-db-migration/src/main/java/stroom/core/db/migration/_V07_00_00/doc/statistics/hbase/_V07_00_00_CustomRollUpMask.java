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

package stroom.core.db.migration._V07_00_00.doc.statistics.hbase;

import stroom.core.db.migration._V07_00_00.docref._V07_00_00_HasDisplayValue;
import stroom.core.db.migration._V07_00_00.docref._V07_00_00_SharedObject;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "customRollUpMask")
public class _V07_00_00_CustomRollUpMask implements _V07_00_00_HasDisplayValue, _V07_00_00_SharedObject {

    private static final long serialVersionUID = -7146872949794428854L;

    /**
     * Holds a list of the positions of tags that are rolled up, zero based. The
     * position number is based on the alphanumeric sorted list of tag/field
     * names in the {@link _V07_00_00_StroomStatsStoreDoc}. Would use a SortedSet but that
     * is not supported by GWT. Must ensure the contents of this are sorted so
     * that when contains is called on lists of these objects it works
     * correctly.
     */
    @XmlElement(name = "rolledUpTagPosition")
    private List<Integer> rolledUpTagPositions = new ArrayList<>();

    public _V07_00_00_CustomRollUpMask() {
    }

    public _V07_00_00_CustomRollUpMask(final List<Integer> rolledUpTagPositions) {
        this.rolledUpTagPositions = new ArrayList<>(rolledUpTagPositions);
        Collections.sort(this.rolledUpTagPositions);
    }

    public List<Integer> getRolledUpTagPositions() {
        return rolledUpTagPositions;
    }

    public void setRolledUpTagPositions(final List<Integer> rolledUpTagPositions) {
        this.rolledUpTagPositions = new ArrayList<>(rolledUpTagPositions);
        Collections.sort(this.rolledUpTagPositions);
    }

    public boolean isTagRolledUp(final int position) {
        return rolledUpTagPositions.contains(position);
    }

    public void setRollUpState(final Integer position, final boolean isRolledUp) {
        if (isRolledUp) {
            if (!rolledUpTagPositions.contains(position)) {
                rolledUpTagPositions.add(position);
                Collections.sort(this.rolledUpTagPositions);
            }
        } else {
            // no need to re-sort on remove as already in order
            rolledUpTagPositions.remove(position);
        }

    }

    @Override
    public String getDisplayValue() {
        return null;
    }

    public _V07_00_00_CustomRollUpMask deepCopy() {
        return new _V07_00_00_CustomRollUpMask(new ArrayList<>(rolledUpTagPositions));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((rolledUpTagPositions == null) ? 0 : rolledUpTagPositions.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final _V07_00_00_CustomRollUpMask other = (_V07_00_00_CustomRollUpMask) obj;
        if (rolledUpTagPositions == null) {
            return other.rolledUpTagPositions == null;
        } else return rolledUpTagPositions.equals(other.rolledUpTagPositions);
    }

    @Override
    public String toString() {
        return "CustomRollUpMask [rolledUpTagPositions=" + rolledUpTagPositions + "]";
    }
}
