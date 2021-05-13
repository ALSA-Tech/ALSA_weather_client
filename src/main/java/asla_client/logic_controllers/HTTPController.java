package asla_client.logic_controllers;

import asla_client.models.JsonTestClass;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.*;
import java.time.Duration;


public class HTTPController {
    private final HttpClient client;
    private final Gson gson;

    public HTTPController() {
        client = HttpClient.newHttpClient();
        gson = new Gson();
    }

    //TODO split up, prepare real,Routes
    public String sendGet(String numberOne, String numberTwo){
        String uri = String.format("https://seb-noren-cloud.herokuapp.com/calc?operation=add&numberone=%s&numbertwo=%s", numberOne, numberTwo);
        HttpResponse<String> response = null;
        try {
            HttpRequest request = buildGet(uri);
            response = client.send(request, BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        printResponse(response);

        return response.body();
    }

    // EXAMPEL OF JSON PARSING Method
    public String sendGetJSON(){
        String uri = "https://jsonplaceholder.typicode.com/todos/1";
        HttpResponse<String> response = null;
        try {
            HttpRequest request = buildGet(uri);
            response = client.send(request, BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        printResponse(response);

        return response.body();
    }

    public <T> T sendGetJSONParse(Class<T> componentType){
        String uri = "https://jsonplaceholder.typicode.com/todos/1";
        T jsonTestClass = null;
        HttpResponse<String> response = null;
        try {
            HttpRequest request = buildGet(uri);
            response = client.send(request, BodyHandlers.ofString());
            jsonTestClass = gson.fromJson(response.body(), componentType);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        printResponse(response);

        return jsonTestClass;
    }


    public String postRequest(String stringJSON){
        String uri = "http://localhost:8080/api/client";
        HttpResponse<String> response = null;

        try {
            HttpRequest request = buildPostJSON(stringJSON, uri);
            response = client.send(request, BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        printResponse(response);

        return response.body();
    }

    public String postRequest(Object data){
        String uri = "https://seb-noren-cloud.herokuapp.com/getWordLengthFrequency";
        HttpResponse<String> response = null;
        String stringJSON = gson.toJson(data);
        System.out.println("json string: " + stringJSON);

        try {
            HttpRequest request = buildPostJSON(stringJSON, uri);
            response = client.send(request, BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        printResponse(response);

        return response.body();
    }


    private HttpRequest buildPostJSON(String stringJSON, String uri){
        return HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(stringJSON))
                .uri(URI.create(uri))
                .timeout(Duration.ofSeconds(30))
                .setHeader("User-Agent", "ALSA-Tech")
                .header("Content-Type", "application/json")
                .build();
    }

    private HttpRequest buildGet(String uri){
        return HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(uri))
                .timeout(Duration.ofSeconds(30))
                .setHeader("User-Agent", "ALSA-Tech")
                .build();
    }

    private void printResponse(HttpResponse<String> response){
        if (response !=null) {
            // print status code, headers, body
            System.out.println(response.statusCode());
            System.out.println(response.headers().map());
            System.out.println(response.body());
        }
    }
}
