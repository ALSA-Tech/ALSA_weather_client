package asla_client.controller;

import asla_client.logic_controllers.HTTPController;
import asla_client.logic_controllers.InputController;
import asla_client.logic_controllers.ScorpioZHash;
import asla_client.logic_controllers.StringResource;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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


    private final HTTPController httpController = new HTTPController();
    private final InputController inputController = new InputController();

    private static final String savedPassword = "65536:U02HkLVPRqQNru5Xfnk5Hw==:wvVaFTZWu/QNNxjHznRkVye+AVv/NgB8Tugf+obUtk0+Tk7QCqdGGFaS51cdQCAk6P+GrQrsADh+VfKx1NDIIg==";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


    public void login() {
        ScorpioZHash scorpioZHash = new ScorpioZHash();

        String userName = textInputUsername.getText().trim();
        String passWord = textInputPassword.getText().trim();

        if (inputController.CheckInputsIsValid(userName, passWord)) {
            // TODO Send to server
            if (scorpioZHash.validatePassword(passWord, savedPassword)) {
                System.out.println("Login");
                loadScene("user_pane.fxml");
            } else {
                System.out.println("Password does not match");
            }
        } else {
            System.out.println("Input missing");
        }


        //  loadScene("/asla_client/fxml/second_pane.fxml");
        /*
        httpController.sendGet("5", "12");
        httpController.sendGetJSON();

        String s = "O Hi this a test Car River Deer Car Bear and";
        String json = new StringBuilder()
                .append("{")
                .append(MessageFormat.format("\"data\": \"{0}\"", s))
                .append("}").toString();


        httpController.postRequest(json);

        JsonTestClass test = httpController.sendGetJSONParse(JsonTestClass.class);

        System.out.println(test.toString());


        //Run a separate thread for request and response.
        new Thread(new Runnable() {
            @Override
            public void run() {


                // DO a request here

                //Update GUI thread:
                Platform.runLater(() -> {
                    //   textArea.setText(cx);
                });
            }
        }).start();
 */
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
        loadScene("registration_pane.fxml");
    }
}
