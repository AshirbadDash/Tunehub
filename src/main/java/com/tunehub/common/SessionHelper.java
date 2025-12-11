package com.tunehub.common;

import com.tunehub.model.enums.Role;
import jakarta.servlet.http.HttpSession;

/**
 * Utility class for session-based authentication and authorization checks.
 * Provides helper methods to verify user login status and roles.
 */
public class SessionHelper {

    /**
     * Check if a user is logged in
     * @param session Current HTTP session
     * @return true if user is logged in, false otherwise
     */
    public static boolean isLoggedIn(HttpSession session) {
        return session != null && session.getAttribute("email") != null;
    }

    /**
     * Check if current user has the specified role
     * @param session Current HTTP session
     * @param requiredRole The role to check for
     * @return true if user has the role, false otherwise
     */
    public static boolean hasRole(HttpSession session, Role requiredRole) {
        if (!isLoggedIn(session)) {
            return false;
        }

        Role userRole = (Role) session.getAttribute("role");
        return requiredRole != null && requiredRole.equals(userRole);
    }

    /**
     * Check if current user is an admin
     * @param session Current HTTP session
     * @return true if user is admin, false otherwise
     */
    public static boolean isAdmin(HttpSession session) {
        return hasRole(session, Role.ADMIN);
    }

    /**
     * Check if current user is a customer
     * @param session Current HTTP session
     * @return true if user is customer, false otherwise
     */
    public static boolean isCustomer(HttpSession session) {
        return hasRole(session, Role.CUSTOMER);
    }

    /**
     * Get the email of the currently logged in user
     * @param session Current HTTP session
     * @return User's email or null if not logged in
     */
    public static String getCurrentUserEmail(HttpSession session) {
        return isLoggedIn(session) ? (String) session.getAttribute("email") : null;
    }

    /**
     * Get the username of the currently logged in user
     * @param session Current HTTP session
     * @return User's username or null if not logged in
     */
    public static String getCurrentUsername(HttpSession session) {
        return isLoggedIn(session) ? (String) session.getAttribute("username") : null;
    }

    /**
     * Get the role of the currently logged in user
     * @param session Current HTTP session
     * @return User's role or null if not logged in
     */
    public static Role getCurrentUserRole(HttpSession session) {
        return isLoggedIn(session) ? (Role) session.getAttribute("role") : null;
    }
}

