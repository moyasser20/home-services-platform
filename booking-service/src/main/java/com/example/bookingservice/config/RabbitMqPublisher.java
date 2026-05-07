package com.example.bookingservice.config;

import com.example.bookingservice.dto.BookingConfirmedEvent;
import com.example.bookingservice.dto.BookingFailedEvent;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import jakarta.ejb.Singleton;
import jakarta.json.Json;
import jakarta.json.JsonObject;

import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

@Singleton
public class RabbitMqPublisher {
    private static final Logger LOGGER = Logger.getLogger(RabbitMqPublisher.class.getName());
    private static final String HOST = "localhost";
    private static final int PORT = 5672;
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "admin123";

    public static final String EXCHANGE = "booking.events.exchange";
    public static final String CONFIRMED_QUEUE = "booking.confirmed.queue";
    public static final String FAILED_QUEUE = "booking.failed.queue";
    public static final String CONFIRMED_ROUTING_KEY = "booking.confirmed";
    public static final String FAILED_ROUTING_KEY = "booking.failed";

    public void publishConfirmed(BookingConfirmedEvent event) {
        LOGGER.info("RabbitMQ publisher started for confirmed event");
        JsonObject payload = Json.createObjectBuilder()
                .add("bookingId", event.getBookingId())
                .add("customerId", event.getCustomerId())
                .add("providerId", event.getProviderId())
                .add("status", event.getStatus())
                .add("message", event.getMessage())
                .build();
        LOGGER.info("Confirmed event payload: " + payload);
        publish(CONFIRMED_ROUTING_KEY, payload.toString());
    }

    public void publishFailed(BookingFailedEvent event) {
        LOGGER.info("RabbitMQ publisher started for failed event");
        JsonObject payload = Json.createObjectBuilder()
                .add("bookingId", event.getBookingId() == null ? -1 : event.getBookingId())
                .add("customerId", event.getCustomerId() == null ? -1 : event.getCustomerId())
                .add("providerId", event.getProviderId() == null ? -1 : event.getProviderId())
                .add("status", event.getStatus())
                .add("message", event.getMessage())
                .build();
        LOGGER.info("Failed event payload: " + payload);
        publish(FAILED_ROUTING_KEY, payload.toString());
    }

    private void publish(String routingKey, String message) {
        LOGGER.info("Preparing RabbitMQ connection to " + HOST + ":" + PORT);
        LOGGER.info("Exchange: " + EXCHANGE + ", Routing key: " + routingKey);
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(HOST);
        factory.setPort(PORT);
        factory.setUsername(USERNAME);
        factory.setPassword(PASSWORD);

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            LOGGER.info("RabbitMQ connection created successfully");
            LOGGER.info("RabbitMQ channel created successfully");
            channel.exchangeDeclare(EXCHANGE, "direct", true);
            channel.queueDeclare(CONFIRMED_QUEUE, true, false, false, null);
            channel.queueDeclare(FAILED_QUEUE, true, false, false, null);
            channel.queueBind(CONFIRMED_QUEUE, EXCHANGE, CONFIRMED_ROUTING_KEY);
            channel.queueBind(FAILED_QUEUE, EXCHANGE, FAILED_ROUTING_KEY);
            LOGGER.info("Publishing message to exchange before basicPublish");
            channel.basicPublish(EXCHANGE, routingKey, null, message.getBytes(StandardCharsets.UTF_8));
            LOGGER.info("Message published successfully");
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "RabbitMQ publish failed", ex);
            throw new RuntimeException("RabbitMQ publish failed", ex);
        }
    }
}
