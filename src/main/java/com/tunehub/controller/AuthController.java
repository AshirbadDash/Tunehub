package com.tunehub.controller;

import com.tunehub.common.SessionHelper;
import com.tunehub.dto.RegisterRequestDTO;
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

        model.addAttribute("newUser", new RegisterRequestDTO());
        return "users/register";
    }

    @PostMapping("/register")
    public String processRegistration(@Valid @ModelAttribute("newUser") RegisterRequestDTO newUserRequest,
                                     BindingResult bindingResult,
                                     RedirectAttributes ra,
                                     Model model) {

        if (bindingResult.hasErrors()) {
            log.info("Registration failed due to validation errors: {}", bindingResult.getAllErrors());
            return "users/register";
        }

        Optional<User> existingUser = userService.findByEmail(newUserRequest.getEmail());

        if (existingUser.isPresent()) {
            String message = "Email already exists. Please use a different email.";
            log.info("Registration failed: Email {} already exists.", newUserRequest.getEmail());
            model.addAttribute("message", message);
            return "users/register";
        }

        userService.newUser(newUserRequest);
        log.info("User registered successfully with email: {}", newUserRequest.getEmail());

        ra.addFlashAttribute("message", "Registration successful! Please login.");
        return "redirect:/login";
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
                              HttpSession session,
                              RedirectAttributes ra) {

        // Normalize email
        String normalizedEmail = email == null ? "" : email.trim().toLowerCase();

        // Generic error message to prevent user enumeration
        final String invalidMsg = "Invalid email or password. Please try again.";

        // Find user
        Optional<User> optionalUser = userService.findByEmail(normalizedEmail);
        if (optionalUser.isEmpty()) {
            log.info("Login failed for email: {}. Email not found.", normalizedEmail);
            ra.addFlashAttribute("message", invalidMsg);
            return "redirect:/login";
        }

        User user = optionalUser.get();

        // Verify password
        if (!BCrypt.checkpw(password, user.getHashedPassword())) {
            log.info("Login failed for email: {}. Incorrect password.", normalizedEmail);
            ra.addFlashAttribute("message", invalidMsg);
            return "redirect:/login";
        }

        // Successful login - protect against session fixation
        try {
            request.changeSessionId();
        } catch (Exception e) {
            session.invalidate();
            session = request.getSession(true);
        }

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

