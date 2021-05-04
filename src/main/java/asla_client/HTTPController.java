package asla_client;

import asla_client.models.JsonTestClass;
import com.google.gson.Gson;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.*;
import java.time.Duration;

/**
 * @author Sebastian Norén <s.norén@gmail.com>
 */
public class HTTPController {
    private  HttpClient client;

    public HTTPController() {
        client = HttpClient.newHttpClient();
    }

    //TODO split up, prepare real,Routes
    public void sendGet(String numberOne, String numberTwo){
        String uri = String.format("https://seb-noren-cloud.herokuapp.com/calc?operation=add&numberone=%s&numbertwo=%s", numberOne, numberTwo);

        System.out.println(uri);

        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(uri)).GET().build();

        try {
            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
            System.out.println(response.headers().map());
            System.out.println("Response from seb-noren-cloud.herokuapp Server: " + response.body());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // EXAMPEL OF JSON PARSING Method
    public String sendGetJSON(){
        String uri = "https://jsonplaceholder.typicode.com/todos/1";
        System.out.println(uri);
        Gson gson = new Gson();
        JsonTestClass jsonTestClass = null;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .timeout(Duration.ofSeconds(30))
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
            System.out.println(response.headers().map());
            jsonTestClass = gson.fromJson(response.body(),JsonTestClass.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Response cloud Server: " + jsonTestClass.toString();
    }
}
