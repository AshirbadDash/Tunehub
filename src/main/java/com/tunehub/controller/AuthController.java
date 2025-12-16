package com.tunehub.controller;

import com.tunehub.common.SessionHelper;
import com.tunehub.dto.UserLoginRequestDTO;
import com.tunehub.dto.UserRegisterRequestDTO;
import com.tunehub.mapper.UserMapper;
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
    public String processRegistration(
            @Valid @ModelAttribute("newUser") UserRegisterRequestDTO dto,
            BindingResult bindingResult,
            RedirectAttributes ra,
            Model model) {

        if (bindingResult.hasErrors()) {
            return "users/register";
        }

        if (!dto.isPasswordMatch()) {
            model.addAttribute("message", "Password and confirmation password do not match.");
            return "users/register";
        }

        try {
            User user = UserMapper.toUser(dto);

            userService.createUser(user);

            ra.addFlashAttribute("message", "Registration successful. Please log in.");
            return "redirect:/login";

        } catch (IllegalStateException e) {
            model.addAttribute("message", e.getMessage());
            return "users/register";

        } catch (IllegalArgumentException e) {
            model.addAttribute("message", "Invalid registration data.");
            return "users/register";
        }
    }

    // ==================== LOGIN ====================

    @GetMapping("/login")
    public String showLoginForm(Model model, HttpSession session) {

        if (SessionHelper.isLoggedIn(session)) {
            Long userId = SessionHelper.getCurrentUserId(session);
            log.info("Authenticated user attempted to access login page. userId={}", userId);
            return "redirect:/users/dashboard";
        }

        model.addAttribute("loginRequest", new UserLoginRequestDTO());
        return "users/login";
    }

    @PostMapping("/login")
    public String processLogin(
            @Valid @ModelAttribute("loginRequest") UserLoginRequestDTO dto,
            BindingResult bindingResult,
            HttpServletRequest request,
            Model model) {

        if (bindingResult.hasErrors()) {
            return "users/login";
        }

        final String invalidMsg = "Invalid username/email or password.";

        // Normalize identifier
        String identifier = dto.getUsernameOrEmail().trim();

        Optional<User> optionalUser;
        if (identifier.contains("@")) {
            optionalUser = userService.findByEmail(identifier.toLowerCase());
        } else {
            optionalUser = userService.findByUsername(identifier);
        }

        if (optionalUser.isEmpty()) {
            model.addAttribute("message", invalidMsg);
            return "users/login";
        }

        User user = optionalUser.get();

        if (!BCrypt.checkpw(dto.getPassword(), user.getHashedPassword())) {
            model.addAttribute("message", invalidMsg);
            return "users/login";
        }

        // Protect against session fixation
        request.changeSessionId();
        HttpSession session = request.getSession();

        // Update last login
        user.updateLastLogin();
        userService.updateUser(user);

        // Store MINIMAL session data
        session.setAttribute("USER_ID", user.getId());
        session.setAttribute("ROLE", user.getRole());
        session.setAttribute("USERNAME", user.getUsername());

        log.info("Login successful. userId={}, role={}", user.getId(), user.getRole());

        return Role.ADMIN.equals(user.getRole())
                ? "redirect:/admin/dashboard"
                : "redirect:/users/dashboard";
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

