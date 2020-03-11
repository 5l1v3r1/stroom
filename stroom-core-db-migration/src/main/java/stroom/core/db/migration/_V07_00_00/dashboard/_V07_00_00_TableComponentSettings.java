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
import stroom.core.db.migration._V07_00_00.docref._V07_00_00_DocRef;
import stroom.core.db.migration._V07_00_00.util.shared._V07_00_00_EqualsBuilder;
import stroom.core.db.migration._V07_00_00.util.shared._V07_00_00_HashCodeBuilder;
import stroom.core.db.migration._V07_00_00.util.shared._V07_00_00_ToStringBuilder;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@JsonPropertyOrder({"queryId", "fields", "extractValues", "extractionPipeline", "maxResults", "showDetail"})
@XmlRootElement(name = "table")
@XmlType(name = "TableComponentSettings", propOrder = {"queryId", "fields", "extractValues", "extractionPipeline", "maxResults", "showDetail"})
public class _V07_00_00_TableComponentSettings extends _V07_00_00_ComponentSettings {
    public static final int[] DEFAULT_MAX_RESULTS = {1000000};
    private static final long serialVersionUID = -2530827581046882396L;
    @XmlElement(name = "queryId")
    private String queryId;
    @XmlElementWrapper(name = "fields")
    @XmlElements({@XmlElement(name = "field", type = _V07_00_00_Field.class)})
    private List<_V07_00_00_Field> fields;
    @XmlElement(name = "extractValues")
    private Boolean extractValues;
    @XmlElement(name = "extractionPipeline")
    private _V07_00_00_DocRef extractionPipeline;
    @XmlElementWrapper(name = "maxResults")
    @XmlElement(name = "level")
    private int[] maxResults = DEFAULT_MAX_RESULTS;
    @XmlElement(name = "showDetail")
    private Boolean showDetail;

    public _V07_00_00_TableComponentSettings() {
    }

    public _V07_00_00_TableComponentSettings(final List<_V07_00_00_Field> fields) {
        this.fields = fields;
    }

    public String getQueryId() {
        return queryId;
    }

    public void setQueryId(final String queryId) {
        this.queryId = queryId;
    }

    public List<_V07_00_00_Field> getFields() {
        return fields;
    }

    public void setFields(final List<_V07_00_00_Field> fields) {
        this.fields = fields;
    }

    public void addField(final _V07_00_00_Field field) {
        if (fields == null) {
            fields = new ArrayList<>();
        }

        fields.add(field);
    }

    public void addField(final int index, final _V07_00_00_Field field) {
        if (fields == null) {
            fields = new ArrayList<>();
        }

        fields.add(index, field);
    }

    public void removeField(final _V07_00_00_Field field) {
        if (fields != null) {
            fields.remove(field);
        }
    }

    public Boolean getExtractValues() {
        return extractValues;
    }

    public void setExtractValues(final Boolean extractValues) {
        if (extractValues != null && extractValues) {
            this.extractValues = null;
        } else {
            this.extractValues = Boolean.FALSE;
        }
    }

    public boolean extractValues() {
        if (extractValues == null) {
            return true;
        }
        return extractValues;
    }

    public _V07_00_00_DocRef getExtractionPipeline() {
        return extractionPipeline;
    }

    public void setExtractionPipeline(final _V07_00_00_DocRef extractionPipeline) {
        this.extractionPipeline = extractionPipeline;
    }

    public int[] getMaxResults() {
        if (maxResults == null || maxResults.length == 0) {
            return DEFAULT_MAX_RESULTS;
        }

        return maxResults;
    }

    public void setMaxResults(final int[] maxResults) {
        if (maxResults == null || maxResults.length == 0) {
            this.maxResults = DEFAULT_MAX_RESULTS;
        } else {
            this.maxResults = maxResults;
        }
    }

    public Boolean getShowDetail() {
        return showDetail;
    }

    public void setShowDetail(final Boolean showDetail) {
        if (showDetail != null && showDetail) {
            this.showDetail = Boolean.TRUE;
        } else {
            this.showDetail = null;
        }
    }

    public boolean showDetail() {
        if (showDetail == null) {
            return false;
        }
        return showDetail;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof _V07_00_00_TableComponentSettings)) {
            return false;
        }

        final _V07_00_00_TableComponentSettings tableSettings = (_V07_00_00_TableComponentSettings) o;
        final _V07_00_00_EqualsBuilder builder = new _V07_00_00_EqualsBuilder();
        builder.append(queryId, tableSettings.queryId);
        builder.append(fields, tableSettings.fields);
        builder.append(extractValues, tableSettings.extractValues);
        builder.append(extractionPipeline, tableSettings.extractionPipeline);
        builder.append(maxResults, tableSettings.maxResults);
        builder.append(showDetail, tableSettings.showDetail);
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        final _V07_00_00_HashCodeBuilder builder = new _V07_00_00_HashCodeBuilder();
        builder.append(queryId);
        builder.append(fields);
        builder.append(extractValues);
        builder.append(extractionPipeline);
        builder.append(maxResults);
        builder.append(showDetail);
        return builder.toHashCode();
    }

    @Override
    public String toString() {
        final _V07_00_00_ToStringBuilder builder = new _V07_00_00_ToStringBuilder();
        builder.append("queryId", queryId);
        builder.append("fields", fields);
        builder.append("extractValues", extractValues);
        builder.append("extractionPipeline", extractionPipeline);
        builder.append("maxResults", Arrays.toString(maxResults));
        builder.append("showDetail", showDetail);
        return builder.toString();
    }
}
