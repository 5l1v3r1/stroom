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

package stroom.config.global.client.presenter;

import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.MyPresenterWidget;
import stroom.config.global.shared.ConfigProperty;
import stroom.config.global.shared.FindGlobalConfigAction;
import stroom.config.global.shared.FindGlobalConfigCriteria;
import stroom.data.grid.client.DataGridView;
import stroom.data.grid.client.DataGridViewImpl;
import stroom.data.grid.client.EndColumn;
import stroom.data.table.client.Refreshable;
import stroom.dispatch.client.ClientDispatchAsync;
import stroom.entity.client.presenter.FindActionDataProvider;
import stroom.svg.client.SvgPreset;
import stroom.widget.button.client.ButtonView;

import java.util.function.Function;

public class ManageGlobalPropertyListPresenter
    extends MyPresenterWidget<DataGridView<ConfigProperty>>
    implements Refreshable {

    // TODO change to use a rest dataprovider, see NodeMonitoringPresenter
    //   Need to figure out how best to handle the fuzzy criteria with rest, i.e. POST
    //   of the criteria object or query params
    private final FindActionDataProvider<FindGlobalConfigCriteria, ConfigProperty> dataProvider;

    @Inject
    public ManageGlobalPropertyListPresenter(final EventBus eventBus,
                                             final ClientDispatchAsync dispatcher) {
        super(eventBus, new DataGridViewImpl<>(true));

        // Name.
        addColumn(buildBasicColumn(
            row ->
                row.getName().toString()),
            "Name",
            450);
        addColumn(buildBasicColumn(
            row ->
                row.getEffectiveValueMasked().orElse(null)),
            "Effective Value",
            300);
        addColumn(buildBasicColumn(
            row ->
                row.getSource().getName()),
            "Source",
            75);

        addColumn(buildDescriptionColumn(), "Description", 750);
        getView().addEndColumn(new EndColumn<>());


//        this.dataProvider = new FindActionDataProvider(dispatcher, getView(),
//                new FindGlobalConfigAction(
//                        new FindGlobalConfigCriteria()));
        this.dataProvider = new FindActionDataProvider<>(dispatcher, getView(), new FindGlobalConfigAction());

//        dataProvider = new EntityServiceFindActionDataProvider<>(dispatcher,
//                getView());
        dataProvider.setCriteria(new FindGlobalConfigCriteria());
//        refresh();
    }

    private Column<ConfigProperty, String> buildDescriptionColumn() {
        return new Column<ConfigProperty, String>(new TextCell()) {
            @Override
            public String getValue(final ConfigProperty row) {
                if (row == null) {
                    return null;
                }
                return row.getDescription();
            }
            @Override
            public String getCellStyleNames(Cell.Context context, ConfigProperty object) {
                return super.getCellStyleNames(context, object) + " "
                    + getView().getResources().dataGridStyle().dataGridCellWrapText() + " "
                    + getView().getResources().dataGridStyle().dataGridCellVerticalTop();
            }
        };
    }

//    public ImageButtonView addButton(final String title, final ImageResource enabledImage,
//                                     final ImageResource disabledImage, final boolean enabled) {
//        return getView().addButton(title, enabledImage, disabledImage, enabled);
//    }


    private Column<ConfigProperty, String> buildBasicColumn(final Function<ConfigProperty, String> valueFunc) {
        return new Column<ConfigProperty, String>(new TextCell()) {
            @Override
            public String getValue(final ConfigProperty row) {
                if (row == null) {
                    return null;
                }
                return valueFunc.apply(row);
            }
            @Override
            public String getCellStyleNames(Cell.Context context, ConfigProperty object) {
                return super.getCellStyleNames(context, object) + " "
                    + getView().getResources().dataGridStyle().dataGridCellVerticalTop();
            }
        };
    }

    private void addColumn(Column<ConfigProperty, String> column, String name, int width) {
        column.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
        getView().addResizableColumn(column, name, width);
    }

    public ButtonView addButton(final SvgPreset preset) {
        return getView().addButton(preset);
    }

    @Override
    protected void onReveal() {
        super.onReveal();
        refresh();
    }

    @Override
    public void refresh() {
        dataProvider.refresh();
//        dispatcher.exec(new FetchGlobalConfigAction(criteria)).onSuccess(result -> {
//            getView().setRowData(0, result);
//            getView().setRowCount(result.size(), true);
//        });
    }

    public ConfigProperty getSelectedItem() {
        return getView().getSelectionModel().getSelected();
    }

    public void setSelectedItem(final ConfigProperty row) {
        getView().getSelectionModel().setSelected(row);
    }

//    public void setCriteria(final FindGlobalConfigCriteria criteria) {
//        this.criteria = criteria;
//        refresh();
//    }
//
    FindGlobalConfigCriteria getFindGlobalPropertyCriteria() {
        return dataProvider.getCriteria();
    }
}
