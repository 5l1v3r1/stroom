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

package stroom.core.db.migration._V07_00_00.doc.xmlschema;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import stroom.core.db.migration._V07_00_00.docstore.shared._V07_00_00_Doc;
import stroom.core.db.migration._V07_00_00.entity.shared._V07_00_00_HasData;

import java.util.Objects;

@JsonPropertyOrder({"type", "uuid", "name", "version", "createTime", "updateTime", "createUser", "updateUser", "description", "namespaceURI", "systemId", "deprecated", "schemaGroup"})
public class _V07_00_00_XmlSchemaDoc extends _V07_00_00_Doc implements _V07_00_00_HasData {
    private static final long serialVersionUID = 4519634323788508083L;

    public static final String DOCUMENT_TYPE = "XMLSchema";

    private String description;
    private String namespaceURI;
    private String systemId;
    @JsonIgnore
    private String data;
    private boolean deprecated;
    private String schemaGroup;

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getNamespaceURI() {
        return namespaceURI;
    }

    public void setNamespaceURI(final String namespaceURI) {
        this.namespaceURI = namespaceURI;
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(final String systemId) {
        this.systemId = systemId;
    }

    @Override
    public String getData() {
        return data;
    }

    @Override
    public void setData(final String data) {
        this.data = data;
    }

    public boolean isDeprecated() {
        return deprecated;
    }

    public void setDeprecated(final boolean deprecated) {
        this.deprecated = deprecated;
    }

    public String getSchemaGroup() {
        return schemaGroup;
    }

    public void setSchemaGroup(final String schemaGroup) {
        this.schemaGroup = schemaGroup;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        final _V07_00_00_XmlSchemaDoc that = (_V07_00_00_XmlSchemaDoc) o;
        return deprecated == that.deprecated &&
                Objects.equals(description, that.description) &&
                Objects.equals(namespaceURI, that.namespaceURI) &&
                Objects.equals(systemId, that.systemId) &&
                Objects.equals(data, that.data) &&
                Objects.equals(schemaGroup, that.schemaGroup);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), description, namespaceURI, systemId, data, deprecated, schemaGroup);
    }
}
