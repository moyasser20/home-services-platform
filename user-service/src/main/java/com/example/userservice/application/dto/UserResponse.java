package com.example.userservice.application.dto;

public class UserResponse {
    private Long id;
    private String username;
    private String role;
    private String professionType;
    private java.math.BigDecimal balance;

    public UserResponse(Long id, String username, String role, String professionType, java.math.BigDecimal balance) {
        this.id = id;
        this.username = username;
        this.role = role;
        this.professionType = professionType;
        this.balance = balance;
    }

    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getRole() { return role; }
    public String getProfessionType() { return professionType; }
    public java.math.BigDecimal getBalance() { return balance; }
}