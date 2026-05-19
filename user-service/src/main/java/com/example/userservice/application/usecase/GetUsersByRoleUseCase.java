package com.example.userservice.application.usecase;

import com.example.userservice.application.dto.UserResponse;

import java.util.List;

public interface GetUsersByRoleUseCase {
    List<UserResponse> getProviders();
    List<UserResponse> getCustomers();
}
