package asla_client.controller;


import asla_client.AppConstants;

import asla_client.utils.RSACryptoClient;
import asla_client.utils.StringResource;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.Objects;
import java.util.ResourceBundle;

public class PrimaryController implements Initializable {


    @FXML
    private BorderPane mainContent;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AppConstants.getInstance();
        System.out.println("Primary Control active");

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

    private String encodeValue(String value) {
        try {
            return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }


    private void loadScene(String fxml) {
        try {
            Pane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(StringResource.ROOT_VIEW + fxml)));
            mainContent.setCenter(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeApp(ActionEvent actionEvent) {
        Platform.exit();
    }
}

