package com.example.offerservice.application.usecase;

import com.example.offerservice.application.dto.OfferResponse;

public interface GetOfferByIdUseCase {
    OfferResponse getOfferById(Long id);
}
