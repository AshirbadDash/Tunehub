package com.bloomwave.controller;


import com.bloomwave.dto.RegisterRequestDTO;
import com.bloomwave.entity.Role;
import com.bloomwave.entity.User;
import com.bloomwave.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

/**
 * UserController class that handles user-related requests.
 */
@Controller
@RequestMapping("/users")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    // Constructor Injection
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("newUser", new RegisterRequestDTO());
        return "users/createUserForm";
    }

    @PostMapping("/register")
    public String addUser(@Valid @ModelAttribute RegisterRequestDTO newUserRequest,
                          RedirectAttributes ra,
                          BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            log.info("Registration failed due to validation errors: {}", bindingResult.getAllErrors());
            return "users/createUserForm";
        }

        Optional<User> optionalUser = userService.findByEmail(newUserRequest.getEmail());
        String message;

        if (optionalUser.isPresent()) {
            message = "Email already exists. Please use a different email.";
            log.info("Registration failed: Email {} already exists.", newUserRequest.getEmail());
        } else {
            userService.addUser(newUserRequest);
            message = "Registration successful!";
            log.info("User registered successfully with email: {}", newUserRequest.getEmail());
        }

        ra.addFlashAttribute("message", message);
        return "redirect:/users/login";
    }

    @GetMapping("/login")
    public String loginForm(HttpSession httpSession) {

        if (httpSession.getAttribute("userEmail") != null) {
            log.info("User already logged in with email: {}. Redirecting to dashboard.", httpSession.getAttribute("email"));
            return "redirect:/";
        }

        return "users/login";
    }

    @PostMapping("/login")
    public String loginProcess(@RequestParam String email,
                               @RequestParam String password,
                               HttpServletRequest request,
                               HttpSession session,
                               RedirectAttributes ra) {

        // Normalize the email (avoid accidental case / whitespace mismatch)
        String normalizedEmail = email == null ? "" : email.trim().toLowerCase();

        // Generic error so we don't reveal whether email exists
        final String invalidMsg = "Invalid email or password. Please try again.";

        Optional<User> optionalUser = userService.findByEmail(normalizedEmail);

        if (optionalUser.isEmpty()) {
            log.info("Login failed for email: {}. Email not found.", normalizedEmail);
            ra.addFlashAttribute("message","we found your account,but the registration hasn't been completed.Create a password to log in.");
            return "redirect:/users/reset-password";
        }

        User user = optionalUser.get();

        if (!BCrypt.checkpw(password, user.getHashedPassword())) {
            log.info("Login failed for email: {}. Incorrect password.", normalizedEmail);
            ra.addFlashAttribute("message", invalidMsg);
            return "redirect:/users/login";
        }

        // Successful login: protect against session fixation
        try {
            // Servlet 3.1+: changeSessionId is preferred to keep session attributes but change id
            request.changeSessionId();
        } catch (Exception e) {
            // Fallback: invalidate old session and create a new one
            session.invalidate();
            session = request.getSession(true);
        }


        // Store consistent and serializable objects in session
        session.setAttribute("Email", user.getEmail());
        session.setAttribute("username", user.getUsername());
        session.setAttribute("role", user.getRole()); // store Role enum directly

        if (Role.CUSTOMER.equals(user.getRole())) {
            log.info("Login successful for user: {}. Redirecting to dashboard.", user.getUsername());
            return "redirect:/users/dashboard";
        } else {
            log.info("Login successful for admin user: {}. Redirecting to admin panel.", user.getUsername());
            return "redirect:/users/admin";
        }
    }

    @GetMapping("/dashboard")
    public String showDashboard(Model model, HttpSession session) {
        if (session.getAttribute("userEmail") == null) {
            log.info("Unauthorized access to dashboard. Redirecting to login.");
            return "redirect:/users/login";
        }
        log.info("User {} accessed the dashboard.", session.getAttribute("username"));
        return "music/musicList";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes ra) {
        session.invalidate();
        ra.addFlashAttribute("message", "You have been logged out successfully.");
        return "redirect:/users/login";
    }

    @GetMapping("/admin")
    public String adminAuthentication(HttpSession session, RedirectAttributes ra) {
        Role role = (Role) session.getAttribute("role");
        String username = (String) session.getAttribute("username");

        if (!Role.ADMIN.equals(role)) {
            log.info("Unauthorized access attempt by user {}.", username);
            ra.addFlashAttribute("message", "You are not authorized to access the admin panel.");
            return "redirect:/users/login";
        }

        log.info("Admin user {} accessed the admin panel.", username);
        ra.addFlashAttribute("message", "Welcome to the admin panel!");
        return "users/adminController";
    }


}
