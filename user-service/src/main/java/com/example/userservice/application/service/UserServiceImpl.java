package com.example.userservice.application.service;

import com.example.userservice.application.dto.LoginRequest;
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
import com.example.userservice.domain.exception.InsufficientBalanceException;
import com.example.userservice.domain.exception.UserNotFoundException;
import com.example.userservice.domain.model.User;
import com.example.userservice.domain.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class UserServiceImpl implements RegisterUserUseCase, LoginUseCase, GetAllUsersUseCase, GetUserByIdUseCase, GetUserBalanceUseCase, AddBalanceUseCase, DeductBalanceUseCase, GetUsersByRoleUseCase {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserResponse register(RegisterRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        String normalizedRole = normalizeRole(request.getRole());
        validateRoleRules(normalizedRole, request.getProfessionType(), request.getBalance());

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setRole(normalizedRole);
        user.setProfessionType(request.getProfessionType());
        user.setBalance(resolveInitialBalance(normalizedRole, request.getBalance()));

        User savedUser = userRepository.save(user);

        return toResponse(savedUser);
    }

    @Override
    public UserResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid username or password"));

        if (!user.getPassword().equals(request.getPassword())) {
            throw new RuntimeException("Invalid username or password");
        }

        return toResponse(user);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public UserResponse getUserById(Long userId) {
        User user = findUserByIdOrThrow(userId);
        return toResponse(user);
    }

    @Override
    public UserBalanceResponse getUserBalance(Long userId) {
        User user = findUserByIdOrThrow(userId);
        return new UserBalanceResponse(user.getId(), user.getBalance());
    }

    @Override
    public UserBalanceResponse addBalance(Long userId, BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Amount must be greater than zero");
        }

        User user = findUserByIdOrThrow(userId);
        BigDecimal currentBalance = user.getBalance() == null ? BigDecimal.ZERO : user.getBalance();
        user.setBalance(currentBalance.add(amount));
        User updatedUser = userRepository.save(user);
        return new UserBalanceResponse(updatedUser.getId(), updatedUser.getBalance());
    }

    @Override
    @Transactional
    public UserBalanceResponse deductBalance(Long userId, BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Amount must be greater than zero");
        }

        User user = findUserByIdOrThrow(userId);
        BigDecimal currentBalance = user.getBalance() == null ? BigDecimal.ZERO : user.getBalance();
        if (currentBalance.compareTo(amount) < 0) {
            throw new InsufficientBalanceException("Insufficient balance");
        }

        user.setBalance(currentBalance.subtract(amount));
        User updatedUser = userRepository.save(user);
        return new UserBalanceResponse(updatedUser.getId(), updatedUser.getBalance());
    }

    @Override
    public List<UserResponse> getProviders() {
        return userRepository.findByRole("PROVIDER")
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public List<UserResponse> getCustomers() {
        return userRepository.findByRole("CUSTOMER")
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private String normalizeRole(String role) {
        if (role == null) {
            throw new RuntimeException("Role is required");
        }
        return role.trim().toUpperCase();
    }

    private void validateRoleRules(String role, String professionType, BigDecimal balance) {
        if (!"CUSTOMER".equals(role) && !"PROVIDER".equals(role)) {
            throw new RuntimeException("Role must be CUSTOMER or PROVIDER");
        }

        if ("PROVIDER".equals(role) && (professionType == null || professionType.isBlank())) {
            throw new RuntimeException("Profession type is required for provider");
        }

        if ("CUSTOMER".equals(role) && balance == null) {
            throw new RuntimeException("Balance is required for customer");
        }
    }

    private BigDecimal resolveInitialBalance(String role, BigDecimal requestedBalance) {
        if ("CUSTOMER".equals(role)) {
            if (requestedBalance.compareTo(BigDecimal.ZERO) < 0) {
                throw new RuntimeException("Balance cannot be negative");
            }
            return requestedBalance;
        }
        return BigDecimal.ZERO;
    }

    private User findUserByIdOrThrow(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
    }

    private UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getRole(),
                user.getProfessionType(),
                user.getBalance()
        );
    }
}