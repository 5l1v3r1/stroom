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
import stroom.core.db.migration._V07_00_00.docref._V07_00_00_SharedObject;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@JsonPropertyOrder({"open", "refresh", "refreshInterval"})
@XmlRootElement(name = "automate")
@XmlType(name = "Automate", propOrder = {"open", "refresh", "refreshInterval"})
public class _V07_00_00_Automate implements _V07_00_00_SharedObject {
    private static final long serialVersionUID = -2530827581046882396L;

    @XmlElement(name = "open")
    private boolean open;

    @XmlElement(name = "refresh")
    private boolean refresh;

    @XmlElement(name = "refreshInterval")
    private String refreshInterval = "10s";

    public _V07_00_00_Automate() {
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(final boolean open) {
        this.open = open;
    }

    public boolean isRefresh() {
        return refresh;
    }

    public void setRefresh(final boolean refresh) {
        this.refresh = refresh;
    }

    public String getRefreshInterval() {
        return refreshInterval;
    }

    public void setRefreshInterval(final String refreshInterval) {
        this.refreshInterval = refreshInterval;
    }
}
