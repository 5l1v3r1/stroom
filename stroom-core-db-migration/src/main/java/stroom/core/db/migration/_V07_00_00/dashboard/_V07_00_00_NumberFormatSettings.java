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
import stroom.core.db.migration._V07_00_00.util.shared._V07_00_00_EqualsBuilder;
import stroom.core.db.migration._V07_00_00.util.shared._V07_00_00_HashCodeBuilder;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@JsonPropertyOrder({"decimalPlaces", "useSeparator"})
@XmlRootElement(name = "numberFormatSettings")
@XmlType(name = "NumberFormatSettings", propOrder = {"decimalPlaces", "useSeparator"})
public class _V07_00_00_NumberFormatSettings implements _V07_00_00_FormatSettings {
    private static final long serialVersionUID = 9145624653060319801L;

    private static final int DEFAULT_DECIMAL_PLACES = 0;
    private static final boolean DEFAULT_USE_SEPARATOR = false;

    @XmlElement(name = "decimalPlaces")
    private Integer decimalPlaces;
    @XmlElement(name = "useSeparator")
    private Boolean useSeparator;

    public _V07_00_00_NumberFormatSettings() {
    }

    public _V07_00_00_NumberFormatSettings(Integer decimalPlaces, Boolean useSeparator) {
        this.decimalPlaces = decimalPlaces;
        this.useSeparator = useSeparator;
    }

    public Integer getDecimalPlaces() {
        return decimalPlaces;
    }

    public void setDecimalPlaces(final Integer decimalPlaces) {
        this.decimalPlaces = decimalPlaces;
    }

    public Boolean getUseSeparator() {
        return useSeparator;
    }

    public void setUseSeparator(final Boolean useSeparator) {
        this.useSeparator = useSeparator;
    }

    @Override
    @XmlTransient
    public boolean isDefault() {
        return (decimalPlaces == null || decimalPlaces.equals(DEFAULT_DECIMAL_PLACES))
                && (useSeparator == null || useSeparator.equals(DEFAULT_USE_SEPARATOR));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        _V07_00_00_NumberFormatSettings that = (_V07_00_00_NumberFormatSettings) o;

        return new _V07_00_00_EqualsBuilder()
                .append(decimalPlaces, that.decimalPlaces)
                .append(useSeparator, that.useSeparator)
                .isEquals();
    }

    @Override
    public int hashCode() {
        _V07_00_00_HashCodeBuilder hashCodeBuilder = new _V07_00_00_HashCodeBuilder();
        hashCodeBuilder.append(decimalPlaces);
        hashCodeBuilder.append(useSeparator);
        return hashCodeBuilder.toHashCode();
    }

    @Override
    public String toString() {
        return "NumberFormatSettings{" +
                "decimalPlaces=" + decimalPlaces +
                ", useSeparator=" + useSeparator +
                '}';
    }
}
