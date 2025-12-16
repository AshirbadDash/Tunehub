package com.tunehub.controller;

import com.tunehub.common.SessionHelper;
import com.tunehub.dto.UserRegisterRequestDTO;
import com.tunehub.model.entity.User;
import com.tunehub.model.enums.Role;
import com.tunehub.service.UserService;
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

import java.util.Optional;

/**
 * AuthController handles authentication and registration operations.
 * Endpoints: /login, /register, /logout
 */
@Controller
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    // ==================== REGISTRATION ====================

    @GetMapping("/register")
    public String showRegistrationForm(Model model, HttpSession session) {
        // Redirect if already logged in
        if (SessionHelper.isLoggedIn(session)) {
            log.info("User already logged in. Redirecting to dashboard.");
            return "redirect:/users/dashboard";
        }

        model.addAttribute("newUser", new UserRegisterRequestDTO());
        return "users/register";
    }

    @PostMapping("/register")
    public String processRegistration(@Valid @ModelAttribute("newUser") UserRegisterRequestDTO newUserRequest,
                                     BindingResult bindingResult,
                                     RedirectAttributes ra,
                                     Model model) {

        // Check for validation errors
        if (bindingResult.hasErrors()) {
            log.info("Registration failed due to validation errors: {}", bindingResult.getAllErrors());
            return "users/register";
        }

        // Check password confirmation
        if (!newUserRequest.isPasswordMatch()) {
            log.info("Registration failed: Password confirmation does not match");
            model.addAttribute("message", "Password and confirmation password do not match.");
            return "users/register";
        }

        // Check if email already exists
        Optional<User> existingUser = userService.findByEmail(newUserRequest.getEmail());
        if (existingUser.isPresent()) {
            log.info("Registration failed: Email {} already exists", newUserRequest.getEmail());
            model.addAttribute("message", "Email already exists. Please use a different email.");
            return "users/register";
        }

        try {
            userService.newUser(newUserRequest);
            log.info("User registered successfully with email: {}", newUserRequest.getEmail());
            ra.addFlashAttribute("message", "Registration successful! Please login.");
            return "redirect:/login";
        } catch (Exception e) {
            log.error("Registration failed for email {}: {}", newUserRequest.getEmail(), e.getMessage());
            model.addAttribute("message", "Registration failed. Please try again.");
            return "users/register";
        }
    }

    // ==================== LOGIN ====================

    @GetMapping("/login")
    public String showLoginForm(HttpSession session) {
        // Redirect if already logged in
        if (SessionHelper.isLoggedIn(session)) {
            log.info("User already logged in with email: {}. Redirecting to dashboard.",
                    SessionHelper.getCurrentUserEmail(session));
            return "redirect:/users/dashboard";
        }

        return "users/login";
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam String email,
                              @RequestParam String password,
                              HttpServletRequest request,
                              Model model) {

        // Normalize email
        String normalizedEmail = email == null ? "" : email.trim().toLowerCase();

        // Generic error message to prevent user enumeration
        final String invalidMsg = "Invalid email or password. Please try again.";

        // Find user
        Optional<User> optionalUser = userService.findByEmail(normalizedEmail);
        if (optionalUser.isEmpty()) {
            log.info("Login failed for email: {}. Email not found.", normalizedEmail);
            model.addAttribute("message", invalidMsg);
            return "users/login";
        }

        User user = optionalUser.get();

        // Verify password
        if (!BCrypt.checkpw(password, user.getHashedPassword())) {
            log.info("Login failed for email: {}. Incorrect password.", normalizedEmail);
            model.addAttribute("message", invalidMsg);
            return "users/login";
        }

        // Successful login - protect against session fixation
        HttpSession session;
        try {
            request.changeSessionId();
            session = request.getSession(false);
        } catch (Exception e) {
            request.getSession().invalidate();
            session = request.getSession(true);
        }

        // Update last login timestamp
        user.updateLastLogin();
        userService.updateUser(user);

        // Store user info in session
        session.setAttribute("email", user.getEmail());
        session.setAttribute("username", user.getUsername());
        session.setAttribute("role", user.getRole());

        log.info("Login successful for user: {} with role: {}", user.getUsername(), user.getRole());

        // Redirect based on role
        if (Role.ADMIN.equals(user.getRole())) {
            return "redirect:/admin/dashboard";
        } else {
            return "redirect:/users/dashboard";
        }
    }

    // ==================== LOGOUT ====================

    @PostMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes ra) {
        String username = SessionHelper.getCurrentUsername(session);
        session.invalidate();

        log.info("User {} logged out successfully.", username);
        ra.addFlashAttribute("message", "You have been logged out successfully.");
        return "redirect:/login";
    }
}

