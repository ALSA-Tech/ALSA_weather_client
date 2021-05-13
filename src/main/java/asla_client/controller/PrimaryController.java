package asla_client.controller;


import asla_client.AppConstants;

import asla_client.logic_controllers.StringResource;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;


import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class PrimaryController implements Initializable {


    @FXML
    private BorderPane mainContent;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AppConstants.getInstance();
        System.out.println("Primary Control active");

        loadScene("login_pane.fxml");


    }

    private void loadScene(String fxml) {
        try {
            Pane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(StringResource.ROOT_VIEW + fxml)));
            mainContent.setCenter(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

