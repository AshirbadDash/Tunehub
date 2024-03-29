package com.kodnest.projecttunehub.controller;

import com.kodnest.projecttunehub.entity.Song;
import com.kodnest.projecttunehub.entity.User;
import com.kodnest.projecttunehub.service.SongService;
import com.kodnest.projecttunehub.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * UserController class that handles user-related requests.
 */
@Controller
public class UserController {

    /**
     * Handles the POST request for validating a user's credentials.
     *
     * @param email    The email of the user.
     * @param password The password of the user.
     * @return The name of the home view if validation is successful, otherwise redirects to the login page.
     */
// Create a Map to store the count of login attempts for each user
    private final Map<String, Integer> loginAttempts = new HashMap<>();
    // UserService instance for managing users.
    @Autowired
    private UserService userService;

    @Autowired
    private SongService songService;

    /**
     * Handles the POST request for registering a new user.
     *
     * @param user The user to be registered.
     * @return The name of the home view if registration is successful, otherwise stays on the same page.
     */

    @PostMapping("/register")
    public String addUser(@ModelAttribute User user) {
        String email = user.getEmail();
        boolean isPremium = user.isPremium();
        boolean status = userService.emailExists(email);
        if (!status) {
            userService.addUser(user);
            String role = userService.getRole(email);
            if (role.equals("admin")) {
                return "Admin";
            } else {
//
                return "Customer";
            }

        } else {
            System.out.println("User already exists with this email id. Please try with another email id.");
            return "Login";
        }
    }


    @PostMapping("/validate")
    public String validate(@RequestParam("email") String email, @RequestParam("password") String password, HttpSession session, Model model) {
        if (userService.validateUser(email, password)) {
            String role = userService.getRole(email);

            session.setAttribute("email", email);
            // Reset the count of login attempts after successful login
            loginAttempts.put(email, 0);
            return role.equals("admin") ? "Admin" : "Customer";
        } else {
            // Increase the count of login attempts
            loginAttempts.put(email, loginAttempts.getOrDefault(email, 0) + 1);
            // If the count of login attempts exceeds 3, redirect to the registration page
            if (loginAttempts.get(email) > 3) {
                return "Registration";
            } else {
                User user = userService.getUser(email);
                if (user != null) {
                    boolean userStatus = user.isPremium();
                    List<Song> fetchAllSongs = songService.viewSongs();
                    model.addAttribute("song", fetchAllSongs);

                    model.addAttribute("isPremium", userStatus);
                }

                return "Login";
            }
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "Login";
    }

}