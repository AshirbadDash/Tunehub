package com.bloomwave.service;

import com.bloomwave.dto.RegisterRequestDTO;
import com.bloomwave.entity.User;

import java.util.*;

/**
 * Service interface for managing Users.
 */
public interface UserService {

    User addUser(RegisterRequestDTO newUserRequest);

    Optional<User> findByEmail(String email);
}