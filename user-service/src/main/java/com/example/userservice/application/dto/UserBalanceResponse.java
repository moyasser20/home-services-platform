package com.example.userservice.application.dto;

import java.math.BigDecimal;

public class UserBalanceResponse {
    private final Long userId;
    private final BigDecimal balance;

    public UserBalanceResponse(Long userId, BigDecimal balance) {
        this.userId = userId;
        this.balance = balance;
    }

    public Long getUserId() {
        return userId;
    }

    public BigDecimal getBalance() {
        return balance;
    }
}
