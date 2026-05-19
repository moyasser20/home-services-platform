package com.example.offerservice.application.usecase;

import com.example.offerservice.application.dto.OfferResponse;

import java.util.List;

public interface GetAllActiveOffersUseCase {
    List<OfferResponse> getAllActiveOffers();
}
