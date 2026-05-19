package com.example.offerservice.application.usecase;

import com.example.offerservice.application.dto.CreateOfferRequest;
import com.example.offerservice.application.dto.OfferResponse;

public interface CreateOfferUseCase {
    OfferResponse createOffer(CreateOfferRequest request);
}
