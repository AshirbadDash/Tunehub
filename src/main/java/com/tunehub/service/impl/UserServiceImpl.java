package com.tunehub.service.impl;

import com.tunehub.dto.UserRegisterRequestDTO;
import com.tunehub.model.entity.User;
import com.tunehub.model.enums.AccountType;
import com.tunehub.model.enums.Role;
import com.tunehub.repository.UserRepository;
import com.tunehub.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;
import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private static String sanitizeEmail(String email) {
        if (email == null) return "";
        return email.trim().toLowerCase(Locale.ROOT);
    }


    private static String sanitizeUsername(String username) {
        if (username == null) return "";

        String trimmed = username.trim();
        return trimmed.isEmpty() ? "" : trimmed;
    }

    @Override
    @Transactional
    public User newUser(UserRegisterRequestDTO newUserRequest) {
        String email = sanitizeEmail(newUserRequest.getEmail());
        String username = sanitizeUsername(newUserRequest.getUsername());

        // Validation
        if (email.isBlank()) {
            log.warn("Attempted to register user with blank email");
            throw new IllegalArgumentException("Email is required");
        }

        if (username.isBlank()) {
            log.warn("Attempted to register user with blank username");
            throw new IllegalArgumentException("Username is required");
        }

        // Check for existing user
        Optional<User> existingUser = userRepository.findByEmail(email);
        if (existingUser.isPresent()) {
            log.warn("Attempted to register with existing email: {}", email);
            throw new IllegalArgumentException("Email already exists: " + email);
        }

        // Create new user
        User user = new User();
        user.setEmail(email);
        user.setUsername(username);
        user.setHashedPassword(BCrypt.hashpw(newUserRequest.getPassword(), BCrypt.gensalt(12)));
        user.setPhoneNumber(newUserRequest.getPhoneNumber());
        user.setAccountType(AccountType.BASIC);
        user.setRole(Role.CUSTOMER);
        user.setActive(true);
        user.setEmailVerified(false);

        User savedUser = userRepository.save(user);
        log.info("User registered successfully: {} ({})", username, email);
        return savedUser;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        log.info("Searching for user with email: {}", email);
        return userRepository.findByEmail(sanitizeEmail(email));
    }

    @Override
    @Transactional
    public User updateUser(User user) {
        if (user == null || user.getId() == null) {
            throw new IllegalArgumentException("User or User ID cannot be null");
        }

        log.info("Updating user: {}", user.getId());
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }

        log.info("Finding user by ID: {}", id);
        return userRepository.findById(id);
    }
}
