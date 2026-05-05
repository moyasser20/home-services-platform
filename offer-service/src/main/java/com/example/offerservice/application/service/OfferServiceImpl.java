package com.example.offerservice.application.service;

import com.example.offerservice.application.dto.CreateOfferRequest;
import com.example.offerservice.application.dto.OfferResponse;
import com.example.offerservice.application.dto.UpdateOfferRequest;
import com.example.offerservice.application.usecase.CreateOfferUseCase;
import com.example.offerservice.application.usecase.GetAllActiveOffersUseCase;
import com.example.offerservice.application.usecase.GetOfferByIdUseCase;
import com.example.offerservice.application.usecase.GetOffersByCategoryUseCase;
import com.example.offerservice.application.usecase.UpdateOfferUseCase;
import com.example.offerservice.exception.OfferNotFoundException;
import com.example.offerservice.infrastructure.persistence.OfferEntity;
import com.example.offerservice.infrastructure.persistence.OfferJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OfferServiceImpl implements
        CreateOfferUseCase,
        GetAllActiveOffersUseCase,
        GetOffersByCategoryUseCase,
        GetOfferByIdUseCase,
        UpdateOfferUseCase {

    private final OfferJpaRepository offerJpaRepository;

    public OfferServiceImpl(OfferJpaRepository offerJpaRepository) {
        this.offerJpaRepository = offerJpaRepository;
    }

    @Override
    @Transactional
    public OfferResponse createOffer(CreateOfferRequest request) {
        OfferEntity entity = new OfferEntity();
        entity.setProviderId(request.getProviderId());
        entity.setCategory(request.getCategory().trim());
        entity.setPrice(request.getPrice());
        entity.setAvailableDate(request.getAvailableDate());
        entity.setActive(true);
        OfferEntity saved = offerJpaRepository.save(entity);
        return toResponse(saved);
    }

    @Override
    public List<OfferResponse> getAllActiveOffers() {
        return offerJpaRepository.findByActiveTrue()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public List<OfferResponse> getOffersByCategory(String category) {
        return offerJpaRepository.findByCategoryIgnoreCaseAndActiveTrue(category)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public OfferResponse getOfferById(Long id) {
        OfferEntity offer = findByIdOrThrow(id);
        return toResponse(offer);
    }

    @Override
    @Transactional
    public OfferResponse updateOffer(Long id, UpdateOfferRequest request) {
        OfferEntity offer = findByIdOrThrow(id);
        offer.setPrice(request.getPrice());
        offer.setAvailableDate(request.getAvailableDate());
        offer.setActive(request.getActive());
        OfferEntity saved = offerJpaRepository.save(offer);
        return toResponse(saved);
    }

    private OfferEntity findByIdOrThrow(Long id) {
        return offerJpaRepository.findById(id)
                .orElseThrow(() -> new OfferNotFoundException("Offer not found with id: " + id));
    }

    private OfferResponse toResponse(OfferEntity entity) {
        return new OfferResponse(
                entity.getId(),
                entity.getProviderId(),
                entity.getCategory(),
                entity.getPrice(),
                entity.getAvailableDate(),
                entity.isActive()
        );
    }
}
