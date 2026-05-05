package com.example.offerservice.application.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class OfferResponse {
    private final Long id;
    private final Long providerId;
    private final String category;
    private final BigDecimal price;
    private final LocalDate availableDate;
    private final boolean active;

    public OfferResponse(Long id, Long providerId, String category, BigDecimal price, LocalDate availableDate, boolean active) {
        this.id = id;
        this.providerId = providerId;
        this.category = category;
        this.price = price;
        this.availableDate = availableDate;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public Long getProviderId() {
        return providerId;
    }

    public String getCategory() {
        return category;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public LocalDate getAvailableDate() {
        return availableDate;
    }

    public boolean isActive() {
        return active;
    }
}
