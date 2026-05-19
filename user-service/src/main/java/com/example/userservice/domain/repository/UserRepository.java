package com.example.userservice.domain.repository;

import com.example.userservice.domain.model.User;
import java.util.List;
import java.util.Optional;

public interface UserRepository {
    User save(User user);
    Optional<User> findByUsername(String username);
    Optional<User> findById(Long id);
    List<User> findByRole(String role);
    List<User> findAll();
}