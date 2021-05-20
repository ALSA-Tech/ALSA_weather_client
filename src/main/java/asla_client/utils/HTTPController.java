package asla_client.utils;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.*;
import java.time.Duration;


public class HTTPController {
    private final HttpClient client;
    private final Gson gson;

    public HTTPController() {
        client = HttpClient.newBuilder()
                .cookieHandler(new CookieManager(null, CookiePolicy.ACCEPT_ALL))
                .build();
        gson = new Gson();
    }

    //TODO split up, prepare real,Routes
    public String sendGet(String path) {
        String uri = StringResource.SERVER_API + path;
        HttpResponse<String> response = null;
        try {
            HttpRequest request = buildGet(uri);
            response = client.send(request, BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            System.err.println("sendGet(): " + e.getMessage());
        }
        if (response.statusCode() == 200) {
            return response.body();
        }
        return null;
    }

    public Integer checkCon(String path) {
        String uri = StringResource.SERVER_API + path;
        HttpResponse<String> response = null;
        try {
            HttpRequest request = buildGet(uri);
            response = client.send(request, BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            System.err.println("checkCon(): " + e.getMessage());
            return null;

        }
        return response.statusCode();
    }

    public <T> T sendGetJSONParse(Class<T> componentType) {
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

    public String postRequest(String stringJSON, String path) {
        String uri = StringResource.SERVER_API + path;
        HttpResponse<String> response = null;
        try {
            HttpRequest request = buildPostJSON(stringJSON, uri);
            response = client.send(request, BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            System.err.println(e.getMessage());
        }
        if (response.statusCode() == 200) {
            return response.body();
        }
        return null;
    }

    public String postRequest(Object data, String path) {
        String uri = StringResource.SERVER_API + path;
        HttpResponse<String> response = null;
        String stringJSON = gson.toJson(data);
        try {
            HttpRequest request = buildPostJSON(stringJSON, uri);
            response = client.send(request, BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            System.err.println("postRequest(): " + e.getMessage());
        }
       // printResponse(response);
        if (response.statusCode() == 200) {
            return response.body();
        }
        return null;
    }


    private HttpRequest buildPostJSON(String stringJSON, String uri) {
        return HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(stringJSON))
                .uri(URI.create(uri))
                .timeout(Duration.ofSeconds(30))
                .setHeader("User-Agent", "ALSA-Tech")
                .header("Content-Type", "application/json")
                .build();
    }

    private HttpRequest buildGet(String uri) {
        return HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(uri))
                .timeout(Duration.ofSeconds(30))
                .setHeader("User-Agent", "ALSA-Tech")
                .header("Content-Type", "application/json")
                .build();
    }

    private void printResponse(HttpResponse<String> response) {
        if (response != null) {
            // print status code, headers, body
            System.out.println(response.statusCode());
            System.out.println(response.headers().map());
         //   String cookie = response.headers().firstValue("Set-Cookie").get();
     //       System.out.println(HttpCookie.parse(cookie).get(0).getValue());
      //      System.out.println(cookie);
            System.out.println(response.body());
        }
    }
}
