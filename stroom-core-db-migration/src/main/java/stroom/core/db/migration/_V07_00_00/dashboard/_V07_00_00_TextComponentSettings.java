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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@JsonPropertyOrder({"tableId", "pipeline", "showAsHtml"})
@XmlRootElement(name = "text")
@XmlType(name = "TextComponentSettings", propOrder = {"tableId", "pipeline", "showAsHtml"})
public class _V07_00_00_TextComponentSettings extends _V07_00_00_ComponentSettings {
    private static final long serialVersionUID = -2530827581046882396L;

    @XmlElement(name = "tableId")
    private String tableId;
    @XmlElement(name = "pipeline")
    private _V07_00_00_DocRef pipeline;
    @XmlElement(name = "showAsHtml")
    private boolean showAsHtml;

    public _V07_00_00_TextComponentSettings() {
    }

    public String getTableId() {
        return tableId;
    }

    public void setTableId(final String tableId) {
        this.tableId = tableId;
    }

    public _V07_00_00_DocRef getPipeline() {
        return pipeline;
    }

    public void setPipeline(final _V07_00_00_DocRef pipeline) {
        this.pipeline = pipeline;
    }

    public boolean isShowAsHtml() {
        return showAsHtml;
    }

    public void setShowAsHtml(boolean showAsHtml) {
        this.showAsHtml = showAsHtml;
    }
}
