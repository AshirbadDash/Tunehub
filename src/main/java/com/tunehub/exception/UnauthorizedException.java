package com.tunehub.exception;

/**
 * Exception thrown when a user is not authorized to access a resource.
 */
public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException(String message) {
        super(message);
    }

    public UnauthorizedException() {
        super("You are not authorized to access this resource.");
    }
}

