package asla_client.controller;

import asla_client.AppConstants;
import asla_client.models.Client;
import asla_client.utils.InputController;
import asla_client.utils.StringResource;
import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;


public class LoginController implements Initializable {


    @FXML
    private Pane mainContent;
    @FXML
    private TextField textInputUsername;
    @FXML
    private PasswordField textInputPassword;


    private final InputController inputController = new InputController();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        textInputPassword.setOnKeyReleased(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                login();
            }
        });
    }

    public void login() {

        String userName = textInputUsername.getText().trim();
        String passWord = textInputPassword.getText().trim();

        if (inputController.CheckInputsIsValid(userName, passWord)) {
            sendLoginRequest(userName, passWord);
        } else {
            System.out.println("Input missing");
        }

    }

    private void sendLoginRequest(String userName, String password) {
        new Thread(() -> {
            clearTextFields();

            if (AppConstants.getInstance().isServerCon()) {
                // DO a request here
                Client loginClient = new Client(-1, null, password, userName,null);
                String lol = AppConstants.getInstance().getHttpController().postRequest(loginClient, "login");
                System.out.println(lol);
                Client client = new Gson().fromJson(lol, Client.class);
                if (client != null) {
                    AppConstants.getInstance().setLoggedInClient(client);
                    AppConstants.getInstance().setOfflineMODE(false);
                    Platform.runLater(() -> {
                        loadScene("user_pane.fxml");
                    });

                } else {
                    Platform.runLater(() -> {
                        Alert alert = new Alert(Alert.AlertType.NONE);
                        alert.setTitle("Failed Login");
                        alert.setContentText("Login Failed!");
                        alert.getButtonTypes().add(ButtonType.OK);
                        alert.show();
                    });
                }
            } else {
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.NONE);
                    alert.setTitle("Server Failure");
                    alert.setContentText("Not Connected to Server!");
                    alert.getButtonTypes().add(ButtonType.OK);
                    alert.show();
                });
            }
        }).start();
    }

    private void loadScene(String fxml) {
        try {
            Pane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(StringResource.ROOT_VIEW + fxml)));
            mainContent.getChildren().setAll(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void clearTextFields() {
        textInputUsername.clear();
        textInputPassword.clear();
    }

    public void registration() {
        if (AppConstants.getInstance().isServerCon()) {
            // DO a request here
            loadScene("registration_pane.fxml");
        } else {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.NONE);
                alert.setTitle("Server Failure");
                alert.setContentText("Not Connected to Server!");
                alert.getButtonTypes().add(ButtonType.OK);
                alert.show();
            });
        }
    }

    public void offlineBtn() {
        loadScene("offline_user_pane.fxml");
    }
}
