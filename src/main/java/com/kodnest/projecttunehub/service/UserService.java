package com.kodnest.projecttunehub.service;

import com.kodnest.projecttunehub.entity.User;

/**
 * Service interface for managing Users.
 */
public interface UserService {

    /**
     * Adds a user.
     *
     * @param user The user to add
     * @return A string indicating the status of the operation
     */
    public String addUser(User user);

    /**
     * Checks if an email exists.
     *
     * @param email The email to check
     * @return A boolean indicating whether the email exists
     */
    public boolean emailExists(String email);

    /**
     * Validates a user.
     *
     * @param email    The email of the user
     * @param password The password of the user
     * @return A boolean indicating whether the user is valid
     */
    public boolean validateUser(String email, String password);

    /**
     * Retrieves the role of a user.
     *
     * @param email The email of the user
     * @return The role of the user
     */
    String getRole(String email);


    public User getUser(String email);

    public User updateUser(User user);


}