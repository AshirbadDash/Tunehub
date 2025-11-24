package com.tunehub.service;

import com.tunehub.dto.RegisterRequestDTO;
import com.tunehub.entity.User;

import java.util.*;

/**
 * Service interface for managing Users.
 */
public interface UserService {

    User addUser(RegisterRequestDTO newUserRequest);

    Optional<User> findByEmail(String email);
}