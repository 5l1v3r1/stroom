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

package stroom.core.db.migration.mysql;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import stroom.core.db.migration._V07_00_00.dashboard.LegacyDashboardDeserialiser;
import stroom.dashboard.shared.DashboardDoc;
import stroom.util.json.JsonUtil;
import stroom.util.string.EncodingUtil;

import java.io.StringWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@SuppressWarnings("unused")
public class V07_00_00_007__Dashboard extends BaseJavaMigration {
    @Override
    public void migrate(final Context context) throws Exception {
        final LegacyDashboardDeserialiser legacyDashboardDeserialiser = new LegacyDashboardDeserialiser();
        final ObjectMapper mapper = JsonUtil.getMapper();

        try (final PreparedStatement preparedStatement = context.getConnection().prepareStatement(
                "SELECT CRT_MS, CRT_USER, UPD_MS, UPD_USER, NAME, UUID, DAT FROM DASH")) {
            try (final ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    final Long crtMs = resultSet.getLong(1);
                    final String crtUser = resultSet.getString(2);
                    final Long updMs = resultSet.getLong(3);
                    final String updUser = resultSet.getString(4);
                    final String name = resultSet.getString(5);
                    final String uuid = resultSet.getString(6);
                    final String dat = resultSet.getString(7);

                    final DashboardDoc document = new DashboardDoc();
                    document.setType(DashboardDoc.DOCUMENT_TYPE);
                    document.setUuid(uuid);
                    document.setName(name);
                    document.setVersion(UUID.randomUUID().toString());
                    document.setCreateTime(crtMs);
                    document.setUpdateTime(updMs);
                    document.setCreateUser(crtUser);
                    document.setUpdateUser(updUser);
                    document.setDashboardConfig(legacyDashboardDeserialiser.getDashboardConfigFromLegacyXML(dat));

                    final StringWriter stringWriter = new StringWriter();
                    mapper.writeValue(stringWriter, document);
                    final Map<String, byte[]> dataMap = new HashMap<>();
                    dataMap.put("meta", EncodingUtil.asBytes(stringWriter.toString()));

                    // Add the records.
                    dataMap.forEach((k, v) -> {
                        try (final PreparedStatement ps = context.getConnection().prepareStatement(
                                "INSERT INTO doc (type, uuid, name, ext, data) VALUES (?, ?, ?, ?, ?)")) {
                            ps.setString(1, DashboardDoc.DOCUMENT_TYPE);
                            ps.setString(2, uuid);
                            ps.setString(3, name);
                            ps.setString(4, k);
                            ps.setBytes(5, v);
                            ps.executeUpdate();
                        } catch (final SQLException e) {
                            throw new RuntimeException(e.getMessage(), e);
                        }
                    });
                }
            }
        }
    }
}
