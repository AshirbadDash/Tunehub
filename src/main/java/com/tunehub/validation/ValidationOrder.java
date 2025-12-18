package com.tunehub.validation;

/**
 * Validation groups for controlling validation order.
 * First group checks required fields, second group checks format/constraints.
 */
public class ValidationOrder {
    
    /**
     * First validation phase - checks if required fields are present
     */
    public interface First {}
    
    /**
     * Second validation phase - checks format, size, pattern constraints
     */
    public interface Second {}
}
