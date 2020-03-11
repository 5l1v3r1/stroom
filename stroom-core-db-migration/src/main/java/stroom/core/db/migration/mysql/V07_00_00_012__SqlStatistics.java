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
import stroom.core.db.migration._V07_00_00.doc.statistics.hbase._V07_00_00_StroomStatsStoreDoc;
import stroom.core.db.migration._V07_00_00.doc.statistics.sql._V07_00_00_StatisticRollUpType;
import stroom.core.db.migration._V07_00_00.doc.statistics.sql._V07_00_00_StatisticStoreDoc;
import stroom.core.db.migration._V07_00_00.doc.statistics.sql._V07_00_00_StatisticStoreSerialiser;
import stroom.core.db.migration._V07_00_00.doc.statistics.sql._V07_00_00_StatisticType;
import stroom.core.db.migration._V07_00_00.doc.statistics.sql._V07_00_00_StatisticsDataSourceData;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.UUID;

@SuppressWarnings("unused")
public class V07_00_00_012__SqlStatistics extends BaseJavaMigration {

    @Override
    public void migrate(final Context context) throws Exception {
        final _V07_00_00_StatisticStoreSerialiser serialiser = new _V07_00_00_StatisticStoreSerialiser();

        try (final PreparedStatement preparedStatement = context.getConnection().prepareStatement(
                "SELECT " +
                    "  CRT_MS, " +
                    "  CRT_USER, " +
                    "  UPD_MS, " +
                    "  UPD_USER, " +
                    "  NAME, " +
                    "  UUID, " +
                    "  DESCRIP, " +
                    "  STAT_TP, " +
                    "  ROLLUP_TP, " +
                    "  PRES, " +
                    "  ENBL, " +
                    "  DAT " +
                    "FROM STAT_DAT_SRC")) {
            try (final ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    final Long crtMs = resultSet.getLong(1);
                    final String crtUser = resultSet.getString(2);
                    final Long updMs = resultSet.getLong(3);
                    final String updUser = resultSet.getString(4);
                    final String name = resultSet.getString(5);
                    final String uuid = resultSet.getString(6);
                    final String descrip = resultSet.getString(7);
                    final byte statTp = resultSet.getByte(8);
                    final byte rollupTp = resultSet.getByte(9);
                    final Long pres = resultSet.getLong(10);
                    final boolean enbl = resultSet.getBoolean(11);
                    final String dat = resultSet.getString(12);

                    final _V07_00_00_StatisticStoreDoc document = new _V07_00_00_StatisticStoreDoc();
                    document.setType(_V07_00_00_StatisticStoreDoc.DOCUMENT_TYPE);
                    document.setUuid(uuid);
                    document.setName(name);
                    document.setVersion(UUID.randomUUID().toString());
                    document.setCreateTime(crtMs);
                    document.setUpdateTime(updMs);
                    document.setCreateUser(crtUser);
                    document.setUpdateUser(updUser);
                    document.setDescription(descrip);
                    document.setStatisticType(_V07_00_00_StatisticType.PRIMITIVE_VALUE_CONVERTER.fromPrimitiveValue(statTp));
                    document.setRollUpType(_V07_00_00_StatisticRollUpType.PRIMITIVE_VALUE_CONVERTER.fromPrimitiveValue(rollupTp));
                    document.setPrecision(pres);
                    document.setEnabled(enbl);

                    final _V07_00_00_StatisticsDataSourceData statisticsDataSourceData = serialiser.getDataFromLegacyXML(dat);
                    if (statisticsDataSourceData != null) {
                        document.setConfig(statisticsDataSourceData);
                    }

                    final Map<String, byte[]> dataMap = serialiser.write(document);

                    // Add the records.
                    dataMap.forEach((k, v) -> {
                        try (final PreparedStatement ps = context.getConnection().prepareStatement(
                                "INSERT INTO doc (type, uuid, name, ext, data) VALUES (?, ?, ?, ?, ?)")) {
                            ps.setString(1, _V07_00_00_StroomStatsStoreDoc.DOCUMENT_TYPE);
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
