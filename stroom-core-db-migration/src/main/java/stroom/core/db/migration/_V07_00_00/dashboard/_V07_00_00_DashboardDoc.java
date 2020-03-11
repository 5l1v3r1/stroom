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

package stroom.core.db.migration._V07_00_00.dashboard;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Objects;

@JsonPropertyOrder({"type", "uuid", "name", "version", "createTime", "updateTime", "createUser", "updateUser"})
public class _V07_00_00_DashboardDoc extends _V07_00_00_Doc {
    public static final String DOCUMENT_TYPE = "Dashboard";
    private static final long serialVersionUID = 3598996730392094523L;

    @JsonIgnore
    private _V07_00_00_DashboardConfig dashboardConfig;

    public _V07_00_00_DashboardDoc() {
    }

    public _V07_00_00_DashboardConfig getDashboardConfig() {
        return dashboardConfig;
    }

    public void setDashboardConfig(final _V07_00_00_DashboardConfig dashboardConfig) {
        this.dashboardConfig = dashboardConfig;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        final _V07_00_00_DashboardDoc that = (_V07_00_00_DashboardDoc) o;
        return Objects.equals(dashboardConfig, that.dashboardConfig);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), dashboardConfig);
    }
}
