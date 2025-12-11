package com.tunehub.controller;

import com.tunehub.common.SessionHelper;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * AdminController handles admin-specific operations.
 * All endpoints require ADMIN role.
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    private static final Logger log = LoggerFactory.getLogger(AdminController.class);

    /**
     * Show admin dashboard
     * Requires: User must be logged in AND have ADMIN role
     */
    @GetMapping("/dashboard")
    public String showAdminDashboard(HttpSession session, Model model, RedirectAttributes ra) {
        // Check if user is logged in
        if (!SessionHelper.isLoggedIn(session)) {
            log.info("Unauthorized access attempt to admin panel. No user logged in.");
            ra.addFlashAttribute("message", "You must be logged in to access this page.");
            return "redirect:/login";
        }

        // Check if user has admin role
        if (!SessionHelper.isAdmin(session)) {
            String username = SessionHelper.getCurrentUsername(session);
            log.info("Unauthorized access attempt to admin panel by user: {}", username);
            ra.addFlashAttribute("message", "You are not authorized to access the admin panel.");
            return "redirect:/users/dashboard";
        }

        String username = SessionHelper.getCurrentUsername(session);
        log.info("Admin user {} accessed the admin panel.", username);

        // Add admin info to model
        model.addAttribute("username", username);
        model.addAttribute("email", SessionHelper.getCurrentUserEmail(session));

        return "users/adminController";
    }

    /**
     * Show user management page
     * Requires: ADMIN role
     */
    @GetMapping("/users")
    public String manageUsers(HttpSession session, RedirectAttributes ra) {
        // Check authentication and authorization
        if (!SessionHelper.isAdmin(session)) {
            ra.addFlashAttribute("message", "You are not authorized to access this page.");
            return "redirect:/login";
        }

        log.info("Admin {} accessed user management.", SessionHelper.getCurrentUsername(session));
        return "admin/userManagement";
    }

    /**
     * Show music management page
     * Requires: ADMIN role
     */
    @GetMapping("/music")
    public String manageMusic(HttpSession session, RedirectAttributes ra) {
        // Check authentication and authorization
        if (!SessionHelper.isAdmin(session)) {
            ra.addFlashAttribute("message", "You are not authorized to access this page.");
            return "redirect:/login";
        }

        log.info("Admin {} accessed music management.", SessionHelper.getCurrentUsername(session));
        return "admin/musicManagement";
    }
}

