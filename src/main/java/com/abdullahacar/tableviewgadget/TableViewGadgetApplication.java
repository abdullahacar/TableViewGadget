package com.abdullahacar.tableviewgadget;

import com.abdullahacar.tableviewgadget.config.ApplicationBean;
import com.abdullahacar.tableviewgadget.dto.ApiResponse;
import com.abdullahacar.tableviewgadget.dto.AuthenticationResponseDTO;
import com.abdullahacar.tableviewgadget.dto.RegisterRequestDTO;
import com.abdullahacar.tableviewgadget.modules.delivery.DeliveryListController;
import com.abdullahacar.tableviewgadget.modules.login.LoginService;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;

import java.io.IOException;

public class TableViewGadgetApplication extends Application {

    LoginService loginService = new LoginService(ApplicationBean.getInstance());

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(TableViewGadgetApplication.class.getResource("DeliveryList.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Delivery List !");
        stage.setScene(scene);
        stage.setMaximized(true);

        stage.setOnShown(event -> {


//            ApiResponse<AuthenticationResponseDTO> responseCreateLogin = loginService.createLogin(RegisterRequestDTO.builder()
//                    .email("abdullahacar.aa@gmail.com")
//                    .password("123123")
//                    .build());
//
//            if (!responseCreateLogin.isSuccessful()) {
//
//                Platform.runLater(() -> Notifications.create()
//                        .owner(DeliveryListController.MASKER_PANE.getParent())
//                        .title("Oops !")
//                        .text(responseCreateLogin.getEntity().getMessage())
//                        .showWarning());
//
//            }
//
//            ApplicationBean.getInstance().setToken(responseCreateLogin.getEntity().getAccessToken());

            ApiResponse<AuthenticationResponseDTO> responseAuthenticateLogin = loginService.authenticateLogin(RegisterRequestDTO.builder()
                    .email("abdullahacar.aa@gmail.com")
                    .password("123123")
                    .build());
            

            if (responseAuthenticateLogin.isSuccessful()) {

                Platform.runLater(() -> Notifications.create()
                        .owner(DeliveryListController.MASKER_PANE.getParent())
                        .title("Good !")
                        .text("Login Successful ! ")
                        .showInformation());

            }

            ApplicationBean.getInstance().setToken(responseAuthenticateLogin.getEntity().getAccessToken());

        });

        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}