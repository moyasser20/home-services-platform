package com.example.offerservice.application.usecase;

import com.example.offerservice.application.dto.CategoryResponse;

import java.util.List;

public interface GetAllCategoriesUseCase {
    List<CategoryResponse> getAllCategories();
}
