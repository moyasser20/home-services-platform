package com.example.notificationservice.service;

import com.example.notificationservice.entity.Notification;
import com.example.notificationservice.repository.NotificationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public Notification saveNotification(Long customerId, Long providerId, Long bookingId, String type, String status, String message) {
        Notification notification = new Notification();
        notification.setCustomerId(customerId);
        notification.setProviderId(providerId);
        notification.setBookingId(bookingId);
        notification.setType(type);
        notification.setStatus(status);
        notification.setMessage(message);
        notification.setTimestamp(LocalDateTime.now());
        return notificationRepository.save(notification);
    }

    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    public List<Notification> getByCustomerId(Long customerId) {
        return notificationRepository.findByCustomerIdOrderByTimestampDesc(customerId);
    }

    public List<Notification> getByProviderId(Long providerId) {
        return notificationRepository.findByProviderIdOrderByTimestampDesc(providerId);
    }
}
