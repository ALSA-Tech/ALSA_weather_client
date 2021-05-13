package asla_client.controller;

import asla_client.logic_controllers.HTTPController;
import asla_client.logic_controllers.InputController;
import asla_client.logic_controllers.ScorpioZHash;
import asla_client.logic_controllers.StringResource;
import asla_client.models.Client;
import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.Objects;
import java.util.ResourceBundle;


public class RegistrationController implements Initializable {

    @FXML
    private Pane mainContent;
    @FXML
    private TextField textInputUsername, textInputEmail;
    @FXML
    private PasswordField textInputPassword,textInputPasswordCtrl;
    @FXML
    private Label errorText;

    private final InputController inputController = new InputController();
    private final HTTPController httpController = new HTTPController();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        errorText.setText("");

    }

    public void createAccBtn(ActionEvent actionEvent) {
        errorText.setText("");
        ScorpioZHash scorpioZHash = new ScorpioZHash();
        Gson gson = new Gson();

        String userName = textInputUsername.getText().trim();
        String email = textInputEmail.getText().trim();
        String passWord = textInputPassword.getText().trim();
        String passWordCheck = textInputPasswordCtrl.getText().trim();

        if (inputController.CheckInputsIsValid(userName,email, passWord,passWordCheck)) {
            if (inputController.validEmail(email)) {
                if (inputController.passwordMatch(passWord, passWordCheck)) {
                    System.out.println("Valid registration");
                    String hassPass = scorpioZHash.generateHash(passWord);
                    Client user = new Client(null,userName,hassPass,email,null);
                    String json = gson.toJson(user);
                    System.out.println(json);
                    httpController.postRequest(json);

                } else {
                    errorText.setText("Password don't Match!");
                }
            }else {
                errorText.setText("Email is not a valid email");
            }
        } else {
            errorText.setText("InputField is missing!");
        }
    }

    public void cancelBtn(ActionEvent actionEvent) {
     //   loadScene("login_pane.fxml");

        String json = new StringBuilder()
                .append("{")
                .append(MessageFormat.format("\"name\": \"{0}\",", "James Bond"))
                .append(MessageFormat.format("\"password\": \"{0}\",", "65536:D4+giM6wPFKyrVNArKU8Jw==:UA23HI6zsXxcPwXB1ZoNy0s7TZaVYBpu5O4ttdgRM3+M4yHWhWxwIb90sYxuFsx8RorcyFwm3JJtl2PNt64Urg=="))
                .append(MessageFormat.format("\"locationSubscriptions\": {0}", "[\"eslöv\",\"malmö\"]"))
                .append("}")
                .toString();

        System.out.println(json);

        httpController.postRequest(json);
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
