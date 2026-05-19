package com.example.offerservice.application.usecase;

import com.example.offerservice.application.dto.OfferResponse;
import com.example.offerservice.application.dto.UpdateOfferRequest;

public interface UpdateOfferUseCase {
    OfferResponse updateOffer(Long id, UpdateOfferRequest request);
}
