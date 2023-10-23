/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abdullahacar.tableviewgadget.modules.delivery;

import com.abdullahacar.tableviewgadget.TVG.*;
import com.abdullahacar.tableviewgadget.dto.ApiResponse;
import com.abdullahacar.tableviewgadget.dto.DeliveryDTO;
import com.abdullahacar.tableviewgadget.dto.Enums.DeliveryStatusDTO;
import com.abdullahacar.tableviewgadget.dto.Enums.DeliveryTypeDTO;
import com.abdullahacar.tableviewgadget.dto.Enums.YesNo;
import com.abdullahacar.tableviewgadget.modules.Service;
import com.abdullahacar.tableviewgadget.querymodel.delivery.DeliveryQueryModel;
import com.abdullahacar.tableviewgadget.querymodel.base.QueryModel;
import com.abdullahacar.tableviewgadget.querymodel.base.SortModel;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;
import org.controlsfx.control.MaskerPane;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

public class DeliveryListController extends Service implements Initializable {

    @FXML
    private TableView<DeliveryDTO> tvDelivery;

    @FXML
    private ScrollPane spDelivery;

    @FXML
    StackPane spDeliveryList;

    private TVGadget tvGadget;

    private BooleanProperty filteredTriggeredProperty = new SimpleBooleanProperty(false);

    public static final MaskerPane MASKER_PANE = new MaskerPane();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {

            spDeliveryList.getChildren().add(MASKER_PANE);
            MASKER_PANE.setVisible(false);
            MASKER_PANE.setText("Please Wait");

            tvGadget = TVGadget.create().builder(TVGBuilderParameter.builder()
                    .scrollPane(spDelivery)
                    .tableView(tvDelivery)
                    .filterhandler(this::fillDelivery)
                    .userData(FilterableColumnsUserData.builder()
                            .list(Arrays.asList(
                                    ColumnFilterMap.builder()
                                            .key("createdDate")
                                            .type(FilterType.DATE)
                                            .column(tvDelivery.getColumns().get(0))
                                            .build(),
                                    ColumnFilterMap.builder()
                                            .key("type")
                                            .type(FilterType.COMBO)
                                            .comboList(new ArrayList(EnumSet.allOf(DeliveryTypeDTO.class)))
                                            .column(tvDelivery.getColumns().get(1))
                                            .build(),
                                    ColumnFilterMap.builder()
                                            .key("deliveryNo")
                                            .defaultValue("")
                                            .type(FilterType.TEXT)
                                            .column(tvDelivery.getColumns().get(2))
                                            .build(),
                                    ColumnFilterMap.builder()
                                            .key("status")
                                            .type(FilterType.COMBO)
                                            .comboList(new ArrayList(EnumSet.allOf(DeliveryStatusDTO.class)))
                                            .column(tvDelivery.getColumns().get(3))
                                            .build(),
                                    ColumnFilterMap.builder().key("consignor.firstName")
                                            .defaultValue("")
                                            .type(FilterType.TEXT)
                                            .column((tvDelivery.getColumns().get(4))).build(),
                                    ColumnFilterMap.builder().key("consignee.firstName")
                                            .defaultValue("")
                                            .type(FilterType.TEXT)
                                            .column((tvDelivery.getColumns().get(5))).build(),
                                    ColumnFilterMap.builder().key("integrated")
                                            .type(FilterType.COMBOBOOLEAN)
                                            .comboList(new ArrayList<>(YesNo.ALL))
                                            .column((tvDelivery.getColumns().get(6))).build()
                            ))
                            .build())
                    .triggerFilterProperty(filteredTriggeredProperty).build()).setMenu(true);

            ((TableColumn) tvDelivery.getColumns().get(0)).setCellFactory(column -> new TableCell<DeliveryDTO, Date>() {
                private SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm");

                @Override
                protected void updateItem(Date item, boolean empty) {
                    super.updateItem(item, empty);
                    try {
                        this.setText(format.format(item));
                    } catch (Exception e) {
                        setText(null);
                        setStyle("");
                    }
                }
            });

            ((TableColumn) tvDelivery.getColumns().get(6)).setCellValueFactory((Callback<TableColumn.CellDataFeatures<DeliveryDTO, String>, ObservableValue<String>>) p -> {
                try {
                    return new SimpleStringProperty(p.getValue().isIntegrated() ? YesNo.YES.name() : YesNo.NO.name());
                } catch (Exception e) {
                    return new SimpleStringProperty(YesNo.NO.name());
                }
            });

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private void fillDelivery(QueryModel query) {

        try {
            query.setSort(Arrays.asList(SortModel.builder().fieldName("createdDate").order(-1).build()));

            DeliveryQueryModel deliveryQueryModel = DeliveryQueryModel.builder()
                    .inModel(query.getInModel())
                    .inModels(query.getInModels())
                    .orModels(query.getOrModels())
                    .sort(query.getSort())
                    ._ids(query.get_ids())
                    .betweenDatesModel(query.getBetweenDatesModel())
                    .betweenDatesModels(query.getBetweenDatesModels())
                    .createdDateStart(query.getCreatedDateStart())
                    .createdDateEnd(query.getCreatedDateEnd())
                    .build();

            ApiResponse<List<DeliveryDTO>> response = getDeliveryService().getDeliveryByParameters(deliveryQueryModel);

            tvGadget.setData(response.getEntity(), response.getCount());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
