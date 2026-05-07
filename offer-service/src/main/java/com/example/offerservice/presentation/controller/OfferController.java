package com.example.offerservice.presentation.controller;

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
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/offers")
public class OfferController {

    private final CreateOfferUseCase createOfferUseCase;
    private final GetAllActiveOffersUseCase getAllActiveOffersUseCase;
    private final GetOffersByCategoryUseCase getOffersByCategoryUseCase;
    private final GetOfferByIdUseCase getOfferByIdUseCase;
    private final UpdateOfferUseCase updateOfferUseCase;
    private final CreateCategoryUseCase createCategoryUseCase;
    private final GetAllCategoriesUseCase getAllCategoriesUseCase;

    public OfferController(
            CreateOfferUseCase createOfferUseCase,
            GetAllActiveOffersUseCase getAllActiveOffersUseCase,
            GetOffersByCategoryUseCase getOffersByCategoryUseCase,
            GetOfferByIdUseCase getOfferByIdUseCase,
            UpdateOfferUseCase updateOfferUseCase,
            CreateCategoryUseCase createCategoryUseCase,
            GetAllCategoriesUseCase getAllCategoriesUseCase
    ) {
        this.createOfferUseCase = createOfferUseCase;
        this.getAllActiveOffersUseCase = getAllActiveOffersUseCase;
        this.getOffersByCategoryUseCase = getOffersByCategoryUseCase;
        this.getOfferByIdUseCase = getOfferByIdUseCase;
        this.updateOfferUseCase = updateOfferUseCase;
        this.createCategoryUseCase = createCategoryUseCase;
        this.getAllCategoriesUseCase = getAllCategoriesUseCase;
    }

    @PostMapping
    public OfferResponse createOffer(@Valid @RequestBody CreateOfferRequest request) {
        return createOfferUseCase.createOffer(request);
    }

    @GetMapping
    public List<OfferResponse> getAllActiveOffers() {
        return getAllActiveOffersUseCase.getAllActiveOffers();
    }

    @GetMapping("/category/{category}")
    public List<OfferResponse> getOffersByCategory(@PathVariable String category) {
        return getOffersByCategoryUseCase.getOffersByCategory(category);
    }

    @GetMapping("/{id}")
    public OfferResponse getOfferById(@PathVariable Long id) {
        return getOfferByIdUseCase.getOfferById(id);
    }

    @PutMapping("/{id}")
    public OfferResponse updateOffer(@PathVariable Long id, @Valid @RequestBody UpdateOfferRequest request) {
        return updateOfferUseCase.updateOffer(id, request);
    }

    // Admin-only endpoint (basic auth in security config)
    @PostMapping("/categories")
    public CategoryResponse createCategory(@Valid @RequestBody CreateCategoryRequest request) {
        return createCategoryUseCase.createCategory(request);
    }

    // Admin-only endpoint (basic auth in security config)
    @GetMapping("/categories")
    public List<CategoryResponse> getAllCategories() {
        return getAllCategoriesUseCase.getAllCategories();
    }

    @GetMapping("/health")
    public String health() {
        return "Offer Service is working";
    }
}
