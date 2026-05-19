package com.example.bookingservice.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class BookingResponse {
    private Long id;
    private Long customerId;
    private Long providerId;
    private Long offerId;
    private String category;
    private BigDecimal price;
    private String status;
    private LocalDateTime createdAt;
    private String customerName;
    private String providerName;

    public BookingResponse() {
    }

    public BookingResponse(Long id, Long customerId, Long providerId, Long offerId, String category, BigDecimal price, String status, LocalDateTime createdAt, String customerName, String providerName) {
        this.id = id;
        this.customerId = customerId;
        this.providerId = providerId;
        this.offerId = offerId;
        this.category = category;
        this.price = price;
        this.status = status;
        this.createdAt = createdAt;
        this.customerName = customerName;
        this.providerName = providerName;
    }

    public Long getId() {
        return id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public Long getProviderId() {
        return providerId;
    }

    public Long getOfferId() {
        return offerId;
    }

    public String getCategory() {
        return category;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public void setProviderId(Long providerId) {
        this.providerId = providerId;
    }

    public void setOfferId(Long offerId) {
        this.offerId = offerId;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }
}
