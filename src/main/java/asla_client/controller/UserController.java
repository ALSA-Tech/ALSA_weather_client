package asla_client.controller;

import asla_client.AppConstants;
import asla_client.models.Client;
import asla_client.models.Location;
import asla_client.models.LocationDataXY;
import asla_client.utils.InputController;
import asla_client.utils.StringResource;
import asla_client.utils.WriteReadFiles;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;


public class UserController implements Initializable {

    @FXML
    private Label user, dateTime;
    @FXML
    private Button trackBtn;
    @FXML
    private Pane mainContent;
    @FXML
    private ChoiceBox<String> dropDownBox;
    @FXML
    private LineChart<String, Double> lineGraph;
    @FXML
    private TextField textInput;

    private volatile boolean removeLocation = false;

    private final InputController inputController = new InputController();
    private final WriteReadFiles writeReadFiles = new WriteReadFiles();
    private String currentLocation = "";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        user.setText("User: " + AppConstants.getInstance().getLoggedInClient().getName().toUpperCase());
        lineGraph.getXAxis().setLabel("Date");
        lineGraph.getYAxis().setLabel("Celsius (°C)");
        lineGraph.setAnimated(false);
        trackBtn.setDisable(true);

        //    getLocationsRequest(AppConstants.getInstance().getLoggedInClient().getId());


        fillBox();

        dropDownBox.setOnAction((event) -> {
            textInput.clear();
            trackBtn.setDisable(false);
            removeLocation = true;
            String value = dropDownBox.getSelectionModel().getSelectedItem();
            if (value != null) {
                trackBtn.setText("Remove Tracking");
                sendLocationRequest(value);
            }
        });


        textInput.setOnKeyReleased(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                dropDownBox.getSelectionModel().clearSelection();
                request();
            }
        });
    }

    public void request() {
        String input = textInput.getText().trim();
        if (inputController.CheckInputsIsValid(input)) {
            textInput.clear();
            trackBtn.setText("Track Location");
            removeLocation = false;
            sendLocationRequest(input);
        }
    }

    private void sendLocationRequest(String input) {
        currentLocation = input;
        new Thread(() -> {
            String uri = String.format("search-location/%s", encodeValue(input));
            String jsonData = AppConstants.getInstance().getHttpController().sendGet(uri);
            Location location = new Gson().fromJson(jsonData, Location.class);
            if (location != null) {
                //Update GUI thread:
                AppConstants.getInstance().saveToCache(location);
                String label = location.getRequestTimeStamp().replace("T", " ").substring(0, 19);
                Platform.runLater(() -> {
                    lineGraph.getData().clear();
                    //Setting the data to Line chart
                    lineGraph.setTitle("Weather forecast for " + location.getLocation());
                    dateTime.setText(label);
                    lineGraph.getData().add(getSeries(location));
                    trackBtn.setDisable(false);
                });

            }
        }).start();
    }

    private void getLocationsRequest(int input) {
        new Thread(() -> {
            // DO a request here
            String uri = String.format("get-locations/%s", input);
            System.out.println(uri);
            String jsonData = AppConstants.getInstance().getHttpController().sendGet(uri);
            System.out.println(jsonData);
            //    ArrayList<Location> locations = new Gson().fromJson(jsonData, new TypeToken<List<Location>>(){}.getType());
            //   System.out.println(locations.toString());
            /*
            if (location != null) {
                //Update GUI thread:
                Platform.runLater(() -> {
                    lineGraph.getData().clear();
                    //Setting the data to Line chart
                    lineGraph.setTitle("Weather forecast for " + location.getLocation());
                    lineGraph.getData().add(getSeries(location));
                });

            }

             */

        }).start();
    }

    public void logOut() {
        sendLogOutRequest();
    }

    private void sendLogOutRequest() {
        new Thread(() -> {
            Client loginClient = new Client(-1, "userName", "password", null, null);
            String jsonData = AppConstants.getInstance().getHttpController().postRequest(loginClient, "logout");
            Client client = new Gson().fromJson(jsonData, Client.class);
            if (client == null) {
                AppConstants.getInstance().setLoggedInClient(null);
                Platform.runLater(() -> {
                    loadScene("login_pane.fxml");
                });
            }
        }).start();
    }

    public void trackLocationBtn() {
        new Thread(() -> {
            Client loginClient = AppConstants.getInstance().getLoggedInClient();
            if (removeLocation) {
                if (loginClient.getLocationSubscriptions().contains(currentLocation)) {
                    loginClient.getLocationSubscriptions().remove(currentLocation);
                    removeLocation = false;
                    Platform.runLater(() -> {
                        trackBtn.setText("Track Location");
                    });
                }
            } else {
                loginClient.getLocationSubscriptions().add(currentLocation);
            }
            String jsonData = AppConstants.getInstance().getHttpController().postRequest(loginClient, "update");
            Client client = new Gson().fromJson(jsonData, Client.class);
            if (client != null) {
                AppConstants.getInstance().setLoggedInClient(client);
            }
            Platform.runLater(() -> {
                fillBox();
                trackBtn.setDisable(true);
            });

        }).start();

    }

    private XYChart.Series<String, Double> getSeries(Location location) {
        XYChart.Series<String, Double> series = new XYChart.Series<>();
        series.setName("Temperature " + location.getLocation());
        for (int i = 0; i < location.getDataSeriesXY().size(); i++) {
            var data = new XYChart.Data<>(location.getDataSeriesXY().get(i).getLocalDate(), location.getDataSeriesXY().get(i).getTemp());
            data.setNode(createDataNode(data.YValueProperty()));
            series.getData().add(data);
        }
        return series;
    }

    private Node createDataNode(ObjectProperty<Double> value) {
        var label = new Label();
        label.textProperty().bind(value.asString("%,.2f (°C)"));
        var pane = new Pane(label);
        pane.setShape(new Circle(6.0));
        pane.setScaleShape(false);
        label.translateYProperty().bind(label.heightProperty().divide(-1.5));

        return pane;
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
            mainContent.getChildren().setAll(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void fillBox() {
        dropDownBox.getItems().clear();
        List<String> list = AppConstants.getInstance().getLoggedInClient().getLocationSubscriptions();
        for (String s : list) {
            if (!s.isEmpty()) {
                dropDownBox.getItems().add(s);
            }
        }
    }
}
