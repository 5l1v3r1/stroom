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

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import stroom.core.db.migration.EntityReferenceReplacer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class V5_0_0_13__Dashboards extends BaseJavaMigration {
    private static final Logger LOGGER = LoggerFactory.getLogger(V5_0_0_13__Dashboards.class);

    @Override
    public void migrate(final Context flywayContext) throws Exception {
        migrate(flywayContext.getConnection());
    }

    private void migrate(final Connection connection) throws Exception {
        // Update dashboard data.
        updateDashboardData(connection);
    }

    private void updateDashboardData(final Connection connection) throws Exception {
        final EntityReferenceReplacer entityReferenceReplacer = new EntityReferenceReplacer();

        try (final Statement statement = connection.createStatement()) {
            try (final ResultSet resultSet = statement.executeQuery("SELECT ID, NAME, DAT FROM DASH;")) {
                while (resultSet.next()) {
                    final long id = resultSet.getLong(1);
                    final String name = resultSet.getString(2);
                    final String data = resultSet.getString(3);

                    LOGGER.info("Starting dashboard upgrade: " + name);

                    if (data == null) {
                        LOGGER.info("Incomplete configuration found");

                    } else {
                        String newData = data;
                        newData = entityReferenceReplacer.replaceEntityReferences(connection, newData);

                        if (!newData.equals(data)) {
                            LOGGER.info("Modifying dashboard");

                            try (final PreparedStatement preparedStatement = connection.prepareStatement("UPDATE DASH SET DAT = ? WHERE ID = ?")) {
                                preparedStatement.setString(1, newData);
                                preparedStatement.setLong(2, id);
                                preparedStatement.executeUpdate();
                            }
                        } else {
                            LOGGER.info("No change required");
                        }
                    }

                    LOGGER.info("Finished dashboard upgrade: " + name);
                }
            }
        }
    }
}
