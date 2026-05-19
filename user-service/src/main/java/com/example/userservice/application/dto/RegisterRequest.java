package com.example.userservice.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public class RegisterRequest {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    private String role; // CUSTOMER / PROVIDER

    private String professionType; // only for provider
    @PositiveOrZero
    private BigDecimal balance;

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getRole() { return role; }
    public String getProfessionType() { return professionType; }
    public BigDecimal getBalance() { return balance; }

    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public void setRole(String role) { this.role = role; }
    public void setProfessionType(String professionType) { this.professionType = professionType; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }
}