package com.tunehub.service.impl;

import com.tunehub.model.entity.User;
import com.tunehub.model.enums.Role;
import com.tunehub.repository.UserRepository;
import com.tunehub.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // ---------------- CREATE ----------------

    @Override
    public User createUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalStateException("Email already exists");
        }

        if (userRepository.existsByUsername(user.getUsername())) {
            throw new IllegalStateException("Username already exists");
        }

        User savedUser = userRepository.save(user);

        log.info("User created successfully. userId={}, role={}",
                savedUser.getId(), savedUser.getRole());

        return savedUser;
    }

    // ---------------- LOOKUP ----------------

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findById(Long id) {
        if (id == null) {
            return Optional.empty();
        }
        return userRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByEmail(String email) {
        if (email == null || email.isBlank()) {
            return Optional.empty();
        }
        return userRepository.findByEmail(email.trim().toLowerCase());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByUsername(String username) {
        if (username == null || username.isBlank()) {
            return Optional.empty();
        }
        return userRepository.findByUsername(username.trim());
    }

    // ---------------- EXISTENCE ----------------

    @Override
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return email != null && userRepository.existsByEmail(email.trim().toLowerCase());
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByUsername(String username) {
        return username != null && userRepository.existsByUsername(username.trim());
    }

    // ---------------- UPDATE ----------------

    @Override
    public User updateUser(User user) {
        if (user == null || user.getId() == null) {
            throw new IllegalArgumentException("User or user ID cannot be null");
        }
        return userRepository.save(user);
    }

    // ---------------- ADMIN ----------------

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getUsersByRole(Role role) {
        if (role == null) {
            return List.of();
        }
        return userRepository.findByRole(role);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getActiveUsers() {
        return userRepository.findByActive(true);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getInactiveUsers() {
        return userRepository.findByActive(false);
    }
}
