package com.bloomwave.serviceimpl;

import com.bloomwave.dto.RegisterRequestDTO;
import com.bloomwave.entity.AccountType;
import com.bloomwave.entity.Role;
import com.bloomwave.entity.User;
import com.bloomwave.repository.UserRepository;
import com.bloomwave.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private static String sanitizeEmail(String email) {
        if (email == null) return "";
        return email.trim().toLowerCase();
    }

    private static String sanitizeUsername(String username) {
        if (username == null) return "";
        return username.trim();
    }

    @Override
    @Transactional
    public User addUser(RegisterRequestDTO newUserRequest) {
        String email = sanitizeEmail(newUserRequest.getEmail());

        if (email.isBlank()) {
            log.info("Attempted to add user with blank email.");
            throw new IllegalArgumentException("Email is required.");
        }

        Optional<User> existingUser = userRepository.findByEmail(email);
        if (existingUser.isPresent()) {
            log.info("Attempted to add user with existing email: {}", email);
            throw new IllegalArgumentException("Email already exists: " + email);
        }

        User user = new User();
        user.setEmail(email);
        user.setUsername(sanitizeUsername(newUserRequest.getUsername()));
        user.setHashedPassword(BCrypt.hashpw(newUserRequest.getPassword(), BCrypt.gensalt(12)));
        user.setAccountType(AccountType.BASIC);
        user.setRole(Role.CUSTOMER);


        User savedUser = userRepository.save(user);
        log.info("User added successfully with email: {}", email);
        return savedUser;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        log.info("Searching for user with email: {}", email);
        return userRepository.findByEmail(sanitizeEmail(email));

    }
}
