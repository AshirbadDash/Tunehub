package com.kodnest.projecttunehub.controller;

import com.kodnest.projecttunehub.entity.User;
import com.kodnest.projecttunehub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * UserController class that handles user-related requests.
 */
@Controller
public class UserController {

    // UserService instance for managing users.
    @Autowired
    private UserService userService;

    /**
     * Handles the POST request for registering a new user.
     *
     * @param user The user to be registered.
     * @return The name of the home view if registration is successful, otherwise stays on the same page.
     */
    @PostMapping("/register")
    public String addUser(@ModelAttribute User user) {
        String email = user.getEmail();
        boolean status = userService.emailExists(email);
        if (!status) {
            userService.addUser(user);
            return "Home";
        } else {
            System.out.println("User already exists with this email id. Please try with another email id.");
            return "Registration";
        }
    }

    /**
     * Handles the POST request for validating a user's credentials.
     *
     * @param email The email of the user.
     * @param password The password of the user.
     * @return The name of the home view if validation is successful, otherwise redirects to the login page.
     */
    @PostMapping("/validate")
    public String validate(@RequestParam("email") String email, @RequestParam("password") String password) {
        if (userService.validateUser(email, password)) {
            return "Home";
        } else {
            return "Login";
        }
    }
}