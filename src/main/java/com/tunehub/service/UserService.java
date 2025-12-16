package com.tunehub.service;

import com.tunehub.model.entity.User;
import com.tunehub.model.enums.Role;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for managing Users.
 */
public interface UserService {

    // -------- Creation --------
    User createUser(User user);

    // -------- Lookup --------
    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    // -------- Update --------
    User updateUser(User user);

    // -------- Admin --------
    List<User> getAllUsers();

    List<User> getUsersByRole(Role role);

    List<User> getActiveUsers();

    List<User> getInactiveUsers();
}
