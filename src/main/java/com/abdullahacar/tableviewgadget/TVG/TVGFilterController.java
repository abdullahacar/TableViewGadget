/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.abdullahacar.tableviewgadget.TVG;

import com.abdullahacar.tableviewgadget.utils.Util;
import com.abdullahacar.tableviewgadget.querymodel.base.ExistsModel;
import com.abdullahacar.tableviewgadget.querymodel.base.InModel;
import com.abdullahacar.tableviewgadget.querymodel.base.OrModel;
import com.abdullahacar.tableviewgadget.querymodel.base.QueryModel;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.controlsfx.control.CheckComboBox;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.function.Consumer;

/**
 *
 * @author abdullahacar
 */
public class TVGFilterController {

    TableView tv;
    double extraSpace = 70.0d;
    BooleanProperty filterChangedProperty;

    public TVGFilterController(TableView tv, BooleanProperty filterChangedProperty) {
        this.filterChangedProperty = filterChangedProperty;
        this.tv = tv;
        insertFilterFields();
    }

    public void clearFilter() {

        for (Object column : tv.getColumns()) {
            ColumnFilterMap filterMap = (ColumnFilterMap) ((TableColumn) column).getUserData();
            if (filterMap == null) {
                continue;
            }

            switch (filterMap.getType()) {
                case TEXT:
                    TextField filterField = (TextField) ((VBox) ((TableColumn) column).getGraphic()).getChildren().get(0);
                    Platform.runLater(() -> filterField.clear());
                    break;
                case COMBO:
                case COMBOID:
                case COMBOBOOLEAN:
                case COMBO_BOOLEAN_EXIST:
                    CheckComboBox comboField = (CheckComboBox) ((VBox) ((TableColumn) column).getGraphic()).getChildren().get(0);
                    Platform.runLater(() -> comboField.getCheckModel().clearChecks());
                    break;
                case DATE:
                    DatePicker dp = (DatePicker) ((VBox) ((TableColumn) column).getGraphic()).getChildren().get(0);
                    Platform.runLater(() -> dp.setValue(null));
                    break;
                default:
                    break;
            }

        }

        filterChangedProperty.set(!filterChangedProperty.get());

    }

    private void insertFilterFields() {

        for (Object column : tv.getColumns()) {

            try {

                ColumnFilterMap filterMap = (ColumnFilterMap) ((TableColumn) column).getUserData();

                if (filterMap == null) {
                    ((TableColumn) column).setGraphic(noGraphic((TableColumn) column));
                    continue;
                }

                switch (filterMap.getType()) {
                    case TEXT:
                        ((TableColumn) column).setGraphic(textField((TableColumn) column, false));
                        break;
                    case INT:
                        ((TableColumn) column).setGraphic(textField((TableColumn) column, true));
                        break;
                    case COMBO:
                    case COMBOID:
                    case COMBOBOOLEAN:
                    case COMBO_BOOLEAN_EXIST:
                        ((TableColumn) column).setGraphic(checkComboBox((TableColumn) column));
                        break;
                    case DATE:
                        ((TableColumn) column).setGraphic(datePicker((TableColumn) column));
                        break;
                    default:
                        break;
                }

                double max = 0.0d;

                Text columnText = new Text(((TableColumn) column).getText());

                max = columnText.getLayoutBounds().getWidth();

                if (((TableColumn) column).getGraphic() != null) {
                    double graphicWidth = ((VBox) (((TableColumn) column).getGraphic())).getPrefWidth();

                    if (graphicWidth > max) {
                        max = graphicWidth;
                    }
                }

                ((TableColumn) column).setPrefWidth(max + extraSpace);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    private ButtonContainer createButtons(TableColumn column, boolean hideButtonVisible, boolean selectAllVisible, boolean clearVisible) {

        try {

            ImageView iwHide = new ImageView(new Image("com/Images/png/deleteGray.png"));
            ImageView iwClear = new ImageView(new Image("com/Images/png/deletee.png"));
            ImageView iwSelect = new ImageView(new Image("com/Images/png/select.png"));

            // Container
            final HBox box = new HBox();
            box.setSpacing(1);
            box.setPadding(new Insets(1, 0, 3, 0));

            // Hide Button
            final Button buttonHide = new Button();
            buttonHide.setGraphic(iwHide);
            buttonHide.setBackground(Background.EMPTY);
            buttonHide.setContentDisplay(ContentDisplay.CENTER);

            // Select All Button for Combo or else
            final Button buttonSelectAll = new Button();
            buttonSelectAll.setGraphic(iwSelect);
            buttonSelectAll.setBackground(Background.EMPTY);
            buttonSelectAll.setContentDisplay(ContentDisplay.CENTER);

            // Clear Button for clearing the filter value
            final Button buttonClearAll = new Button();
            buttonClearAll.setGraphic(iwClear);
            buttonClearAll.setBackground(Background.EMPTY);
            buttonClearAll.setContentDisplay(ContentDisplay.CENTER);

            if (hideButtonVisible) {
                box.getChildren().add(buttonHide);
            }
            if (selectAllVisible) {
                box.getChildren().add(buttonSelectAll);
            }
            if (clearVisible) {
                box.getChildren().add(buttonClearAll);
            }

            box.getChildren().forEach((t) -> {

                ((Button) t).prefWidthProperty().bind(((TableColumn) column).widthProperty().divide(box.getChildren().size()));

            });

            return ButtonContainer.builder().hideButton(buttonHide).selectAllButton(buttonSelectAll).clearButton(buttonClearAll).container(box).build();

        } catch (Exception e) {
            return ButtonContainer.builder().build();
        }

    }

    private VBox textField(TableColumn column, boolean numberFormat) {

        try {

            VBox box = presetVBox(column);

            TextField textField = new TextField();

            if (numberFormat) {
                textField.setTextFormatter(Util.integerTextFormatter());
            }

            textField.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    filterChangedProperty.set(!filterChangedProperty.getValue());
                }
            });

            ButtonContainer container = createButtons(column, true, false, true);

            container.getHideButton().setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    textField.clear();
                    ((TableColumn<?, ?>) column).setVisible(false);
                }
            });

            container.getClearButton().setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    textField.clear();
                }
            });

            box.getChildren().addAll(textField, container.getContainer());
            return box;
        } catch (Exception e) {
            return new VBox();
        }

    }

    private VBox checkComboBox(TableColumn column) {

        try {

            VBox box = presetVBox(column);

            ColumnFilterMap filterMap = (ColumnFilterMap) ((TableColumn) column).getUserData();

            final CheckComboBox comboField = new CheckComboBox(FXCollections.observableList(filterMap.getComboList()));
            comboField.prefWidthProperty().bind(box.widthProperty());

            try {
                comboField.getCheckModel().check(filterMap.getDefaultValue());
            } catch (Exception e) {
                System.out.println("Cannot set combo default value");
            }

            ButtonContainer container = createButtons(column, true, true, true);

            container.getClearButton().setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    comboField.getCheckModel().clearChecks();
                }
            });

            container.getHideButton().setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    ((TableColumn) column).setVisible(false);
                    comboField.getCheckModel().clearChecks();
                }
            });

            container.getSelectAllButton().setOnAction((ActionEvent event) -> {
                Platform.runLater(() -> {
                    comboField.getCheckModel().checkAll();
                });
            });

            comboField.getCheckModel().getCheckedItems().addListener(new ListChangeListener() {
                @Override
                public void onChanged(Change c) {
                    Task task = new Task<Void>() {
                        @Override
                        protected Void call() throws Exception {
                            try {
                                Thread.sleep(500);
                                return null;
                            } catch (Exception e) {
                                return null;
                            }
                        }

                        @Override
                        protected void succeeded() {
                            super.succeeded();
                            filterChangedProperty.set(!filterChangedProperty.getValue());
                        }
                    };
                    new Thread(task).start();
                }
            });

            box.getChildren().addAll(comboField, container.getContainer());

            return box;

        } catch (Exception e) {
            return new VBox();
        }

    }

    private VBox datePicker(TableColumn column) {

        try {
            VBox box = presetVBox(column);

            DatePicker datePicker = new DatePicker();

            datePicker.prefWidthProperty().bind(box.prefWidthProperty());

            datePicker.valueProperty().addListener(new ChangeListener<LocalDate>() {
                @Override
                public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) {
                    filterChangedProperty.set(!filterChangedProperty.getValue());
                }
            });

            ButtonContainer container = createButtons(column, true, false, true);

            container.getHideButton().setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    datePicker.setValue(null);
                    ((TableColumn) column).setVisible(false);
                }
            });

            container.getClearButton().setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    datePicker.setValue(null);
                }
            });

            box.getChildren().addAll(datePicker, container.getContainer());
            return box;
        } catch (Exception e) {
            return new VBox();
        }

    }

    private VBox noGraphic(TableColumn column) {

        try {
            VBox box = presetVBox(column);

            ButtonContainer container = createButtons(column, true, false, false);
            container.getHideButton().setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    ((TableColumn) column).setVisible(false);
                }
            });

            box.getChildren().addAll(container.getContainer());
            return box;

        } catch (Exception e) {
            return new VBox();
        }

    }

    private VBox presetVBox(TableColumn column) {

        VBox box = new VBox();
        box.setPadding(new Insets(0, 5, 0, 5));
        box.setAlignment(Pos.CENTER);
        box.prefWidthProperty().bind(((TableColumn) column).widthProperty());
        return box;

    }

    public QueryModel getParameters() {

        QueryModel queryModel = new QueryModel();

        for (Object column : tv.getColumns()) {

            ColumnFilterMap filterMap = (ColumnFilterMap) ((TableColumn) column).getUserData();
            if (filterMap == null) {
                continue;
            }

            switch (filterMap.getType()) {
                case TEXT:
                case INT:
                    TextField filterField = (TextField) ((VBox) ((TableColumn) column).getGraphic()).getChildren().get(0);

                    if (filterField.getText().length() == 0) {
                        continue;
                    }

                    if (filterMap.getQueryFields() != null) {
                        List<String> fields = new ArrayList<>();
                        List<String> values = new ArrayList<>();
                        for (String value : filterMap.getQueryFields()) {
                            fields.add(value);
                            values.add(filterField.getText());
                        }
                        OrModel orModel = OrModel.builder().fieldNames(fields).values(values).build();
                        queryModel.getOrModels().add(orModel);
                    } else { 
                        InModel inModel = InModel.builder().fieldName(filterMap.getKey()).values(Arrays.asList(filterField.getText())).build();
                        queryModel.getInModels().add(inModel);
                    }
                    break;

                case COMBO:
                    CheckComboBox comboField = (CheckComboBox) ((VBox) ((TableColumn) column).getGraphic()).getChildren().get(0);

                    List<String> valueList = new ArrayList<>();
                    comboField.getCheckModel().getCheckedItems().forEach(new Consumer() {
                        @Override
                        public void accept(Object t) {
                            valueList.add(t.toString());
                        }
                    });
                    if (!valueList.isEmpty()) {
                        queryModel.getInModels().add(InModel.builder().fieldName(filterMap.getKey()).values(valueList).build());
                    }
                    break;

                case COMBOID:
                    CheckComboBox comboFieldId = (CheckComboBox) ((VBox) ((TableColumn) column).getGraphic()).getChildren().get(0);

                    ArrayList<String> valueListId = new ArrayList<>();

                    comboFieldId.getCheckModel().getCheckedItems().forEach(new Consumer() {
                        @Override
                        public void accept(Object t) {
                            valueListId.add(filterMap.getIdMap().get(t));
                        }
                    });
                    if (!valueListId.isEmpty()) { 
                        queryModel.getInModels().add(InModel.builder().id(true).fieldName(filterMap.getKey()).values(valueListId).build()); 
                    }
                    break;

                case COMBO_BOOLEAN_EXIST:

                    CheckComboBox comboFieldBooleanE = (CheckComboBox) ((VBox) ((TableColumn) column).getGraphic()).getChildren().get(0);

                    ArrayList<Boolean> valueListBooleanE = new ArrayList<>();
                    comboFieldBooleanE.getCheckModel().getCheckedItems().forEach(new Consumer() {
                        @Override
                        public void accept(Object t) {
                            if (t.toString().equalsIgnoreCase("YES")) {
                                queryModel.getExistsModels().add(ExistsModel.builder().fieldName(filterMap.getKey()).exists(true).build());
                            } else if (t.toString().equalsIgnoreCase("NO")) {
                                queryModel.getExistsModels().add(ExistsModel.builder().fieldName(filterMap.getKey()).exists(false).build());
                            }
                        }
                    });

                    break;
                case COMBOBOOLEAN:
                    CheckComboBox comboFieldBoolean = (CheckComboBox) ((VBox) ((TableColumn) column).getGraphic()).getChildren().get(0);

                    ArrayList<Boolean> valueListBoolean = new ArrayList<>();

                    comboFieldBoolean.getCheckModel().getCheckedItems().forEach(new Consumer() {
                        @Override
                        public void accept(Object t) {
                            if (t.toString().equalsIgnoreCase("YES")) {
                                valueListBoolean.add(Boolean.TRUE);
                            } else if (t.toString().equalsIgnoreCase("NO")) {
                                valueListBoolean.add(Boolean.FALSE);
                            }
                        }
                    });

                    if (!valueListBoolean.isEmpty()) {
                        queryModel.getInModels().add(InModel.builder().bool(true).fieldName(filterMap.getKey()).values(valueListBoolean).build());
                    }
                    break;

                case DATE:

                    DatePicker dp = (DatePicker) ((VBox) ((TableColumn) column).getGraphic()).getChildren().get(0);

                    if (dp.getValue() == null) {
                        continue;
                    }

                    Date date = Date.from(dp.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                    Calendar c = Calendar.getInstance();
                    c.setTime(date);
                    c.add(Calendar.DATE, 1);
                    Date oneDayLater = c.getTime();
//
//                    queryModel.getBetweenDatesModels().add(BetweenDatesModel.builder().fieldName(filterMap.getKey()).startDate(date).endDate(oneDayLater).build());

                    queryModel.setCreatedDateStart(date);
                    queryModel.setCreatedDateEnd(oneDayLater);

                    break;

                default:
                    break;
            }

        }

        return queryModel;
    }

}
