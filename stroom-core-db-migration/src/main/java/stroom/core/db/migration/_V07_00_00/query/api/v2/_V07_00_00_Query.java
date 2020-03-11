/*
 * Copyright 2017 Crown Copyright
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package stroom.core.db.migration._V07_00_00.query.api.v2;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import stroom.core.db.migration._V07_00_00.docref._V07_00_00_DocRef;
import stroom.docref.DocRef;
import stroom.query.api.v2.ExpressionOperator;
import stroom.query.api.v2.Param;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * {@value #CLASS_DESC}
 */
@JsonPropertyOrder({"dataSource", "expression", "params"})
@JsonInclude(Include.NON_DEFAULT)
@XmlType(name = "Query", propOrder = {"dataSource", "expression", "params"})
@XmlRootElement(name = "query")
@XmlAccessorType(XmlAccessType.FIELD)
@ApiModel(description = stroom.query.api.v2.Query.CLASS_DESC)
public final class _V07_00_00_Query implements Serializable {

    private static final long serialVersionUID = 9055582579670841979L;

    public static final String CLASS_DESC = "The query terms for the search";

    @XmlElement
    @ApiModelProperty(
            required = true)
    @JsonProperty
    private _V07_00_00_DocRef dataSource;

    @XmlElement
    @ApiModelProperty(
            value = "The root logical operator in the query expression tree",
            required = true)
    @JsonProperty
    private _V07_00_00_ExpressionOperator expression;

    @XmlElementWrapper(name = "params")
    @XmlElement(name = "param")
    @ApiModelProperty(
            value = "A list of key/value pairs that provide additional information about the query")
    @JsonProperty
    private List<_V07_00_00_Param> params;

    public _V07_00_00_Query() {
    }

    public _V07_00_00_Query(final _V07_00_00_DocRef dataSource, final _V07_00_00_ExpressionOperator expression) {
        this(dataSource, expression, null);
    }

    @JsonCreator
    public _V07_00_00_Query(@JsonProperty("dataSource") final _V07_00_00_DocRef dataSource,
                            @JsonProperty("expression") final _V07_00_00_ExpressionOperator expression,
                            @JsonProperty("params") final List<_V07_00_00_Param> params) {
        this.dataSource = dataSource;
        this.expression = expression;
        this.params = params;
    }

    public _V07_00_00_DocRef getDataSource() {
        return dataSource;
    }

    public void setDataSource(final _V07_00_00_DocRef dataSource) {
        this.dataSource = dataSource;
    }

    public _V07_00_00_ExpressionOperator getExpression() {
        return expression;
    }

    public void setExpression(final _V07_00_00_ExpressionOperator expression) {
        this.expression = expression;
    }

    public List<_V07_00_00_Param> getParams() {
        return params;
    }

    public void setParams(final List<_V07_00_00_Param> params) {
        this.params = params;
    }
}