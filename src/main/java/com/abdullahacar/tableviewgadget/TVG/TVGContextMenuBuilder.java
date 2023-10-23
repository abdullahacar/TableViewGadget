/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abdullahacar.tableviewgadget.TVG;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.input.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ResourceBundle;

public class TVGContextMenuBuilder {

    private Gson gson;
    private MenuItem mi;
    private MenuItem miGiveInfo;
    private ContextMenu cm = new ContextMenu();
    private ContextMenu cmGiveInfo = new ContextMenu();
    private Clipboard clipboard = Clipboard.getSystemClipboard();
    private ClipboardContent content = new ClipboardContent();
    ResourceBundle resourceBundle;

    public TVGContextMenuBuilder() {

        String copyCell = "Copy Cell";
        String info = "Info";

        this.mi = new MenuItem(copyCell);
        this.miGiveInfo = new MenuItem(info); 

        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .disableHtmlEscaping()
                .create();
    }

    public static void build(TableView tableView) {

        TVGContextMenuBuilder builder = new TVGContextMenuBuilder();

        builder.setCopyFunction(tableView);
    }

    public void setCopyFunction(TableView tableView) {
        cm.getItems().add(mi);
        mi.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Object clickedRow = tableView.getSelectionModel().getSelectedItem();
                if (clickedRow == null) {
                    return;
                }
                TableView.TableViewSelectionModel selectionModel = tableView.getSelectionModel();
                ObservableList selectedItem = selectionModel.getSelectedCells();
                TablePosition getName = (TablePosition) selectedItem.get(0);
                Object val = getName.getTableColumn().getCellData(clickedRow);
                content.putString(val + "");
                clipboard.setContent(content);
            }
        });

        final KeyCodeCombination keyCodeCopy = new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_ANY);
        tableView.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (keyCodeCopy.match(event)) {
                    Object clickedRow = tableView.getSelectionModel().getSelectedItem();
                    TableView.TableViewSelectionModel selectionModel = tableView.getSelectionModel();
                    ObservableList selectedItem = selectionModel.getSelectedCells();
                    TablePosition getName = (TablePosition) selectedItem.get(0);
                    Object val = getName.getTableColumn().getCellData(clickedRow);
                    content.putString(val + "");
                    clipboard.setContent(content);
                }
            }
        });

        if (tableView.getContextMenu() == null) {
            tableView.setContextMenu(cm);
        } else {
            tableView.getContextMenu().getItems().add(mi);
        }

    }

}
