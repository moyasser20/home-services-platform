package com.example.bookingservice.dto;

public class CreateBookingRequest {
    private Long customerId;
    private Long offerId;

    public Long getCustomerId() {
        return customerId;
    }

    public Long getOfferId() {
        return offerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public void setOfferId(Long offerId) {
        this.offerId = offerId;
    }
}
