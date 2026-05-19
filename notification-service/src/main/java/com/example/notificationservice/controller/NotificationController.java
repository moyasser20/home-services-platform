package com.example.notificationservice.controller;

import com.example.notificationservice.entity.Notification;
import com.example.notificationservice.service.NotificationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {
    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping
    public List<Notification> getAll() {
        return notificationService.getAllNotifications();
    }

    @GetMapping("/customer/{customerId}")
    public List<Notification> getByCustomer(@PathVariable Long customerId) {
        return notificationService.getByCustomerId(customerId);
    }

    @GetMapping("/provider/{providerId}")
    public List<Notification> getByProvider(@PathVariable Long providerId) {
        return notificationService.getByProviderId(providerId);
    }
}
