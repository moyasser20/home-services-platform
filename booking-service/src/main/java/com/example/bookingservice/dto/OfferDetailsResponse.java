package com.example.bookingservice.dto;

import java.math.BigDecimal;

public class OfferDetailsResponse {
    private Long id;
    private Long providerId;
    private String category;
    private BigDecimal price;
    private boolean active;

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

    public boolean isActive() {
        return active;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setProviderId(Long providerId) {
        this.providerId = providerId;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
