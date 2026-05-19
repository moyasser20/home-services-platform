package com.example.offerservice.application.service;

import com.example.offerservice.application.dto.CreateOfferRequest;
import com.example.offerservice.application.dto.CategoryResponse;
import com.example.offerservice.application.dto.CreateCategoryRequest;
import com.example.offerservice.application.dto.OfferResponse;
import com.example.offerservice.application.dto.UpdateOfferRequest;
import com.example.offerservice.application.usecase.CreateCategoryUseCase;
import com.example.offerservice.application.usecase.CreateOfferUseCase;
import com.example.offerservice.application.usecase.GetAllCategoriesUseCase;
import com.example.offerservice.application.usecase.GetAllActiveOffersUseCase;
import com.example.offerservice.application.usecase.GetOfferByIdUseCase;
import com.example.offerservice.application.usecase.GetOffersByCategoryUseCase;
import com.example.offerservice.application.usecase.UpdateOfferUseCase;
import com.example.offerservice.exception.DuplicateCategoryException;
import com.example.offerservice.exception.InvalidCategoryException;
import com.example.offerservice.exception.OfferNotFoundException;
import com.example.offerservice.infrastructure.persistence.CategoryEntity;
import com.example.offerservice.infrastructure.persistence.CategoryJpaRepository;
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
        UpdateOfferUseCase,
        CreateCategoryUseCase,
        GetAllCategoriesUseCase {

    private final OfferJpaRepository offerJpaRepository;
    private final CategoryJpaRepository categoryJpaRepository;

    public OfferServiceImpl(OfferJpaRepository offerJpaRepository, CategoryJpaRepository categoryJpaRepository) {
        this.offerJpaRepository = offerJpaRepository;
        this.categoryJpaRepository = categoryJpaRepository;
    }

    @Override
    @Transactional
    public OfferResponse createOffer(CreateOfferRequest request) {
        String normalizedCategory = normalizeCategory(request.getCategory());
        validateCategoryExists(normalizedCategory);

        OfferEntity entity = new OfferEntity();
        entity.setProviderId(request.getProviderId());
        entity.setCategory(normalizedCategory);
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
        if (request.getCategory() != null && !request.getCategory().isBlank()) {
            String normalizedCategory = normalizeCategory(request.getCategory());
            validateCategoryExists(normalizedCategory);
            offer.setCategory(normalizedCategory);
        }
        OfferEntity saved = offerJpaRepository.save(offer);
        return toResponse(saved);
    }

    @Override
    @Transactional
    public CategoryResponse createCategory(CreateCategoryRequest request) {
        String normalizedName = normalizeCategory(request.getName());
        if (categoryJpaRepository.existsByNameIgnoreCase(normalizedName)) {
            throw new DuplicateCategoryException("Category already exists: " + normalizedName);
        }
        CategoryEntity category = new CategoryEntity();
        category.setName(normalizedName);
        CategoryEntity saved = categoryJpaRepository.save(category);
        return new CategoryResponse(saved.getId(), saved.getName());
    }

    @Override
    public List<CategoryResponse> getAllCategories() {
        return categoryJpaRepository.findAll()
                .stream()
                .map(cat -> new CategoryResponse(cat.getId(), cat.getName()))
                .toList();
    }

    private OfferEntity findByIdOrThrow(Long id) {
        return offerJpaRepository.findById(id)
                .orElseThrow(() -> new OfferNotFoundException("Offer not found with id: " + id));
    }

    private String normalizeCategory(String category) {
        return category == null ? null : category.trim().toUpperCase();
    }

    private void validateCategoryExists(String normalizedCategory) {
        if (normalizedCategory == null || normalizedCategory.isBlank()
                || !categoryJpaRepository.existsByNameIgnoreCase(normalizedCategory)) {
            throw new InvalidCategoryException("Category does not exist: " + normalizedCategory);
        }
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
