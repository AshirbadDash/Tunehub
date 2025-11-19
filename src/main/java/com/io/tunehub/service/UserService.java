package com.io.tunehub.service;

import com.io.tunehub.dto.RegisterRequestDTO;
import com.io.tunehub.entity.User;

import java.util.*;

/**
 * Service interface for managing Users.
 */
public interface UserService {

    User addUser(RegisterRequestDTO newUserRequest);

    Optional<User> findByEmail(String email);
}