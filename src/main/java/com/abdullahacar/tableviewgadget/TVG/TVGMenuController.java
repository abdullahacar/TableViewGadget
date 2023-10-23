/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.abdullahacar.tableviewgadget.TVG;

import com.abdullahacar.tableviewgadget.utils.Util;
import com.sun.javafx.tk.FileChooserType;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class TVGMenuController implements Initializable {

    TableView table;
    TableColumn barcodeColumn;
    int numOfPages = 1;
    int rowsPerPage = 50;
    int currentPage = 0;

    @FXML
    ComboBox<Integer> cbPageSize;

    @FXML
    Pagination pagination;

    @FXML
    private Label lblSelectionCount;

    @FXML
    private Label lblTotalCount;

    @FXML
    Button btnExportExcel;

    @FXML
    Button btnClearFilter;

    @FXML
    Button btnPopUp;

    ResourceBundle resourceBundle;

    BooleanProperty pagingChangedProperty;

    BooleanProperty filtersClearedProperty;

    private static CellStyle headerStyle;

    private static CellStyle style;

    private static final SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        resourceBundle = rb;
        try {
            btnExportExcel.setVisible(false);
            pagination.setVisible(true);
            pagination.setPageCount(1);
            //Set page counts
            cbPageSize.getItems().add(20);
            cbPageSize.getItems().add(50);
            cbPageSize.getItems().add(100);
            cbPageSize.getItems().add(250);
            cbPageSize.getItems().add(500);
            cbPageSize.getItems().add(750);
            cbPageSize.getItems().add(1000);
            cbPageSize.getItems().add(2000);
            cbPageSize.getItems().add(5000);
            cbPageSize.getItems().add(10000);
            cbPageSize.getSelectionModel().select(1);

            //Combo Page Size
            cbPageSize.managedProperty().bind(cbPageSize.visibleProperty());
            cbPageSize.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Integer>() {
                @Override
                public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                    rowsPerPage = cbPageSize.getValue();
                    pagingChangedProperty.set(!pagingChangedProperty.getValue());
                }
            });

            //Paginator
            pagination.managedProperty().bind(pagination.visibleProperty());
            pagination.currentPageIndexProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    pagingChangedProperty.set(!pagingChangedProperty.getValue());
                }

            });

        } catch (NullPointerException ex) {
        }
    }


    public void setTableView(TableView table, boolean exportExcelVisible, BooleanProperty pagingChangedProperty, BooleanProperty filtersClearedProperty) {

        this.table = table;
        this.pagingChangedProperty = pagingChangedProperty;
        this.filtersClearedProperty = filtersClearedProperty;

        try {
            this.btnExportExcel.setVisible(exportExcelVisible);
        } catch (NullPointerException ex) {
        }

        if (table.getSelectionModel() != null) {
            lblSelectionCount.textProperty().bind(Bindings.size(table.getSelectionModel().getSelectedItems()).asString());
        } else {
            lblSelectionCount.setText("0");
        }

//        lblTotalCount.textProperty().bind(Bindings.size(table.getItems()).asString());
        pagingChangedProperty.addListener(new ChangeListener<Object>() {
            @Override
            public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {
                refreshPagination();
            }
        });

    }

    public void setTableView(TableView table, boolean exportExcelVisible, boolean paginationVisible, BooleanProperty filtersClearedProperty) {

        this.table = table;
        this.filtersClearedProperty = filtersClearedProperty;

        try {
            this.pagination.setVisible(paginationVisible);
        } catch (NullPointerException ex) {
        }

        try {
            this.btnExportExcel.setVisible(exportExcelVisible);
        } catch (NullPointerException ex) {
        }

        if (table.getSelectionModel() != null) {
            lblSelectionCount.textProperty().bind(Bindings.size(table.getSelectionModel().getSelectedItems()).asString());
        } else {
            lblSelectionCount.setText("0");
        }

    }

    public void refreshPagination() {

        long count = table.getUserData() == null ? 0 : (long) table.getUserData();
        int pages = (int) Math.ceil((double) count / rowsPerPage);
        Platform.runLater(() -> {
            pagination.setPageCount(pages == 0 ? 1 : pages);
            if (pagination.getCurrentPageIndex() > pages) {
                pagination.setCurrentPageIndex(0);
            }
            lblTotalCount.setText(table.getItems().size() + "");
        });
    }

    public void freezePagination() {
        int pages = 0;
        Platform.runLater(() -> {
            pagination.setPageCount(pages == 0 ? 1 : pages);
            if (pagination.getCurrentPageIndex() > pages) {
                pagination.setCurrentPageIndex(0);
            }
        });

    }

    public void handleCountLabel() {
        lblTotalCount.setText(table.getItems().size() + "");
    }

    @FXML
    public void onCopyAllAll(ActionEvent event) {
//        WarehouseUtility.getValueList(table, null, false);
    }

    @FXML
    public void onCopySelectionAll(ActionEvent event) {
        // WarehouseUtility.getValueList(table, null, true);
    }

    @FXML
    void onSelectAll(ActionEvent event) {
        table.getSelectionModel().clearSelection();
        table.getSelectionModel().selectAll();
    }

    @FXML
    void onExportExcel(ActionEvent event) {

        String outputDirPath = null;
        FileOutputStream fileOut = null;
        outputDirPath = Util.fileChooser(FileChooserType.SAVE, ".xlsx");
        if (outputDirPath == null) {
            return;
        }

        try {
            fileOut = new FileOutputStream(outputDirPath);
        } catch (FileNotFoundException fnfe) {
            //Update GUI
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    Util.onMessageBox("Attention", "File already open !", Alert.AlertType.ERROR);
                }
            });
            return;
        }

        try {
            //create excel file
            SXSSFWorkbook wb = new SXSSFWorkbook(-1);
            Sheet stockSheet = wb.createSheet("Data");
            DataFormat format = wb.createDataFormat();
            CellStyle doubleStyle = wb.createCellStyle();

            int qtyOfColumnsHaveNestedColumns = 0;
            int nestedColumnCount = 0;

            ObservableList<TableColumn> columns = table.getColumns();
            //create headers
            Row headerRow = stockSheet.createRow(0);
            for (TableColumn column : columns) {
                if (column.getColumns() != null && !column.getColumns().isEmpty()) {
                    ObservableList<TableColumn> innerColumns = column.getColumns();
                    int index = 0;
                    for (TableColumn c : innerColumns) {
                        index += (column.getColumns().indexOf(c) + table.getColumns().indexOf(column) + (nestedColumnCount - qtyOfColumnsHaveNestedColumns));
                        headerRow.createCell(index).setCellValue(column.getText() + " " + c.getText());
                        index = 0;
                    }
                    qtyOfColumnsHaveNestedColumns++;
                    nestedColumnCount += innerColumns.size();
                } else {
                    headerRow.createCell(table.getColumns().indexOf(column) + (nestedColumnCount - qtyOfColumnsHaveNestedColumns)).setCellValue(column.getText());
                }
            }

            //create data      
            int row = 1;

            for (Object rowObject : table.getItems()) {
                Row dataRow = stockSheet.createRow(row);
                qtyOfColumnsHaveNestedColumns = 0;
                nestedColumnCount = 0;
                for (TableColumn column : columns) {
                    try {
                        if (column.getColumns() != null && !column.getColumns().isEmpty()) {
                            ObservableList<TableColumn> innerColumns = column.getColumns();
                            int index = 0;
                            for (TableColumn c : innerColumns) {
                                index += (column.getColumns().indexOf(c) + table.getColumns().indexOf(column) + (nestedColumnCount - qtyOfColumnsHaveNestedColumns));
                                dataRow.createCell(index).setCellValue(c.getCellObservableValue(rowObject).getValue().toString());
                                index = 0;
                            }
                            qtyOfColumnsHaveNestedColumns++;
                            nestedColumnCount += innerColumns.size();

                        } else {

                            Object o = column.getCellObservableValue(rowObject).getValue();

                            if (o instanceof Date) {

                                String strDate = formatter.format(column.getCellObservableValue(rowObject).getValue());

                                dataRow.createCell(table.getColumns().indexOf(column) + (nestedColumnCount - qtyOfColumnsHaveNestedColumns)).setCellValue(strDate);

                            } else if (o instanceof Double) {

                                double nb = Double.parseDouble(column.getCellObservableValue(rowObject).getValue().toString());

                                Cell cell = dataRow.createCell(table.getColumns().indexOf(column) + (nestedColumnCount - qtyOfColumnsHaveNestedColumns));
                                cell.setCellType(CellType.NUMERIC);
                                doubleStyle.setDataFormat(format.getFormat("0.0"));
                                cell.setCellStyle(doubleStyle);
                                cell.setCellValue(nb);

                            } else {

                                dataRow.createCell(table.getColumns().indexOf(column) + (nestedColumnCount - qtyOfColumnsHaveNestedColumns)).setCellValue(column.getCellObservableValue(rowObject).getValue().toString());

                            }
                        }

                    } catch (NullPointerException npe) {

                    }
                }

                row = row + 1;
                if (row % 100 == 0) {
                    try {

                        setStyle(wb.getSheet("Data"));

                    } catch (Exception e) {
                        //do nothing
                    }
                    ((SXSSFSheet) stockSheet).flushRows(100);
                }
            }

            if (!wb.getSheet("Data").areAllRowsFlushed()) {
                try {
                    setStyle(wb.getSheet("Data"));
                } catch (Exception e) {
                    //do nothing
                }
            }

            wb.write(fileOut);
            fileOut.close();

            String path = outputDirPath;

            Util.onMessageBox(resourceBundle.getString("File_Export_Successful"), resourceBundle.getString("Export_Successful") + path, Alert.AlertType.INFORMATION);

            Desktop.getDesktop().open(new File(outputDirPath));

//			Desktop.getDesktop().open(new File(outputDirPath));
//			Runtime.getRuntime().exec(outputDirPath);
            wb.dispose();

        } catch (IOException ioe) {
            //Update GUI
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    Util.onMessageBox(resourceBundle.getString("Attention"), resourceBundle.getString("File_Already_Open"), Alert.AlertType.ERROR);
                }
            });

        } catch (Exception ex) {
        }
    }

    @FXML
    void onClearFilter(ActionEvent event) {
        try {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    if (!table.getSortOrder().isEmpty()) {
                        table.getSortOrder().clear();
                    }
                }
            });

            filtersClearedProperty.set(!filtersClearedProperty.getValue());
        } catch (Exception e) {
        }

    }

    public int getPageSize() {
        return rowsPerPage;
    }

    public int getPage() {
        return pagination.getCurrentPageIndex();
    }

    private static void setStyle(Sheet sheet) {

        //Set font into style
        style = sheet.getWorkbook().createCellStyle();
        headerStyle = sheet.getWorkbook().createCellStyle();
        Row row = sheet.getRow(0);
        Font font = sheet.getWorkbook().createFont();
        font.setFontName("Calibri");
        style.setFont(font);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);

        try {

            for (Row r : sheet) {
                r.cellIterator().forEachRemaining((Cell cell) -> {
                    cell.setCellStyle(style);
                });
            }

            if (row != null && row.getRowNum() == 0) {
                setHeaderStyle(row);
            }

        } catch (Exception e) {
            System.out.println("Sheet: " + sheet.getSheetName() + " - auto size column error.");
        }

    }

    private static void setHeaderStyle(Row row) {

        headerStyle.setBorderBottom(BorderStyle.THIN);
        headerStyle.setBorderTop(BorderStyle.THIN);
        headerStyle.setBorderRight(BorderStyle.THIN);
        headerStyle.setBorderLeft(BorderStyle.THIN);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        headerStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.GOLD.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        try {
            for (int colNum = 0; colNum < row.getLastCellNum(); colNum++) {
                ((SXSSFSheet) row.getSheet()).trackAllColumnsForAutoSizing();
                row.getSheet().autoSizeColumn(colNum);
                row.getSheet().setColumnWidth(colNum, row.getSheet().getColumnWidth(colNum) + 1300);
                row.getSheet().setHorizontallyCenter(true);
            }
        } catch (Exception e) {
            //System.out.println("Sheet: " + row.getSheet().getSheetName() + " - auto size column error.");
        }

        row.cellIterator().forEachRemaining((Cell cell) -> {
            cell.setCellStyle(headerStyle);
        });

    }

}
