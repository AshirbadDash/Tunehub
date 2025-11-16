package com.io.tunehub.service;

import com.io.tunehub.dto.CreateUserDTO;
import com.io.tunehub.entity.User;

import java.util.*;

/**
 * Service interface for managing Users.
 */
public interface UserService {

    User addUser(CreateUserDTO createUserDTO);

    Optional<User> findByEmail(String email);
}