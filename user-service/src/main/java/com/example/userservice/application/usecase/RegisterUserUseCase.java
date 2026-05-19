package com.example.userservice.application.usecase;

import com.example.userservice.application.dto.RegisterRequest;
import com.example.userservice.application.dto.UserResponse;

public interface RegisterUserUseCase {
    UserResponse register(RegisterRequest request);
}