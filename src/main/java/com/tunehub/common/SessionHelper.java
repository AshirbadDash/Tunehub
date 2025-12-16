package com.tunehub.common;

import com.tunehub.model.enums.Role;
import jakarta.servlet.http.HttpSession;

/**
 * Utility class for session-based authentication and authorization checks.
 * Provides helper methods to verify user login status and roles.
 */
public final class SessionHelper {

    // Session attribute keys (constants)
    public static final String USER_ID = "USER_ID";
    public static final String ROLE = "ROLE";
    public static final String USERNAME = "USERNAME";

    private SessionHelper() {
        // utility class
    }

    /**
     * Check if a user is logged in
     */
    public static boolean isLoggedIn(HttpSession session) {
        return session != null && session.getAttribute(USER_ID) != null;
    }

    /**
     * Get the ID of the currently logged-in user
     */
    public static Long getCurrentUserId(HttpSession session) {
        return isLoggedIn(session)
                ? (Long) session.getAttribute(USER_ID)
                : null;
    }

    /**
     * Get the role of the currently logged-in user
     */
    public static Role getCurrentUserRole(HttpSession session) {
        return isLoggedIn(session)
                ? (Role) session.getAttribute(ROLE)
                : null;
    }

    /**
     * Check if current user has the specified role
     */
    public static boolean hasRole(HttpSession session, Role requiredRole) {
        if (!isLoggedIn(session) || requiredRole == null) {
            return false;
        }
        return requiredRole.equals(getCurrentUserRole(session));
    }

    /**
     * Check if current user is an admin
     */
    public static boolean isAdmin(HttpSession session) {
        return hasRole(session, Role.ADMIN);
    }

    /**
     * Check if current user is a customer
     */
    public static boolean isCustomer(HttpSession session) {
        return hasRole(session, Role.CUSTOMER);
    }

    /**
     * Get the username of the currently logged-in user (display only)
     */
    public static String getCurrentUsername(HttpSession session) {
        return isLoggedIn(session)
                ? (String) session.getAttribute(USERNAME)
                : null;
    }
}
