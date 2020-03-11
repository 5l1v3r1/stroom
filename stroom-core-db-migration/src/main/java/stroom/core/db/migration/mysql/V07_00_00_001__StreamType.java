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
import stroom.core.db.migration._V07_00_00.entity.shared._V07_00_00_SQLNameConstants;
import stroom.core.db.migration._V07_00_00.streamstore.shared._V07_00_00_StreamType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

@SuppressWarnings("unused")
public class V07_00_00_001__StreamType extends BaseJavaMigration {
    private static final Logger LOGGER = LoggerFactory.getLogger(V07_00_00_001__StreamType.class);

    @Override
    public void migrate(final Context context) throws Exception {
        final long now = System.currentTimeMillis();

        for (final _V07_00_00_StreamType streamType : _V07_00_00_StreamType.initialValues()) {
            final long id = streamType.getId();

            boolean exists;
            try (final PreparedStatement preparedStatement = context.getConnection().prepareStatement(
                    "SELECT ID FROM " + _V07_00_00_StreamType.TABLE_NAME + " WHERE ID = ?")) {
                preparedStatement.setLong(1, id);
                try (final ResultSet resultSet = preparedStatement.executeQuery()) {
                    exists = resultSet.next();
                }
            }

            if (!exists) {
                try {
                    // We use SQL to insert because we need a predefined key.
                    final StringBuilder sql = new StringBuilder();
                    sql.append("INSERT INTO ");
                    sql.append(_V07_00_00_StreamType.TABLE_NAME);
                    sql.append(" (");
                    sql.append(_V07_00_00_StreamType.ID);
                    sql.append(",");
                    sql.append(_V07_00_00_StreamType.VERSION);
                    sql.append(",");
                    sql.append(_V07_00_00_StreamType.UPDATE_TIME);
                    sql.append(",");
                    sql.append(_V07_00_00_StreamType.CREATE_TIME);
                    sql.append(",");
                    sql.append(_V07_00_00_StreamType.UPDATE_USER);
                    sql.append(",");
                    sql.append(_V07_00_00_StreamType.CREATE_USER);
                    sql.append(",");
                    sql.append(_V07_00_00_StreamType.PATH);
                    sql.append(",");
                    sql.append(_V07_00_00_StreamType.EXTENSION);
                    sql.append(",");
                    sql.append(_V07_00_00_SQLNameConstants.NAME);
                    sql.append(",");
                    sql.append(_V07_00_00_SQLNameConstants.PURPOSE);
                    sql.append(") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ");
//                    sql.arg(streamType.getId());
//                    sql.append(",");
//                    sql.arg(1);
//                    sql.append(",");
//                    sql.arg(now);
//                    sql.append(",");
//                    sql.arg(now);
//                    sql.append(",");
//                    sql.arg("upgrade");
//                    sql.append(",");
//                    sql.arg("upgrade");
//                    sql.append(",");
//                    sql.arg(streamType.getPath());
//                    sql.append(",");
//                    sql.arg(streamType.getExtension());
//                    sql.append(",");
//                    sql.arg(streamType.getName());
//                    sql.append(",");
//                    sql.arg(streamType.getPpurpose());
//                    sql.append(")");

                    try (final PreparedStatement preparedStatement = context.getConnection().prepareStatement(sql.toString())) {

                        preparedStatement.setLong(1, streamType.getId());
                        preparedStatement.setInt(2, 1);
                        preparedStatement.setLong(3, now);
                        preparedStatement.setLong(4, now);
                        preparedStatement.setString(5, "upgrade");
                        preparedStatement.setString(6, "upgrade");
                        preparedStatement.setString(7, streamType.getPath());
                        preparedStatement.setString(8, streamType.getExtension());
                        preparedStatement.setString(9, streamType.getName());
                        preparedStatement.setByte(10, streamType.getPpurpose());

                        preparedStatement.executeUpdate();
                    }
                } catch (final Exception ex) {
                    LOGGER.error("init() - Added initial stream type {}", streamType, ex);
                }
            }
        }
    }
}
