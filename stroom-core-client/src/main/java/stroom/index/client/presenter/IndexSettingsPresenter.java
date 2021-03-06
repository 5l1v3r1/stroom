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
 *
 */

package stroom.index.client.presenter;

import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.ui.TextArea;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.View;
import stroom.core.client.event.DirtyKeyDownHander;
import stroom.docref.DocRef;
import stroom.entity.client.presenter.DocumentSettingsPresenter;
import stroom.entity.client.presenter.ReadOnlyChangeHandler;
import stroom.feed.client.presenter.SupportedRetentionAge;
import stroom.index.client.presenter.IndexSettingsPresenter.IndexSettingsView;
import stroom.index.shared.IndexDoc;
import stroom.index.shared.IndexDoc.PartitionBy;
import stroom.item.client.ItemListBox;
import stroom.item.client.StringListBox;

public class IndexSettingsPresenter extends DocumentSettingsPresenter<IndexSettingsView, IndexDoc> implements IndexSettingsUiHandlers {
    @Inject
    public IndexSettingsPresenter(final EventBus eventBus, final IndexSettingsView view) {
        super(eventBus, view);

        view.setUiHandlers(this);
        view.getRetentionAge().addItems(SupportedRetentionAge.values());
    }

    @Override
    protected void onBind() {
        final KeyDownHandler keyDownHander = new DirtyKeyDownHander() {
            @Override
            public void onDirty(final KeyDownEvent event) {
                setDirty(true);
            }
        };
        registerHandler(getView().getDescription().addKeyDownHandler(keyDownHander));
    }

    @Override
    public void onChange() {
        setDirty(true);
    }

    @Override
    public String getType() {
        return IndexDoc.DOCUMENT_TYPE;
    }

    @Override
    protected void onRead(final DocRef docRef, final IndexDoc index) {
        getView().getDescription().setText(index.getDescription());
        getView().setMaxDocsPerShard(index.getMaxDocsPerShard());
        getView().setShardsPerPartition(index.getShardsPerPartition());
        getView().setPartitionBy(index.getPartitionBy());
        getView().setPartitionSize(index.getPartitionSize());
        getView().getRetentionAge().setSelectedItem(SupportedRetentionAge.get(index.getRetentionDayAge()));
        getView().getVolumeGroups().setSelected(index.getVolumeGroupName());
    }

    @Override
    protected void onWrite(final IndexDoc index) {
        index.setDescription(getView().getDescription().getText().trim());
        index.setMaxDocsPerShard(getView().getMaxDocsPerShard());
        index.setShardsPerPartition(getView().getShardsPerPartition());
        index.setPartitionBy(getView().getPartitionBy());
        index.setPartitionSize(getView().getPartitionSize());
        index.setRetentionDayAge(getView().getRetentionAge().getSelectedItem().getDays());
        index.setVolumeGroupName(getView().getVolumeGroups().getSelected());
    }

    public interface IndexSettingsView extends View, ReadOnlyChangeHandler, HasUiHandlers<IndexSettingsUiHandlers> {
        TextArea getDescription();

        int getMaxDocsPerShard();

        void setMaxDocsPerShard(int maxDocsPerShard);

        int getShardsPerPartition();

        void setShardsPerPartition(int shardsPerPartition);

        int getPartitionSize();

        void setPartitionSize(int size);

        PartitionBy getPartitionBy();

        void setPartitionBy(PartitionBy partitionBy);

        ItemListBox<SupportedRetentionAge> getRetentionAge();

        StringListBox getVolumeGroups();
    }
}
