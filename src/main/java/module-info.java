module OOPThesis {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.net.http;
    requires com.google.gson;
    opens asla_client to javafx.fxml;
    opens asla_client.controller to javafx.fxml;
    opens asla_client.models to javafx.fxml;
    exports asla_client;
    exports asla_client.controller;
    exports asla_client.models;


}