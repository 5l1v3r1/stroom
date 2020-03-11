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

package stroom.index;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import stroom.docref.DocRef;
import stroom.index.impl.IndexShardWriter;
import stroom.index.impl.IndexStore;
import stroom.index.mock.MockIndexShardWriter;
import stroom.index.mock.MockIndexShardWriterCache;
import stroom.index.shared.AnalyzerType;
import stroom.index.shared.IndexDoc;
import stroom.index.shared.IndexField;
import stroom.index.impl.IndexFieldUtil;
import stroom.index.shared.IndexShardKey;
import stroom.meta.shared.Meta;
import stroom.pipeline.PipelineStore;
import stroom.pipeline.PipelineTestUtil;
import stroom.pipeline.errorhandler.ErrorReceiverProxy;
import stroom.pipeline.errorhandler.FatalErrorReceiver;
import stroom.pipeline.factory.Pipeline;
import stroom.pipeline.factory.PipelineDataCache;
import stroom.pipeline.factory.PipelineFactory;
import stroom.pipeline.shared.PipelineDoc;
import stroom.pipeline.shared.XsltDoc;
import stroom.pipeline.shared.data.PipelineData;
import stroom.pipeline.shared.data.PipelineDataUtil;
import stroom.pipeline.state.MetaHolder;
import stroom.pipeline.xslt.XsltStore;
import stroom.test.AbstractProcessIntegrationTest;
import stroom.test.common.StroomPipelineTestFileUtil;
import stroom.util.io.StreamUtil;
import stroom.util.pipeline.scope.PipelineScopeRunnable;

import javax.inject.Inject;
import javax.inject.Provider;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TestIndexingPipeline extends AbstractProcessIntegrationTest {
    private static final String PIPELINE = "TestIndexingPipeline/TestIndexingPipeline.Pipeline.data.xml";
    private static final String SAMPLE_INDEX_INPUT = "TestIndexingPipeline/TestIndexes.out";

    private static final String SAMPLE_INDEX_XSLT = "TestIndexingPipeline/Indexes.xsl";

    @Inject
    private XsltStore xsltStore;
    @Inject
    private IndexStore indexStore;
    @Inject
    private Provider<PipelineFactory> pipelineFactoryProvider;
    @Inject
    private Provider<ErrorReceiverProxy> errorReceiverProvider;
    @Inject
    private MockIndexShardWriterCache indexShardWriterCache;
    @Inject
    private PipelineStore pipelineStore;
    @Inject
    private Provider<MetaHolder> metaHolderProvider;
    @Inject
    private PipelineDataCache pipelineDataCache;
    @Inject
    private PipelineScopeRunnable pipelineScopeRunnable;

    @BeforeEach
    @AfterEach
    void clear() {
        indexShardWriterCache.shutdown();
    }

    @Test
    void testSimple() {
        pipelineScopeRunnable.scopeRunnable(() -> {
            // Setup the XSLT.
            final DocRef xsltRef = xsltStore.createDocument("Indexing XSLT");
            final XsltDoc xsltDoc = xsltStore.readDocument(xsltRef);
            xsltDoc.setData(StreamUtil.streamToString(StroomPipelineTestFileUtil.getInputStream(SAMPLE_INDEX_XSLT)));
            xsltStore.writeDocument(xsltDoc);

            final List<IndexField> indexFields = IndexFieldUtil.createStreamIndexFields();
            // indexFields.add(IndexField.createIdField(IndexConstants.ID));
            // indexFields.add(IndexField.createIdField(IndexConstants.EVENT_ID));
            indexFields.add(IndexField.createDateField("EventTime"));
            indexFields.add(IndexField.createField("UserId", AnalyzerType.KEYWORD));
            indexFields.add(IndexField.createField("Action"));
            indexFields.add(IndexField.createField("Generator"));
            indexFields.add(IndexField.createNumericField("DeviceLocationFloor"));
            indexFields.add(IndexField.createField("DeviceHostName"));
            indexFields.add(IndexField.createField("ProcessCommand"));

            // Setup the target index
            final DocRef indexRef = indexStore.createDocument("Test index");
            IndexDoc index = indexStore.readDocument(indexRef);
            index.setFields(indexFields);
            index = indexStore.writeDocument(index);

            errorReceiverProvider.get().setErrorReceiver(new FatalErrorReceiver());

            // Set the stream for decoration purposes.
            final long id = (long) (Math.random() * 1000);

            final Meta meta = mock(Meta.class);
            when(meta.getId()).thenReturn(id);
            metaHolderProvider.get().setMeta(meta);

            // Create the pipeline.
            final DocRef pipelineRef = PipelineTestUtil.createTestPipeline(pipelineStore, StroomPipelineTestFileUtil.getString(PIPELINE));
            final PipelineDoc pipelineDoc = pipelineStore.readDocument(pipelineRef);
            pipelineDoc.getPipelineData().addProperty(PipelineDataUtil.createProperty("xsltFilter", "xslt", xsltRef));
            pipelineDoc.getPipelineData().addProperty(PipelineDataUtil.createProperty("indexingFilter", "index", indexRef));
            pipelineStore.writeDocument(pipelineDoc);

            // Create the parser.
            final PipelineData pipelineData = pipelineDataCache.get(pipelineDoc);
            final Pipeline pipeline = pipelineFactoryProvider.get().create(pipelineData);

            final InputStream inputStream = StroomPipelineTestFileUtil.getInputStream(SAMPLE_INDEX_INPUT);
            pipeline.process(inputStream);

            // Make sure we only used one writer.
            assertThat(indexShardWriterCache.getWriters().size()).isEqualTo(1);

            // Get the writer from the pool.
            final Map<IndexShardKey, IndexShardWriter> writers = indexShardWriterCache.getWriters();
            final MockIndexShardWriter writer = (MockIndexShardWriter) writers.values().iterator().next();

            // Check that we indexed 4 documents.
            assertThat(writer.getDocuments().size()).isEqualTo(4);
            assertThat(writer.getDocuments().get(0).getField("Action").stringValue()).isEqualTo("Authenticate");
            assertThat(writer.getDocuments().get(1).getField("Action").stringValue()).isEqualTo("Process");
            assertThat(writer.getDocuments().get(2).getField("Action").stringValue()).isEqualTo("Process");
            assertThat(writer.getDocuments().get(3).getField("Action").stringValue()).isEqualTo("Process");

            for (int i = 0; i < 4; i++) {
                final String streamId = writer.getDocuments().get(i).getField("StreamId").stringValue();
                final String eventId = writer.getDocuments().get(i).getField("EventId").stringValue();
                final String userId = writer.getDocuments().get(i).getField("UserId").stringValue();

                System.out.println(streamId + ":" + eventId);

                assertThat(streamId).isEqualTo(String.valueOf(id));
                assertThat(eventId).isEqualTo(Integer.toString(i + 1));
                assertThat(userId).isEqualTo("user" + (i + 1));
            }

            // // Return the writer to the pool.
            // indexShardManager.returnObject(poolItem, true);
        });
    }
}
