package com.tunehub.service;

import com.tunehub.dto.UserRegisterRequestDTO;
import com.tunehub.model.entity.User;

import java.util.*;

/**
 * Service interface for managing Users.
 */
public interface UserService {

    User newUser(UserRegisterRequestDTO newUserRequest);

    Optional<User> findByEmail(String email);

    User updateUser(User user);

    Optional<User> findById(Long id);
}