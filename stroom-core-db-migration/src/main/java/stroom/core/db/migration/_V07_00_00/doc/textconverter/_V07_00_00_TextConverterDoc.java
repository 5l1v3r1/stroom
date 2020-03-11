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

package stroom.core.db.migration._V07_00_00.doc.textconverter;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import stroom.core.db.migration._V07_00_00.docref._V07_00_00_HasDisplayValue;
import stroom.core.db.migration._V07_00_00.docstore.shared._V07_00_00_Doc;
import stroom.core.db.migration._V07_00_00.entity.shared._V07_00_00_HasData;

import java.util.Objects;

@JsonPropertyOrder({"type", "uuid", "name", "version", "createTime", "updateTime", "createUser", "updateUser", "description", "converterType"})
public class _V07_00_00_TextConverterDoc extends _V07_00_00_Doc implements _V07_00_00_HasData {
    private static final long serialVersionUID = 4519634323788508083L;

    public static final String DOCUMENT_TYPE = "TextConverter";

    private String description;
    @JsonIgnore
    private String data;
    private TextConverterType converterType = TextConverterType.NONE;

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    @Override
    public String getData() {
        return data;
    }

    @Override
    public void setData(final String data) {
        this.data = data;
    }

    public TextConverterType getConverterType() {
        return converterType;
    }

    public void setConverterType(final TextConverterType converterType) {
        this.converterType = converterType;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        final _V07_00_00_TextConverterDoc that = (_V07_00_00_TextConverterDoc) o;
        return Objects.equals(description, that.description) &&
                Objects.equals(data, that.data) &&
                Objects.equals(converterType, that.converterType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), description, data, converterType);
    }

    public enum TextConverterType implements _V07_00_00_HasDisplayValue {
        NONE("None"), DATA_SPLITTER("Data Splitter"), XML_FRAGMENT("XML Fragment");

        private final String displayValue;

        TextConverterType(final String displayValue) {
            this.displayValue = displayValue;
        }

        @Override
        public String getDisplayValue() {
            return displayValue;
        }
    }
}
