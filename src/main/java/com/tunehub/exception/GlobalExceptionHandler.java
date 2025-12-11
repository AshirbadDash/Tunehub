package com.tunehub.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * Global exception handler for the application.
 * Handles all uncaught exceptions and provides user-friendly error pages.
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * Handle validation errors
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleValidationException(MethodArgumentNotValidException ex, Model model) {
        log.error("Validation error occurred: {}", ex.getMessage());

        StringBuilder errorMessage = new StringBuilder("Validation failed: ");
        ex.getBindingResult().getFieldErrors().forEach(error ->
            errorMessage.append(error.getField())
                       .append(" - ")
                       .append(error.getDefaultMessage())
                       .append("; ")
        );

        model.addAttribute("error", "Validation Error");
        model.addAttribute("message", errorMessage.toString());
        model.addAttribute("status", HttpStatus.BAD_REQUEST.value());
        return "error";
    }

    /**
     * Handle illegal argument exceptions (business logic errors)
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleIllegalArgumentException(IllegalArgumentException ex, Model model) {
        log.error("Illegal argument: {}", ex.getMessage());
        model.addAttribute("error", "Invalid Request");
        model.addAttribute("message", ex.getMessage());
        model.addAttribute("status", HttpStatus.BAD_REQUEST.value());
        return "error";
    }

    /**
     * Handle database constraint violations
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleDataIntegrityViolation(DataIntegrityViolationException ex, Model model) {
        log.error("Database constraint violation: {}", ex.getMessage());
        model.addAttribute("error", "Data Conflict");
        model.addAttribute("message", "The operation could not be completed due to a data conflict. This may be a duplicate entry.");
        model.addAttribute("status", HttpStatus.CONFLICT.value());
        return "error";
    }

    /**
     * Handle resource not found (404)
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFoundException(NoHandlerFoundException ex, Model model, HttpServletRequest request) {
        String requestURI = request.getRequestURI();

        // Don't log static resource 404s as errors (CSS, JS, images, etc.)
        if (isStaticResource(requestURI)) {
            log.debug("Static resource not found: {}", requestURI);
        } else {
            log.warn("Page not found: {}", requestURI);
        }

        model.addAttribute("error", "Page Not Found");
        model.addAttribute("message", "The page you are looking for does not exist.");
        model.addAttribute("status", HttpStatus.NOT_FOUND.value());
        return "error";
    }

    /**
     * Check if the request is for a static resource
     */
    private boolean isStaticResource(String uri) {
        return uri != null && (
            uri.endsWith(".css") ||
            uri.endsWith(".js") ||
            uri.endsWith(".png") ||
            uri.endsWith(".jpg") ||
            uri.endsWith(".jpeg") ||
            uri.endsWith(".gif") ||
            uri.endsWith(".svg") ||
            uri.endsWith(".ico") ||
            uri.endsWith(".woff") ||
            uri.endsWith(".woff2") ||
            uri.endsWith(".ttf") ||
            uri.endsWith(".eot")
        );
    }

    /**
     * Handle custom resource not found exception
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleResourceNotFoundException(ResourceNotFoundException ex, Model model) {
        log.error("Resource not found: {}", ex.getMessage());
        model.addAttribute("error", "Resource Not Found");
        model.addAttribute("message", ex.getMessage());
        model.addAttribute("status", HttpStatus.NOT_FOUND.value());
        return "error";
    }

    /**
     * Handle unauthorized access exception
     */
    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String handleUnauthorizedException(UnauthorizedException ex, Model model) {
        log.error("Unauthorized access: {}", ex.getMessage());
        model.addAttribute("error", "Unauthorized Access");
        model.addAttribute("message", ex.getMessage());
        model.addAttribute("status", HttpStatus.UNAUTHORIZED.value());
        return "error";
    }

    /**
     * Handle all other uncaught exceptions
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleGenericException(Exception ex, Model model, HttpServletRequest request) {
        log.error("Unexpected error occurred at {}: {}", request.getRequestURI(), ex.getMessage(), ex);
        model.addAttribute("error", "Internal Server Error");
        model.addAttribute("message", "An unexpected error occurred. Please try again later or contact support.");
        model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        return "error";
    }
}

