package asla_client.controller;


import asla_client.AppConstants;
import asla_client.HTTPController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PrimaryController implements Initializable {

    @FXML
    private Button registerBtn, loginBtn;
    @FXML
    private TextField textInputUsername, textInputPassword;
    @FXML
    private TextArea textArea;
    private HTTPController httpController = new HTTPController();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AppConstants.getInstance();
        System.out.println("Primary Control active");

    }


    public void login() {

        String numberOne = textInputUsername.getText().trim();
        String numberTwo = textInputPassword.getText().trim();

        System.out.println(numberOne);
        System.out.println(numberTwo);


        //  httpController.sendGet(numberOne,numberTwo);


        //Run a separate thread for request and response.
        new Thread(new Runnable() {
            @Override
            public void run() {

                String cx = httpController.sendGetJSON();


                //Update GUI thread:
                Platform.runLater(() -> {
                    textArea.setText(cx);
                });
            }
        }).start();


    }


}

