package com.tunehub.controller;

import com.tunehub.common.SessionHelper;
import com.tunehub.model.enums.Role;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * UserController handles user-specific operations.
 * Authentication is enforced by an interceptor.
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

        Long userId = SessionHelper.getCurrentUserId(session);
        String username = SessionHelper.getCurrentUsername(session);
        Role role = SessionHelper.getCurrentUserRole(session);

        log.info("User dashboard accessed. userId={}", userId);

        model.addAttribute("username", username); // display only
        model.addAttribute("role", role);

        return "users/dashboard";
    }

    /**
     * Show user profile
     * Authentication: Required (handled by interceptor)
     */
    @GetMapping("/profile")
    public String showProfile(HttpSession session, Model model) {

        Long userId = SessionHelper.getCurrentUserId(session);
        String username = SessionHelper.getCurrentUsername(session);
        Role role = SessionHelper.getCurrentUserRole(session);

        log.info("User profile accessed. userId={}", userId);

        model.addAttribute("username", username);
        model.addAttribute("role", role);

        return "users/profile";
    }
}
