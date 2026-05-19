package com.example.notificationservice.service;

import com.example.notificationservice.config.RabbitMqConfig;
import com.example.notificationservice.dto.BookingConfirmedEvent;
import com.example.notificationservice.dto.BookingFailedEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationEventListener {
    private final NotificationService notificationService;
    private final ObjectMapper objectMapper;

    public NotificationEventListener(NotificationService notificationService, ObjectMapper objectMapper) {
        this.notificationService = notificationService;
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = RabbitMqConfig.CONFIRMED_QUEUE)
    public void onBookingConfirmed(String payload) {
        try {
            BookingConfirmedEvent event = objectMapper.readValue(payload, BookingConfirmedEvent.class);
            notificationService.saveNotification(
                    event.getCustomerId(),
                    event.getProviderId(),
                    event.getBookingId(),
                    "BOOKING_CONFIRMED",
                    event.getStatus(),
                    event.getMessage()
            );
        } catch (Exception ignored) {
        }
    }

    @RabbitListener(queues = RabbitMqConfig.FAILED_QUEUE)
    public void onBookingFailed(String payload) {
        try {
            BookingFailedEvent event = objectMapper.readValue(payload, BookingFailedEvent.class);
            notificationService.saveNotification(
                    safeLong(event.getCustomerId()),
                    safeLong(event.getProviderId()),
                    safeLong(event.getBookingId()),
                    "BOOKING_FAILED",
                    event.getStatus(),
                    event.getMessage()
            );
        } catch (Exception ignored) {
        }
    }

    private Long safeLong(Long value) {
        return value != null && value >= 0 ? value : null;
    }
}
