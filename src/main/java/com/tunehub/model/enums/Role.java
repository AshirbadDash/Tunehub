package com.tunehub.model.enums;

/**
 * Enum representing user roles in the system.
 */
public enum Role {
    ADMIN("Administrator", "Full system access with administrative privileges"),
    CUSTOMER("Customer", "Standard user with basic access"),
    ARTIST("Artist", "Music creator with content management access"),
    MODERATOR("Moderator", "Content moderation access");

    private final String displayName;
    private final String description;

    Role(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }

    public boolean isAdmin() {
        return this == ADMIN;
    }

    public boolean isCustomer() {
        return this == CUSTOMER;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
