package asla_client.controller;

import asla_client.AppConstants;
import asla_client.utils.*;
import asla_client.models.Client;
import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;


public class RegistrationController implements Initializable {

    @FXML
    private Pane mainContent;
    @FXML
    private TextField textInputUsername, textInputEmail;
    @FXML
    private PasswordField textInputPassword, textInputPasswordCtrl;
    @FXML
    private Label errorText;

    private final InputController inputController = new InputController();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        errorText.setText("");

    }

    public void createAccBtn(ActionEvent actionEvent) {
        errorText.setText("");

        String userName = textInputUsername.getText().trim();
        String email = textInputEmail.getText().trim();
        String passWord = textInputPassword.getText().trim();
        String passWordCheck = textInputPasswordCtrl.getText().trim();

        if (inputController.CheckInputsIsValid(userName, email, passWord, passWordCheck)) {
            if (inputController.validEmail(email)) {
                if (inputController.passwordMatch(passWord, passWordCheck)) {
                    Client user = new Client(-1, userName, passWord, email);
                    sendRegistrationRequest(user);

                    /*
                    RSACryptoClient cryptoClient = new RSACryptoClient();
                    String encrypted = cryptoClient.encrypt(AppConstants.getInstance().getServerPublicKey(), data);
                    ClientRequest request = new ClientRequest(encrypted);
                    String json = "{" + MessageFormat.format("\"data\": \"{0}\"", request.getData()) + "}";

                    System.out.println(json);


                    System.out.println(AppConstants.getInstance().getHttpController().postRequest(data,""));
   */
                } else {
                    errorText.setText("Password don't Match!");
                }
            } else {
                errorText.setText("Email is not a valid email");
            }
        } else {
            errorText.setText("InputField is missing!");
        }
    }


    public void cancelBtn(ActionEvent actionEvent) {

        loadScene("login_pane.fxml");
    }

    private void sendRegistrationRequest(Client user) {
        new Thread(() -> {
            // DO a request here
            String lol = AppConstants.getInstance().getHttpController().postRequest(user, "register");
            Client client = new Gson().fromJson(lol, Client.class);
            if (client != null) {
                //Update GUI thread:
                Platform.runLater(() -> {
                    loadScene("login_pane.fxml");
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
}
