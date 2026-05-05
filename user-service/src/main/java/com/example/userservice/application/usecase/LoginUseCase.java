package com.example.userservice.application.usecase;

import com.example.userservice.application.dto.LoginRequest;
import com.example.userservice.application.dto.UserResponse;

public interface LoginUseCase {
    UserResponse login(LoginRequest request);
}