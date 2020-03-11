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

package stroom.index.impl;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import stroom.index.shared.IndexDoc;
import stroom.index.shared.IndexField;
import stroom.index.shared.IndexShardKey;
import stroom.node.shared.Node;
import stroom.test.common.util.test.StroomUnitTest;
import stroom.util.concurrent.SimpleExecutor;
import stroom.util.io.FileUtil;

import java.util.List;

class TestIndexShardPoolImpl2 extends StroomUnitTest {
    @BeforeEach
    void before() {
        FileUtil.deleteContents(getCurrentTestDir().resolve("index"));
    }

    @Test
    void testThreadingLikeTheRealThing() throws InterruptedException {
        final IndexField indexField = IndexField.createField("test");
        final List<IndexField> indexFields = IndexFieldUtil.createStreamIndexFields();
        indexFields.add(indexField);

        final Node defaultNode = new Node();
        defaultNode.setName("TEST");

        try {
            final Indexer indexer = new Indexer() {
                @Override
                public void addDocument(final IndexShardKey key,
                                        final Document document) {

                }
            };

            final IndexDoc index = new IndexDoc();
            index.setUuid("1");
            index.setFields(indexFields);
            index.setMaxDocsPerShard(1000);

            final IndexShardKey indexShardKey = IndexShardKeyUtil.createTestKey(index);
            final SimpleExecutor simpleExecutor = new SimpleExecutor(10);

            for (int i = 0; i < 1000; i++) {
                simpleExecutor.execute(() -> {
                    for (int i1 = 0; i1 < 100; i1++) {
                        // Do some work.
                        final Field field = FieldFactory.create(indexField, "test");
                        final Document document = new Document();
                        document.add(field);
                        indexer.addDocument(indexShardKey, document);
                    }
                });
            }

            simpleExecutor.stop(false);
        } catch (final RuntimeException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
