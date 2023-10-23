package com.abdullahacar.tableviewgadget.utils;

import com.abdullahacar.tableviewgadget.modules.delivery.DeliveryListController;
import com.sun.javafx.tk.FileChooserType;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextFormatter;
import javafx.stage.FileChooser;
import javafx.stage.StageStyle;
import org.controlsfx.control.MaskerPane;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.Optional;

public class Util {

    public static TextFormatter integerTextFormatter() {
        NumberFormat integerFormat = DecimalFormat.getIntegerInstance();
        return new TextFormatter<>(c -> {
            if (c.getControlNewText().isEmpty()) {
                return c;
            }

            ParsePosition parsePosition = new ParsePosition(0);
            Object object = integerFormat.parse(c.getControlNewText(), parsePosition);

            if (object == null || parsePosition.getIndex() < c.getControlNewText().length()) {
                return null;
            } else {
                return c;
            }
        });
    }

    public static String fileChooser(FileChooserType type, String extension) {
        File selectedFile = null;
        String outputDirPath = null;
        final FileChooser fc = new FileChooser();

        fc.getExtensionFilters().addAll(Util.getExcelExtensionFilters());

        if (type == FileChooserType.SAVE) {
            selectedFile = fc.showSaveDialog(null);
        }
        if (type == FileChooserType.OPEN) {
            selectedFile = fc.showOpenDialog(null);
        }

        if (selectedFile != null && extension != null) {
            outputDirPath = selectedFile.getAbsolutePath();
            if (!outputDirPath.endsWith(extension)) {
                outputDirPath.concat(extension);
            }
        } else {
            return null;
        }

        FileOutputStream fileOut = null;
        try {

            if (type == FileChooserType.SAVE) {
                fileOut = new FileOutputStream(outputDirPath);
                fileOut.close();
            }

            return outputDirPath;
        } catch (FileNotFoundException fnfe) {

            Util.onMessageBox("Hata", "Dosya açık", Alert.AlertType.ERROR);

            return null;

        } catch (IOException fnfe) {

            Util.onMessageBox("Hata", "Dosya açık", Alert.AlertType.ERROR);

            return null;

        }
    }

    private static FileChooser.ExtensionFilter getExcelExtensionFilters() {
        return null;
    }

    public static Optional<ButtonType> onMessageBox(String title, String message, Alert.AlertType alertType) {

        Alert alert = new Alert(alertType);
        alert.getDialogPane().getStylesheets().add(Util.class.getResource("/Styles/cdek.css").toExternalForm());
        alert.setTitle(title);
        alert.setContentText(message);
        alert.setHeaderText("Header");
        alert.initStyle(StageStyle.UTILITY);
        alert.initOwner(DeliveryListController.MASKER_PANE.getScene().getWindow());

        if (alertType == Alert.AlertType.WARNING || alertType == Alert.AlertType.ERROR) {
            Util.onMessageBox("Err", "Err", Alert.AlertType.ERROR);
        }

        if (alertType == Alert.AlertType.WARNING) {
            ButtonType buttonTypeCancel = new ButtonType("OK", ButtonBar.ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().clear();

            alert.getButtonTypes().setAll(buttonTypeCancel);
        }

        Optional<ButtonType> result = alert.showAndWait();
        return result;

    }

    public static void handleWithTask(MethodRunner method, MaskerPane maskerPane) {

        try {
            Task<Void> t = new Task<>() {
                @Override
                protected Void call() throws IOException {
                    Platform.runLater(() -> {
                        maskerPane.setVisible(true);
                        maskerPane.setText("Please Wait");
                    });

                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                    }

                    method.run();

                    return null;
                }

                @Override
                protected void succeeded() {
                    super.succeeded();
                    Platform.runLater(() -> {
                        maskerPane.setVisible(false);
                        maskerPane.setText("Please Wait");
                    });
                }
            };
            new Thread(t).start();
        } catch (Exception e) {
        }

    }

}
