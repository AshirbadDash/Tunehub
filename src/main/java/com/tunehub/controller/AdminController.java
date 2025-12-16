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
 * AdminController handles admin-specific operations.
 * Authentication and authorization are enforced by an interceptor.
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

        Long userId = SessionHelper.getCurrentUserId(session);
        String username = SessionHelper.getCurrentUsername(session);
        Role role = SessionHelper.getCurrentUserRole(session);

        log.info("Admin dashboard accessed. userId={}", userId);

        model.addAttribute("username", username); // display only
        model.addAttribute("role", role);

        return "admin/dashboard";
    }

    /**
     * Show user management page
     * Authorization: ADMIN role required (handled by interceptor)
     */
    @GetMapping("/users")
    public String manageUsers(HttpSession session, Model model) {

        Long userId = SessionHelper.getCurrentUserId(session);
        log.info("Admin user management accessed. userId={}", userId);

        model.addAttribute("username", SessionHelper.getCurrentUsername(session));
        return "admin/userManagement";
    }

    /**
     * Show music management page
     * Authorization: ADMIN role required (handled by interceptor)
     */
    @GetMapping("/music")
    public String manageMusic(HttpSession session, Model model) {

        Long userId = SessionHelper.getCurrentUserId(session);
        log.info("Admin music management accessed. userId={}", userId);

        model.addAttribute("username", SessionHelper.getCurrentUsername(session));
        return "admin/musicManagement";
    }
}
