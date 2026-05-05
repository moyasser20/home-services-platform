package com.example.userservice.presentation.controller;

import com.example.userservice.application.dto.LoginRequest;
import com.example.userservice.application.dto.AddBalanceRequest;
import com.example.userservice.application.dto.DeductBalanceRequest;
import com.example.userservice.application.dto.RegisterRequest;
import com.example.userservice.application.dto.UserResponse;
import com.example.userservice.application.dto.UserBalanceResponse;
import com.example.userservice.application.usecase.AddBalanceUseCase;
import com.example.userservice.application.usecase.DeductBalanceUseCase;
import com.example.userservice.application.usecase.GetAllUsersUseCase;
import com.example.userservice.application.usecase.GetUserBalanceUseCase;
import com.example.userservice.application.usecase.GetUserByIdUseCase;
import com.example.userservice.application.usecase.GetUsersByRoleUseCase;
import com.example.userservice.application.usecase.LoginUseCase;
import com.example.userservice.application.usecase.RegisterUserUseCase;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final RegisterUserUseCase registerUserUseCase;
    private final LoginUseCase loginUseCase;
    private final GetAllUsersUseCase getAllUsersUseCase;
    private final GetUserByIdUseCase getUserByIdUseCase;
    private final GetUserBalanceUseCase getUserBalanceUseCase;
    private final AddBalanceUseCase addBalanceUseCase;
    private final DeductBalanceUseCase deductBalanceUseCase;
    private final GetUsersByRoleUseCase getUsersByRoleUseCase;

    public UserController(
            RegisterUserUseCase registerUserUseCase,
            LoginUseCase loginUseCase,
            GetAllUsersUseCase getAllUsersUseCase,
            GetUserByIdUseCase getUserByIdUseCase,
            GetUserBalanceUseCase getUserBalanceUseCase,
            AddBalanceUseCase addBalanceUseCase,
            DeductBalanceUseCase deductBalanceUseCase,
            GetUsersByRoleUseCase getUsersByRoleUseCase
    ) {
        this.registerUserUseCase = registerUserUseCase;
        this.loginUseCase = loginUseCase;
        this.getAllUsersUseCase = getAllUsersUseCase;
        this.getUserByIdUseCase = getUserByIdUseCase;
        this.getUserBalanceUseCase = getUserBalanceUseCase;
        this.addBalanceUseCase = addBalanceUseCase;
        this.deductBalanceUseCase = deductBalanceUseCase;
        this.getUsersByRoleUseCase = getUsersByRoleUseCase;
    }

    @PostMapping("/register")
    public UserResponse register(@Valid @RequestBody RegisterRequest request) {
        return registerUserUseCase.register(request);
    }

    @PostMapping("/login")
    public UserResponse login(@Valid @RequestBody LoginRequest request) {
        return loginUseCase.login(request);
    }

    @GetMapping
    public List<UserResponse> getAllUsers() {
        return getAllUsersUseCase.getAllUsers();
    }

    @GetMapping("/{id}")
    public UserResponse getUserById(@PathVariable Long id) {
        return getUserByIdUseCase.getUserById(id);
    }

    @GetMapping("/{id}/balance")
    public UserBalanceResponse getUserBalance(@PathVariable Long id) {
        return getUserBalanceUseCase.getUserBalance(id);
    }

    @PostMapping("/{id}/add-balance")
    public UserBalanceResponse addBalance(@PathVariable Long id, @Valid @RequestBody AddBalanceRequest request) {
        return addBalanceUseCase.addBalance(id, request.getAmount());
    }

    @PostMapping("/{id}/deduct-balance")
    public UserBalanceResponse deductBalance(@PathVariable Long id, @Valid @RequestBody DeductBalanceRequest request) {
        return deductBalanceUseCase.deductBalance(id, request.getAmount());
    }

    @GetMapping("/providers")
    public List<UserResponse> getProviders() {
        return getUsersByRoleUseCase.getProviders();
    }

    @GetMapping("/customers")
    public List<UserResponse> getCustomers() {
        return getUsersByRoleUseCase.getCustomers();
    }

    @GetMapping("/health")
    public String health() {
        return "User Service is working";
    }
}