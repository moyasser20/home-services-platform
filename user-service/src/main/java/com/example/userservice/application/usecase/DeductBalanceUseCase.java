package com.example.userservice.application.usecase;

import com.example.userservice.application.dto.UserBalanceResponse;

import java.math.BigDecimal;

public interface DeductBalanceUseCase {
    UserBalanceResponse deductBalance(Long userId, BigDecimal amount);
}
