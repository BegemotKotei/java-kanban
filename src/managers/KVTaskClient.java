package managers;

import exception.ManagerSaveException;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class KVTaskClient {
    private final String url;
    private final String apiToken;

    public KVTaskClient(int port) {
        url = "http://localhost:" + port;
        apiToken = register(url);
    }

    private String register(String url) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url + "/reguster"))
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != HttpURLConnection.HTTP_OK) {
                throw new ManagerSaveException("Ошибка: " + response.statusCode());
            }
            return response.body();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Ошибка";
    }

    public void put(String key, String json) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url + "/save/" + key + "?API_TOKEN=" + apiToken))
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != HttpURLConnection.HTTP_OK) {
                throw new ManagerSaveException("Ошибка: " + response.statusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public String load(String key)  {

        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url+"/load/" + key + "?API_TOKEN=" + apiToken))
                    .GET()
                    .build();
            HttpResponse<String>  response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != HttpURLConnection.HTTP_OK) {
                throw new ManagerSaveException("Ошибка: " + response.statusCode());
            }
            return response.body();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
