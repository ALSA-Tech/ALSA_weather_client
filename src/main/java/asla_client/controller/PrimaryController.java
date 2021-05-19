package asla_client.controller;


import asla_client.App;
import asla_client.AppConstants;

import asla_client.utils.RSACryptoClient;
import asla_client.utils.StringResource;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;


import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class PrimaryController implements Initializable {

    @FXML
    private Circle connection;
    @FXML
    private BorderPane mainContent;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AppConstants.getInstance();
        checkConn();

        RSACryptoClient cryptoClient = new RSACryptoClient();
        //  String serverKey = AppConstants.getInstance().getHttpController().sendGet("search-location/location");
        //   System.out.println(serverKey);
        /*
        AppConstants.getInstance().setServerPublicKey(cryptoClient.publicKeyFromString(serverKey));

         */
        loadScene("login_pane.fxml");


/*
        String strToEncrypt = "Hello ASLA SERVER, try to read this";
        String encrypted = cryptoClient.encrypt(AppConstants.getInstance().getServerPublicKey(), strToEncrypt);
        String json = "{" + MessageFormat.format("\"data\": \"{0}\"", encrypted) + "}";
        System.out.println(AppConstants.getInstance().getHttpController().postRequest(json,"voidtest"));


 */

    }

    private void loadScene(String fxml) {
        try {
            Pane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(StringResource.ROOT_VIEW + fxml)));
            mainContent.setCenter(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void checkConn() {
        new Thread(() -> {
            // DO a request here
            while (AppConstants.getInstance().isPingFlag()) {
                try {
                    Integer con = AppConstants.getInstance().getHttpController().checkCon("connection");
                    if (con == null) {
                        connection.setFill(Color.RED);
                        System.out.println("No Connection!");
                        if (!AppConstants.getInstance().isOfflineMODE()) {
                            AppConstants.getInstance().setServerCon(false);
                            AppConstants.getInstance().setOfflineMODE(true);
                            AppConstants.getInstance().setLoggedInClient(null);
                            Platform.runLater(() -> {
                                loadScene("login_pane.fxml");

                            });
                        }
                    } else {
                        AppConstants.getInstance().setServerCon(true);
                        connection.setFill(Color.GREEN);
                    }
                    Thread.sleep(3000);
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }

            }
        }).start();
    }

    public void closeApp() {
        AppConstants.getInstance().setPingFlag(false);
        Platform.exit();
    }
}

