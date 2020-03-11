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

package stroom.core.db.migration._V07_00_00.doc.dictionary;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import stroom.core.db.migration._V07_00_00.docref._V07_00_00_DocRef;
import stroom.core.db.migration._V07_00_00.docstore.shared._V07_00_00_Doc;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;
import java.util.Objects;

// barring class/pkg naming, this should be identical to stroom.dictionary.legacy.OldDictionaryDoc

@XmlAccessorType(XmlAccessType.FIELD)
@JsonPropertyOrder({"type", "uuid", "name", "version", "createTime", "updateTime", "createUser", "updateUser", "description", "data", "includes"})
@JsonInclude(Include.NON_EMPTY)
@XmlRootElement(name = "dictionary")
@XmlType(name = "DictionaryDoc", propOrder = {"type", "uuid", "name", "version", "createTime", "updateTime", "createUser", "updateUser", "description", "data", "imports"})
public class _V07_00_00_OldDictionaryDoc extends _V07_00_00_Doc {
    private static final long serialVersionUID = -4208920620555926044L;

    public static final String DOCUMENT_TYPE = "Dictionary";

    @XmlElement(name = "description")
    private String description;
    @XmlElement(name = "data")
    private String data;
    @XmlElementWrapper(name = "imports")
    @XmlElement(name = "docRef")
    private List<_V07_00_00_DocRef> imports;

    public _V07_00_00_OldDictionaryDoc() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getData() {
        return data;
    }

    public void setData(final String data) {
        this.data = data;
    }

    public List<_V07_00_00_DocRef> getImports() {
        return imports;
    }

    public void setImports(final List<_V07_00_00_DocRef> imports) {
        this.imports = imports;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        final _V07_00_00_OldDictionaryDoc that = (_V07_00_00_OldDictionaryDoc) o;
        return Objects.equals(description, that.description) &&
                Objects.equals(data, that.data) &&
                Objects.equals(imports, that.imports);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), description, data, imports);
    }
}
