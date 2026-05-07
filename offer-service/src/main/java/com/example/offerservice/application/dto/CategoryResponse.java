package com.example.offerservice.application.dto;

public class CategoryResponse {
    private final Long id;
    private final String name;

    public CategoryResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
