/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.abdullahacar.tableviewgadget.TVG;

import com.abdullahacar.tableviewgadget.modules.delivery.DeliveryListController;
import com.abdullahacar.tableviewgadget.utils.Util;
import com.abdullahacar.tableviewgadget.config.AppSettings;
import com.abdullahacar.tableviewgadget.querymodel.base.QueryModel;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.*;

import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author abdullahacar
 */
public class TVGadget {

    TableView tv;
    Consumer<QueryModel> consumer;
    ITVGFilterHandler filterHandler;
    TVGFilterController filterController;
    TVGMenuController menuController;
    BooleanProperty filterChangeListenerProperty;
    FilterableColumnsUserData userData;
    boolean controllerParametersActive = false;
    QueryModel controllerParameter = new QueryModel();
    ScrollPane scrollPane;

    int index;
    VBox vbox;
    Parent menuRoot;
    Node parent;
    static long threadId;

    public static TVGadget create() {
        return new TVGadget();
    }

    public TVGadget builder(TVGBuilderParameter builderParameter) {

        this.scrollPane = builderParameter.getScrollPane();

        this.tv = builderParameter.getTableView();

        this.tv.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);

        if (builderParameter.getUserData() != null) {

            builderParameter.getUserData().getList().forEach(map -> {
                map.getColumn().setUserData(map);
            });
        }

        if (builderParameter.getFilterhandler() != null) {
            this.filterChangeListenerProperty = new SimpleBooleanProperty();
            this.filterController = new TVGFilterController(this.tv, this.filterChangeListenerProperty);
            this.filterHandler = builderParameter.getFilterhandler();

            consumer = tt -> {

                if (controllerParametersActive) {

                    QueryModel queryModel = new QueryModel();

                    final QueryModel t = filterController.getParameters();

                    queryModel.setLimit(menuController.getPageSize());
                    queryModel.setSkip(menuController.getPageSize() * menuController.getPage());
                    queryModel.getInModels().addAll(t.getInModels());
                    queryModel.getInModels().addAll(controllerParameter.getInModels());

                    if (t.getInModel() != null) {
                        queryModel.getInModels().add(t.getInModel());
                    }

                    if (controllerParameter.getInModel() != null) {
                        queryModel.getInModels().add(controllerParameter.getInModel());
                    }

                    queryModel.getOrModels().addAll(t.getOrModels());
                    queryModel.getOrModels().addAll(controllerParameter.getOrModels());

                    queryModel.getBetweenDatesModels().addAll(t.getBetweenDatesModels());
                    queryModel.getBetweenDatesModels().addAll(controllerParameter.getBetweenDatesModels());

                    if (t.getBetweenDatesModel() != null) {
                        queryModel.getBetweenDatesModels().add(t.getBetweenDatesModel());
                    }

                    if (controllerParameter.getBetweenDatesModel() != null) {
                        queryModel.getBetweenDatesModels().add(controllerParameter.getBetweenDatesModel());
                    }

                    queryModel.getExistsModels().addAll(t.getExistsModels());
                    queryModel.getExistsModels().addAll(controllerParameter.getExistsModels());

                    queryModel.getNinModels().addAll(t.getNinModels());
                    queryModel.getNinModels().addAll(controllerParameter.getNinModels());

                    if (t.getNinModel() != null) {
                        queryModel.getNinModels().add(t.getNinModel());
                    }

                    if (controllerParameter.getNinModel() != null) {
                        queryModel.getNinModels().add(controllerParameter.getNinModel());
                    }

                    filterHandler.filter(queryModel);

                } else {

                    tt.setLimit(menuController.getPageSize());
                    tt.setSkip(menuController.getPageSize() * menuController.getPage());
                    filterHandler.filter(tt);

                }
                ;

            };

            this.filterChangeListenerProperty.addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {

                Util.handleWithTask(() -> consumer.accept(filterController.getParameters()), DeliveryListController.MASKER_PANE);

            });

            if (builderParameter.getTriggerFilterProperty() != null) {

                builderParameter.getTriggerFilterProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
                    filterChangeListenerProperty.set(!filterChangeListenerProperty.get());
                });

            }

        } else {
            for (Object col : tv.getColumns()) {
                ((TableColumn) col).prefWidthProperty().bind(tv.widthProperty().divide(tv.getVisibleLeafColumns().size()));
            }
        }

        tv.setTableMenuButtonVisible(true);
        TVGContextMenuBuilder.build(tv);

        return this;

    }

    public TVGadget setMenu(boolean exportExcelVisible) {

        vbox = new VBox();
        vbox.setFillWidth(true);
        VBox.setVgrow(vbox, Priority.ALWAYS);
        HBox.setHgrow(vbox, Priority.ALWAYS);
        HBox.setHgrow(tv, Priority.ALWAYS);
        VBox.setVgrow(tv, Priority.ALWAYS);

        try {
            FXMLLoader menuLoader = new FXMLLoader(TVGadget.class.getResource("/com/abdullahacar/tableviewgadget/TableViewMenu.fxml"), AppSettings.RESOURCE_BUNDLE);
            menuRoot = menuLoader.load();
            menuController = menuLoader.getController();

            BooleanProperty filtersClearedProperty = new SimpleBooleanProperty(false);
            BooleanProperty paginationChangedProperty = new SimpleBooleanProperty(false);

            paginationChangedProperty.addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    Util.handleWithTask(() -> consumer.accept(filterController.getParameters()), DeliveryListController.MASKER_PANE);
                }
            });

            filtersClearedProperty.addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    if (filterController != null) {
                        deactivateControllerParameters();
                        filterController.clearFilter();
                    }
                }
            });

            menuController.setTableView(tv, exportExcelVisible, paginationChangedProperty, filtersClearedProperty);

        } catch (IOException ex) {
        }

        insertMenu();

        return this;

    }

    public void setData(List list, long count) {

        tv.setUserData(count);

        if (filterHandler == null) {

            Platform.runLater(() -> {
                tv.getItems().clear();
                if (!list.isEmpty()) {
                    tv.getItems().addAll(list);
                }
                tv.sort();
            });

            try {
                Platform.runLater(() -> menuController.handleCountLabel());
            } catch (Exception e) {
            }

        } else {

            Platform.runLater(() -> {
                tv.getItems().clear();
                if (list != null && !list.isEmpty()) {
                    tv.getItems().addAll(list);
                }
                tv.sort();
            });
            try {
                menuController.refreshPagination();
            } catch (Exception e) {
            }
        }

    }

    public void setDataWithNoPagination(List list, long count) {

        tv.setUserData(count);

        if (filterHandler == null) {

        } else {
            Platform.runLater(() -> {

                tv.getItems().clear();
                if (!list.isEmpty()) {
                    tv.getItems().addAll(list);
                }
                tv.sort();
            });

            menuController.freezePagination();

        }

    }

    private void insertMenu() {

        int i = 0;

        if (scrollPane != null) {
            scrollPane.pannableProperty().set(true);
            parent = scrollPane.getParent();

            if (parent instanceof Pane) {
                i = ((Pane) parent).getChildren().indexOf(scrollPane);
                ((Pane) parent).getChildren().remove(scrollPane);
                vbox.getChildren().add(menuRoot);
                vbox.getChildren().add(scrollPane);
                ((Pane) parent).getChildren().add(i, vbox);
                return;
            }

        } else {

            parent = tv.getParent();

        }

        if (parent instanceof Pane) {
            i = ((Pane) parent).getChildren().indexOf(tv);
            ((Pane) parent).getChildren().remove(tv);
            vbox.getChildren().add(menuRoot);
            vbox.getChildren().add(tv);
            ((Pane) parent).getChildren().add(i, vbox);
            return;
        }

        if (parent instanceof VBox) {
            i = ((VBox) parent).getChildren().indexOf(tv);
            ((VBox) parent).getChildren().remove(i);
            vbox.getChildren().add(menuRoot);
            vbox.getChildren().add(tv);
            ((VBox) parent).getChildren().add(i, vbox);
            return;
        }
        if (parent instanceof HBox) {
            i = ((HBox) parent).getChildren().indexOf(tv);
            ((HBox) parent).getChildren().remove(i);
            vbox.getChildren().add(menuRoot);
            vbox.getChildren().add(tv);
            ((HBox) parent).getChildren().add(i, vbox);
            return;
        }
        if (parent instanceof StackPane) {
            i = ((StackPane) parent).getChildren().indexOf(tv);
            ((StackPane) parent).getChildren().remove(i);
            vbox.getChildren().add(menuRoot);
            vbox.getChildren().add(tv);
            ((StackPane) parent).getChildren().add(i, vbox);
            return;
        }
        if (parent instanceof BorderPane) {
            i = ((BorderPane) parent).getChildren().indexOf(tv);
            ((BorderPane) parent).getChildren().remove(i);
            vbox.getChildren().add(menuRoot);
            vbox.getChildren().add(tv);
            ((BorderPane) parent).getChildren().add(i, vbox);
            return;
        }
        if (parent instanceof TilePane) {
            i = ((TilePane) parent).getChildren().indexOf(tv);
            ((TilePane) parent).getChildren().remove(i);
            vbox.getChildren().add(menuRoot);
            vbox.getChildren().add(tv);
            ((TilePane) parent).getChildren().add(i, vbox);
            return;
        }
        if (parent instanceof AnchorPane) {
            i = ((AnchorPane) parent).getChildren().indexOf(tv);
            ((AnchorPane) parent).getChildren().remove(i);
            vbox.getChildren().add(menuRoot);
            vbox.getChildren().add(tv);
            ((AnchorPane) parent).getChildren().add(i, vbox);
            return;
        }
        if (parent instanceof GridPane) {
            i = ((GridPane) parent).getChildren().indexOf(tv);
            ((GridPane) parent).getChildren().remove(i);
            vbox.getChildren().add(menuRoot);
            vbox.getChildren().add(tv);
            ((GridPane) parent).getChildren().add(i, vbox);
        }
    }

    public QueryModel getLimitAndSkip() {

        QueryModel queryModel = new QueryModel();

        try {

            queryModel.setLimit(menuController.getPageSize());
            queryModel.setSkip(menuController.getPageSize() * menuController.getPage());

        } catch (Exception e) {
        }

        return queryModel;

    }

    public void activateControllerParameters(QueryModel queryModel) {

        this.controllerParametersActive = true;

        this.controllerParameter = queryModel;

        try {
            Util.handleWithTask(() -> this.consumer.accept(this.controllerParameter), DeliveryListController.MASKER_PANE);
        } catch (Exception e) {
        }

    }

    public void deactivateControllerParameters() {

        this.controllerParametersActive = false;

        this.controllerParameter = new QueryModel();

    }

}
