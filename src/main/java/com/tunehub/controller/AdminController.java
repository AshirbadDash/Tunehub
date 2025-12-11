package com.tunehub.controller;

import com.tunehub.common.SessionHelper;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * AdminController handles admin-specific operations.
 * Authentication and authorization handled by AuthenticationInterceptor.
 */
@Controller
@RequestMapping("/admin")
@Slf4j
public class AdminController {

    /**
     * Show admin dashboard
     * Authorization: ADMIN role required (handled by interceptor)
     */
    @GetMapping("/dashboard")
    public String showAdminDashboard(HttpSession session, Model model) {
        String username = SessionHelper.getCurrentUsername(session);
        log.info("Admin user {} accessed the admin panel", username);

        // Add admin info to model
        model.addAttribute("username", username);
        model.addAttribute("email", SessionHelper.getCurrentUserEmail(session));
        model.addAttribute("role", SessionHelper.getCurrentUserRole(session));

        return "users/adminController";
    }

    /**
     * Show user management page
     * Authorization: ADMIN role required (handled by interceptor)
     */
    @GetMapping("/users")
    public String manageUsers(HttpSession session, Model model) {
        log.info("Admin {} accessed user management", SessionHelper.getCurrentUsername(session));

        model.addAttribute("username", SessionHelper.getCurrentUsername(session));
        return "admin/userManagement";
    }

    /**
     * Show music management page
     * Authorization: ADMIN role required (handled by interceptor)
     */
    @GetMapping("/music")
    public String manageMusic(HttpSession session, Model model) {
        log.info("Admin {} accessed music management", SessionHelper.getCurrentUsername(session));

        model.addAttribute("username", SessionHelper.getCurrentUsername(session));
        return "admin/musicManagement";
    }
}

