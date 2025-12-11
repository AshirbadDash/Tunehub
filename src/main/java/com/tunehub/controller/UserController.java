package com.tunehub.controller;

import com.tunehub.common.SessionHelper;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * UserController handles user-specific operations.
 * Authentication is handled by AuthenticationInterceptor.
 */
@Controller
@RequestMapping("/users")
@Slf4j
public class UserController {

    /**
     * Show user dashboard
     * Authentication: Required (handled by interceptor)
     */
    @GetMapping("/dashboard")
    public String showDashboard(HttpSession session, Model model) {
        String username = SessionHelper.getCurrentUsername(session);
        log.info("User {} accessed the dashboard", username);

        // Add user info to model
        model.addAttribute("username", username);
        model.addAttribute("email", SessionHelper.getCurrentUserEmail(session));
        model.addAttribute("role", SessionHelper.getCurrentUserRole(session));

        return "users/dashboard";
    }

    /**
     * Show user profile
     * Authentication: Required (handled by interceptor)
     */
    @GetMapping("/profile")
    public String showProfile(HttpSession session, Model model) {
        String username = SessionHelper.getCurrentUsername(session);
        log.info("User {} accessed their profile", username);

        model.addAttribute("username", username);
        model.addAttribute("email", SessionHelper.getCurrentUserEmail(session));
        model.addAttribute("role", SessionHelper.getCurrentUserRole(session));

        return "users/profile";
    }
}

