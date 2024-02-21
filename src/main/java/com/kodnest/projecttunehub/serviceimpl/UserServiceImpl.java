package com.kodnest.projecttunehub.serviceimpl;

import com.kodnest.projecttunehub.entity.User;
import com.kodnest.projecttunehub.repository.UserRepository;
import com.kodnest.projecttunehub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * UserServiceImpl class that implements the UserService interface.
 * This class provides the methods to manage users in the application.
 */
@Service
public class UserServiceImpl implements UserService {

    // UserRepository instance for interacting with the database.
    @Autowired
    public UserRepository userRepository;

    /**
     * Adds a new user to the database.
     *
     * @param user The user to be added.
     * @return A string message indicating the operation's result.
     */
    @Override
    public String addUser(User user) {
        userRepository.save(user);
        return "User added successfully";
    }

    /**
     * Checks if a user with the given email already exists in the database.
     *
     * @param email The email to check.
     * @return true if the email exists, false otherwise.
     */
    @Override
    public boolean emailExists(String email) {
        return userRepository.findByEmail(email) != null;
    }

    /**
     * Validates a user's credentials.
     *
     * @param email    The email of the user.
     * @param password The password of the user.
     * @return true if the credentials are valid, false otherwise.
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

    @Override
    public String getRole(String email) {

        User user = userRepository.findByEmail(email);
        return user.getRole();
    }


}