package com.tunehub.interceptor;

import com.tunehub.common.SessionHelper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * Interceptor to check if user is authenticated before accessing protected resources.
 * Redirects to login page if not authenticated.
 */
@Component
@Slf4j
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request,
                            @NonNull HttpServletResponse response,
                            @NonNull Object handler) throws Exception {

        HttpSession session = request.getSession(false);
        String requestURI = request.getRequestURI();

        // Allow access to public pages
        if (isPublicPage(requestURI)) {
            return true;
        }

        // Check if user is logged in
        if (!SessionHelper.isLoggedIn(session)) {
            log.warn("Unauthenticated access attempt to: {}", requestURI);
            response.sendRedirect(request.getContextPath() + "/login");
            return false;
        }

        // Check admin access
        if (requestURI.startsWith("/admin")) {
            if (!SessionHelper.isAdmin(session)) {
                log.warn("Unauthorized admin access attempt by user: {}",
                        SessionHelper.getCurrentUsername(session));
                response.sendRedirect(request.getContextPath() + "/users/dashboard");
                return false;
            }
        }

        return true;
    }

    /**
     * Check if the page is public (no authentication required)
     */
    private boolean isPublicPage(String uri) {
        return uri.equals("/") ||
               uri.equals("/login") ||
               uri.equals("/register") ||
               uri.equals("/logout") ||
               uri.startsWith("/css/") ||
               uri.startsWith("/js/") ||
               uri.startsWith("/images/") ||
               uri.startsWith("/static/") ||
               uri.startsWith("/avatars/") ||
               uri.startsWith("/fonts/") ||
               uri.startsWith("/favicon.ico") ||
               uri.startsWith("/error") ||
               uri.startsWith("/actuator/health");
    }
}

