package com.example.offerservice.application.usecase;

import com.example.offerservice.application.dto.CategoryResponse;
import com.example.offerservice.application.dto.CreateCategoryRequest;

public interface CreateCategoryUseCase {
    CategoryResponse createCategory(CreateCategoryRequest request);
}
