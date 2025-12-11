package com.tunehub.controller;

import com.tunehub.common.SessionHelper;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * UserController handles user-specific operations.
 * All endpoints require authentication.
 */
@Controller
@RequestMapping("/users")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    /**
     * Show user dashboard
     * Requires: User must be logged in
     */
    @GetMapping("/dashboard")
    public String showDashboard(HttpSession session, Model model) {
        // Check authentication using helper
        if (!SessionHelper.isLoggedIn(session)) {
            log.info("Unauthorized access to dashboard. Redirecting to login.");
            return "redirect:/login";
        }

        String username = SessionHelper.getCurrentUsername(session);
        log.info("User {} accessed the dashboard.", username);

        // Add user info to model
        model.addAttribute("username", username);
        model.addAttribute("email", SessionHelper.getCurrentUserEmail(session));

        return "users/dashboard";
    }

    /**
     * Show user profile
     * Requires: User must be logged in
     */
    @GetMapping("/profile")
    public String showProfile(HttpSession session, Model model) {
        // Check authentication using helper
        if (!SessionHelper.isLoggedIn(session)) {
            log.info("Unauthorized access to profile. Redirecting to login.");
            return "redirect:/login";
        }

        String username = SessionHelper.getCurrentUsername(session);
        log.info("User {} accessed their profile.", username);

        model.addAttribute("username", username);
        model.addAttribute("email", SessionHelper.getCurrentUserEmail(session));

        return "users/profile";
    }
}

