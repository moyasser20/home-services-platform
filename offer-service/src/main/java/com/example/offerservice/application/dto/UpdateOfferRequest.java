package com.example.offerservice.application.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

public class UpdateOfferRequest {
    @NotNull
    @Positive
    private BigDecimal price;

    @NotNull
    private LocalDate availableDate;

    @NotNull
    private Boolean active;

    public BigDecimal getPrice() {
        return price;
    }

    public LocalDate getAvailableDate() {
        return availableDate;
    }

    public Boolean getActive() {
        return active;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setAvailableDate(LocalDate availableDate) {
        this.availableDate = availableDate;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
