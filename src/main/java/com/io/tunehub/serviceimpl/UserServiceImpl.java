package com.io.tunehub.serviceimpl;

import com.io.tunehub.dto.RegisterRequestDTO;
import com.io.tunehub.entity.AccountType;
import com.io.tunehub.entity.Role;
import com.io.tunehub.entity.User;
import com.io.tunehub.repository.UserRepository;
import com.io.tunehub.service.DefaultImageService;
import com.io.tunehub.service.MusicService;
import com.io.tunehub.service.UserService;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final DefaultImageService defaultImageService;
    private MusicService musicService;

    public UserServiceImpl(UserRepository userRepository, DefaultImageService defaultImageService) {
        this.userRepository = userRepository;
        this.defaultImageService = defaultImageService;

    }

    private static String sanitizeEmail(String email) {
        if (email == null) return "";
        return email.trim().toLowerCase();
    }

    private static String sanitizeUsername(String username) {
        if (username == null) return "";
        return username.trim();
    }

    @Autowired(required = false)
    public void setMusicService(MusicService musicService) {
        this.musicService = musicService;
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

        // Assign a canonical default image reference (no per-user duplicate BLOB)
        Long defaultImageId = defaultImageService.pickDefaultImageIdForEmail(email);
        user.setDefaultImageId(defaultImageId);
        user.setHasCustomAvatar(false);
        // Do NOT set profilePictureData here for defaults.

        User savedUser = userRepository.save(user);
        log.info("User added successfully with email: {}", email);
        return savedUser;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(sanitizeEmail(email));
    }
}
