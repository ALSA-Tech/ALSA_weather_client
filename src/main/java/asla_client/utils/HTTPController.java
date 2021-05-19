package asla_client.utils;

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
    public String sendGet(String path){
        String uri = StringResource.SERVER_API + path;
        System.out.println(uri);
        HttpResponse<String> response = null;
        try {
            HttpRequest request = buildGet(uri);
            response = client.send(request, BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            System.err.println(e.getMessage());
        }
      //  printResponse(response);

        return response.body();
    }

    public Integer checkCon(String path){
        String uri = StringResource.SERVER_API + path;
        HttpResponse<String> response = null;
        try {
            HttpRequest request = buildGet(uri);
            response = client.send(request, BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            System.err.println( e.getMessage());
            return null;

        }
        //  printResponse(response);

        return response.statusCode();
    }

    // EXAMPEL OF JSON PARSING Method
    public String sendGetJSON(){
        String uri = "http://localhost:8086/api/clients/search-location/test";
        HttpResponse<String> response = null;
        try {
            HttpRequest request = buildGet(uri);
            response = client.send(request, BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            System.err.println(e.getMessage());;
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
            System.err.println(e.getMessage());
        }

        printResponse(response);

        return jsonTestClass;
    }


    public String postRequest(String stringJSON, String path){
        String uri = StringResource.SERVER_API + path;
        HttpResponse<String> response = null;
        try {
            HttpRequest request = buildPostJSON(stringJSON, uri);
            response = client.send(request, BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            System.err.println(e.getMessage());
        }

        printResponse(response);

        return response.body();
    }

    public String postRequest(Object data,String path){
        String uri = StringResource.SERVER_API + path;
        HttpResponse<String> response = null;
        String stringJSON = gson.toJson(data);

        try {
            HttpRequest request = buildPostJSON(stringJSON, uri);
            response = client.send(request, BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            System.err.println(e.getMessage());
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
