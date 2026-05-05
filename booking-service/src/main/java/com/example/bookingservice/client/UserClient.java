package com.example.bookingservice.client;

import jakarta.ejb.Stateless;
import jakarta.json.Json;
import jakarta.json.JsonObject;

import java.io.StringWriter;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Stateless
public class UserClient {

    private static final String USER_SERVICE_URL = "http://localhost:8081/api/users/";
    private final HttpClient httpClient = HttpClient.newHttpClient();

    public boolean deductBalance(Long customerId, BigDecimal amount) {
        try {
            JsonObject payload = Json.createObjectBuilder()
                    .add("amount", amount)
                    .build();
            StringWriter writer = new StringWriter();
            Json.createWriter(writer).writeObject(payload);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(USER_SERVICE_URL + customerId + "/deduct-balance"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(writer.toString()))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return response.statusCode() >= 200 && response.statusCode() < 300;
        } catch (Exception ex) {
            return false;
        }
    }
}
