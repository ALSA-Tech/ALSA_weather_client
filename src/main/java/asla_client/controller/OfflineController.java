package asla_client.controller;

import asla_client.AppConstants;
import asla_client.models.Location;
import asla_client.utils.StringResource;
import asla_client.utils.WriteReadFiles;

import javafx.beans.property.ObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;


public class OfflineController implements Initializable {

    @FXML
    private Label dateTime;
    @FXML
    private Pane mainContent;

    @FXML
    private ChoiceBox<String> dropDownBox;
    @FXML
    private LineChart<String, Double> lineGraph;

    private final WriteReadFiles writeReadFiles = new WriteReadFiles();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lineGraph.getXAxis().setLabel("Date");
        lineGraph.getYAxis().setLabel("Celsius  (°C)");
        lineGraph.setAnimated(false);


        ArrayList<Location> locations = (ArrayList<Location>) writeReadFiles.readObjectFile(new File(StringResource.FILE_CACHE));

        for (Location location : locations) {
            dropDownBox.getItems().add(location.getLocation());
        }
        dropDownBox.getSelectionModel().selectFirst();
        int index = dropDownBox.getSelectionModel().getSelectedIndex();
        //Setting the data to Line chart
        String label = locations.get(index).getRequestTimeStamp().replace("T", " ").substring(0, 19);
        dateTime.setText(label);
        lineGraph.getData().add(getSeries(locations.get(index)));

        dropDownBox.setOnAction((event) -> {
            lineGraph.getData().clear();
            int selectedIndex = dropDownBox.getSelectionModel().getSelectedIndex();
            String label2 = locations.get(selectedIndex).getRequestTimeStamp().replace("T", " ").substring(0, 19);
            dateTime.setText(label2);
            lineGraph.getData().add(getSeries(locations.get(selectedIndex)));
        });

    }

    private XYChart.Series<String, Double> getSeries(Location location){
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

    private void loadScene(String fxml) {
        try {
            Pane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(StringResource.ROOT_VIEW + fxml)));
            mainContent.getChildren().setAll(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void goLogin(ActionEvent actionEvent) {
        loadScene("login_pane.fxml");
    }
}
