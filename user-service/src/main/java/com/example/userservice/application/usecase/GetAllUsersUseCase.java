package com.example.userservice.application.usecase;

import com.example.userservice.application.dto.UserResponse;
import java.util.List;

public interface GetAllUsersUseCase {
    List<UserResponse> getAllUsers();
}