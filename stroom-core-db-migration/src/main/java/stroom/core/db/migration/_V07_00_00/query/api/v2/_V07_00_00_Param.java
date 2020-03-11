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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.Objects;

@JsonPropertyOrder({"key", "value"})
@JsonInclude(Include.NON_DEFAULT)
@XmlType(name = "Param", propOrder = {"key", "value"})
@XmlAccessorType(XmlAccessType.FIELD)
@ApiModel(description = "A key value pair that describes a property of a query")
public final class _V07_00_00_Param implements Serializable {

    private static final long serialVersionUID = 9055582579670841979L;

    @XmlElement
    @ApiModelProperty(
            value = "The property key",
            required = true)
    @JsonProperty
    private String key;

    @XmlElement
    @ApiModelProperty(
            value = "The property value",
            required = true)
    @JsonProperty
    private String value;

    public _V07_00_00_Param() {
    }

    @JsonCreator
    public _V07_00_00_Param(@JsonProperty("key") final String key,
                            @JsonProperty("value") final String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(final String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(final String value) {
        this.value = value;
    }
}