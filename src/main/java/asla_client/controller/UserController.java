package asla_client.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;


public class UserController implements Initializable {

    @FXML
    private LineChart lineGraph;
    @FXML
    private TextField textInput;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lineGraph.getXAxis().setLabel("xAxis");
        lineGraph.getYAxis().setLabel("yAxis");

        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName("Some shit");


        series.getData().add(new XYChart.Data(2000, 240));
        series.getData().add(new XYChart.Data(2014, 300));

        //Setting the data to Line chart
        lineGraph.getData().add(series);

    }

    public void request(ActionEvent actionEvent) {
    }
}
