package com.kodnest.projecttunehub.serviceimpl;

import com.kodnest.projecttunehub.entity.Song;
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
        if (userRepository.findByEmail(email) != null) {
            return true;
        } else {
            return false;
        }

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
        if (userRepository.findByEmail(email) == null) {
            return false;
        }
        User user = userRepository.findByEmail(email);

        String db_password = user.getPassword();


        if (password.equals(db_password)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String getRole(String email) {

        User user = userRepository.findByEmail(email);
        return user.getRole();
    }





}