package asla_client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;


public class App extends Application {


    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/primary.fxml")));
        stage.setTitle("ASLA-Tech Weather App");
        stage.setScene(new Scene(root, 1024, 720));
        stage.setResizable(false);
        stage.show();
    }



    public static void main(String[] args) {
        launch();


    }



}
