package com.example.userservice.application.usecase;

import com.example.userservice.application.dto.UserBalanceResponse;

public interface GetUserBalanceUseCase {
    UserBalanceResponse getUserBalance(Long userId);
}
