/*
 * Copyright 2018 Crown Copyright
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

package stroom.search.solr;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import stroom.docstore.api.DocumentActionHandlerBinder;
import stroom.importexport.api.ImportExportActionHandler;
import stroom.search.solr.indexing.SolrIndexingElementModule;
import stroom.search.solr.search.SolrAsyncSearchTask;
import stroom.search.solr.search.SolrAsyncSearchTaskHandler;
import stroom.search.solr.search.SolrEventSearchTask;
import stroom.search.solr.search.SolrEventSearchTaskHandler;
import stroom.search.solr.search.StroomSolrIndexQueryResource;
import stroom.search.solr.shared.SolrIndexDoc;
import stroom.task.api.TaskHandlerBinder;
import stroom.util.entityevent.EntityEvent;
import stroom.util.entityevent.EntityEvent.Handler;
import stroom.util.guice.GuiceUtil;
import stroom.util.shared.Clearable;
import stroom.util.shared.RestResource;

public class SolrSearchModule extends AbstractModule {
    @Override
    protected void configure() {
        install(new SolrIndexingElementModule());
        install(new SolrJobsModule());

        bind(SolrIndexCache.class).to(SolrIndexCacheImpl.class);
        bind(SolrIndexClientCache.class).to(SolrIndexClientCacheImpl.class);
        bind(SolrIndexStore.class).to(SolrIndexStoreImpl.class);

        GuiceUtil.buildMultiBinder(binder(), RestResource.class)
                .addBinding(SolrIndexResourceImpl.class)
                .addBinding(NewUiSolrIndexResource.class)
                .addBinding(StroomSolrIndexQueryResource.class);

        TaskHandlerBinder.create(binder())
                .bind(SolrAsyncSearchTask.class, SolrAsyncSearchTaskHandler.class)
                .bind(SolrEventSearchTask.class, SolrEventSearchTaskHandler.class);

        final Multibinder<Handler> entityEventHandlerBinder = Multibinder.newSetBinder(binder(), EntityEvent.Handler.class);
        entityEventHandlerBinder.addBinding().to(SolrIndexCacheImpl.class);

        GuiceUtil.buildMultiBinder(binder(), Clearable.class)
                .addBinding(SolrIndexCacheImpl.class)
                .addBinding(SolrIndexClientCacheImpl.class);

        GuiceUtil.buildMultiBinder(binder(), ImportExportActionHandler.class)
                .addBinding(SolrIndexStoreImpl.class);

        DocumentActionHandlerBinder.create(binder())
                .bind(SolrIndexDoc.DOCUMENT_TYPE, SolrIndexStoreImpl.class);
    }
}