package com.example.userservice.domain.model;

public class User {
    private Long id;
    private String username;
    private String password;
    private String role;
    private String professionType;
    private java.math.BigDecimal balance;

    public User() {}

    public User(Long id, String username, String password, String role, String professionType, java.math.BigDecimal balance) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.professionType = professionType;
        this.balance = balance;
    }

    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getRole() { return role; }
    public String getProfessionType() { return professionType; }
    public java.math.BigDecimal getBalance() { return balance; }

    public void setId(Long id) { this.id = id; }
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public void setRole(String role) { this.role = role; }
    public void setProfessionType(String professionType) { this.professionType = professionType; }
    public void setBalance(java.math.BigDecimal balance) { this.balance = balance; }
}