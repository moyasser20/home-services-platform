package com.example.bookingservice.client;

import com.example.bookingservice.dto.OfferDetailsResponse;
import jakarta.ejb.Stateless;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

import java.io.StringReader;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Stateless
public class OfferClient {

    private static final String OFFER_SERVICE_URL = "http://localhost:8082/api/offers/";
    private final HttpClient httpClient = HttpClient.newHttpClient();

    public OfferDetailsResponse getOfferById(Long offerId) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(OFFER_SERVICE_URL + offerId))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() < 200 || response.statusCode() >= 300) {
                throw new RuntimeException("Failed to fetch offer details");
            }

            try (JsonReader reader = Json.createReader(new StringReader(response.body()))) {
                JsonObject json = reader.readObject();
                OfferDetailsResponse offer = new OfferDetailsResponse();
                offer.setId(json.getJsonNumber("id").longValue());
                offer.setProviderId(json.getJsonNumber("providerId").longValue());
                offer.setCategory(json.getString("category", null));
                offer.setPrice(new BigDecimal(json.get("price").toString()));
                offer.setActive(json.getBoolean("active", false));
                return offer;
            }
        } catch (Exception ex) {
            throw new RuntimeException("Error calling offer-service", ex);
        }
    }
}
