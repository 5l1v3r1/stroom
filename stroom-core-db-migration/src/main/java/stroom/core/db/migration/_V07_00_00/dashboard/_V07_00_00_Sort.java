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

package stroom.core.db.migration._V07_00_00.dashboard;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import stroom.core.db.migration._V07_00_00.docref._V07_00_00_HasDisplayValue;
import stroom.core.db.migration._V07_00_00.util.shared._V07_00_00_EqualsBuilder;
import stroom.core.db.migration._V07_00_00.util.shared._V07_00_00_HashCodeBuilder;
import stroom.core.db.migration._V07_00_00.util.shared._V07_00_00_ToStringBuilder;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
@JsonPropertyOrder({"order", "direction"})
@XmlRootElement(name = "sort")
@XmlType(name = "Sort", propOrder = {"order", "direction"})
public class _V07_00_00_Sort implements Serializable {
    private static final long serialVersionUID = 4530846367973824427L;

    @XmlElement(name = "order")
    private int order = 1;
    @XmlElement(name = "direction")
    private _V07_00_00_SortDirection direction = _V07_00_00_SortDirection.ASCENDING;

    public _V07_00_00_Sort() {
    }

    public _V07_00_00_Sort(final int order, final _V07_00_00_SortDirection direction) {
        this.order = order;
        this.direction = direction;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(final int order) {
        this.order = order;
    }

    public _V07_00_00_SortDirection getDirection() {
        return direction;
    }

    public void setDirection(final _V07_00_00_SortDirection direction) {
        this.direction = direction;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof _V07_00_00_Sort)) {
            return false;
        }

        final _V07_00_00_Sort sort = (_V07_00_00_Sort) o;
        final _V07_00_00_EqualsBuilder builder = new _V07_00_00_EqualsBuilder();
        builder.append(order, sort.order);
        builder.append(direction, sort.direction);
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        final _V07_00_00_HashCodeBuilder builder = new _V07_00_00_HashCodeBuilder();
        builder.append(order);
        builder.append(direction);
        return builder.toHashCode();
    }

    @Override
    public String toString() {
        final _V07_00_00_ToStringBuilder builder = new _V07_00_00_ToStringBuilder();
        builder.append("order", order);
        builder.append("direction", direction);
        return builder.toString();
    }

    public enum _V07_00_00_SortDirection implements _V07_00_00_HasDisplayValue {
        ASCENDING("Ascending"), DESCENDING("Descending");

        private final String displayValue;

        _V07_00_00_SortDirection(final String displayValue) {
            this.displayValue = displayValue;
        }

        @Override
        public String getDisplayValue() {
            return displayValue;
        }
    }
}
