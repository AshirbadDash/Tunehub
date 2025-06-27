package com.kodnest.projecttunehub.serviceimpl;

import com.kodnest.projecttunehub.entity.User;
import com.kodnest.projecttunehub.repository.UserRepository;
import com.kodnest.projecttunehub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service implementation for managing Users.
 * This class implements the UserService interface.
 */
@Service
public class UserServiceImpl implements UserService {

    /**
     * The UserRepository that this service will use to interact with the database.
     */
    @Autowired
    public UserRepository userRepository;

    /**
     * Adds a user to the database.
     *
     * @param user The user to add
     * @return A string indicating the status of the operation
     */
    @Override
    public String addUser(User user) {
        userRepository.save(user);
        return "User added successfully";
    }

    /**
     * Checks if an email exists in the database.
     *
     * @param email The email to check
     * @return A boolean indicating whether the email exists
     */
    @Override
    public boolean emailExists(String email) {
        return userRepository.findByEmail(email) != null;
    }

    /**
     * Validates a user's credentials.
     *
     * @param email    The email of the user
     * @param password The password of the user
     * @return A boolean indicating whether the credentials are valid
     */
    @Override
    public boolean validateUser(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return false;
        }

        String db_password = user.getPassword();
        return password.equals(db_password);
    }

    /**
     * Retrieves the role of a user.
     *
     * @param email The email of the user
     * @return The role of the user
     */
    @Override
    public String getRole(String email) {
        User user = userRepository.findByEmail(email);
        return user.getRole();
    }


    @Override
    public User getUser(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }


}