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

package stroom.core.db.migration.mysql;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import stroom.core.db.migration._V07_00_00.docref._V07_00_00_DocRefs;
import stroom.core.db.migration._V07_00_00.entity.util._V07_00_00_ObjectMarshaller;
import stroom.docref.DocRef;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class V5_0_0_12__Script extends BaseJavaMigration {
    private static final Logger LOGGER = LoggerFactory.getLogger(V5_0_0_12__Script.class);

    @Override
    public void migrate(final Context flywayContext) throws Exception {
        // Change parent pipeline references to be document references.
        makeParentReferenceDocRef(flywayContext.getConnection());
    }

    private void makeParentReferenceDocRef(final Connection connection) throws Exception {
        try (final Statement statement = connection.createStatement()) {
            statement.executeUpdate("ALTER TABLE SCRIPT ADD COLUMN DEP longtext;");
        }
        try (final Statement statement = connection.createStatement()) {
            // Create a map of document references.
            final Map<Long, _V07_00_00_DocRefs> map = new HashMap<>();
            try (final ResultSet resultSet = statement.executeQuery("SELECT s.ID, d.UUID, d.NAME FROM SCRIPT s JOIN SCRIPT_DEP sd ON (s.ID = sd.FK_SCRIPT_ID) JOIN SCRIPT d ON (d.ID = sd.DEP_FK_SCRIPT_ID);")) {
                while (resultSet.next()) {
                    final long id = resultSet.getLong(1);
                    final String uuid = resultSet.getString(2);
                    final String name = resultSet.getString(3);
                    final DocRef docRef = new DocRef("Script", uuid, name);

                    _V07_00_00_DocRefs docRefs = map.get(id);
                    if (docRefs == null) {
                        docRefs = new _V07_00_00_DocRefs();
                        map.put(id, docRefs);
                    }
                    docRefs.add(docRef);
                }
            }

            final _V07_00_00_ObjectMarshaller<_V07_00_00_DocRefs> objectMarshaller = new _V07_00_00_ObjectMarshaller<>(_V07_00_00_DocRefs.class);
            for (final Map.Entry<Long, _V07_00_00_DocRefs> entry : map.entrySet()) {
                final String xml = objectMarshaller.marshal(entry.getValue());
                try (final PreparedStatement preparedStatement = connection.prepareStatement("UPDATE SCRIPT SET DEP = ? WHERE ID = ?")) {
                    preparedStatement.setString(1, xml);
                    preparedStatement.setLong(2, entry.getKey());
                    preparedStatement.executeUpdate();
                }
            }
        }

        try (final Statement statement = connection.createStatement()) {
            statement.executeUpdate("DROP TABLE SCRIPT_DEP;");
        }
    }
}
