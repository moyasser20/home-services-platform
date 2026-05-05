package com.example.bookingservice.service;

import com.example.bookingservice.dto.CreateBookingRequest;
import jakarta.ejb.Stateless;

@Stateless
public class BookingValidationService {

    public void validateCreateRequest(CreateBookingRequest request) {
        if (request == null) {
            throw new RuntimeException("Request body is required");
        }
        if (request.getCustomerId() == null) {
            throw new RuntimeException("customerId is required");
        }
        if (request.getOfferId() == null) {
            throw new RuntimeException("offerId is required");
        }
    }
}
