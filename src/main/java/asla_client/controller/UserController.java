package asla_client.controller;

import asla_client.AppConstants;
import asla_client.utils.InputController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextField;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Map;
import java.util.ResourceBundle;


public class UserController implements Initializable {

    @FXML
    private LineChart lineGraph;
    @FXML
    private TextField textInput;

    private InputController inputController = new InputController();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lineGraph.getXAxis().setLabel("xAxis");
        lineGraph.getYAxis().setLabel("yAxis");

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Some shit");


        series.getData().add(new XYChart.Data(LocalDate.now().toString(), 240));
        series.getData().add(new XYChart.Data(LocalDate.now().plusDays(1).toString(), 300));

        //Setting the data to Line chart
        lineGraph.getData().add(series);

    }

    public void request(ActionEvent actionEvent) {

        String input = textInput.getText().trim();
        if (inputController.CheckInputsIsValid(input)){


            String uri = String.format("search-location/%s", encodeValue(input));

            String lol = AppConstants.getInstance().getHttpController().sendGet(uri);
            System.out.println(lol);


        }

    }

    private String encodeValue(String value) {
        try {
            return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

}
