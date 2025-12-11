package com.tunehub.model.enums;

/**
 * Enum representing user account subscription types.
 */
public enum AccountType {
    BASIC("Basic", "Free account with limited features", 0.0),
    PREMIUM("Premium", "Premium subscription with full features", 9.99),
    FAMILY("Family", "Family plan for up to 6 members", 14.99),
    STUDENT("Student", "Discounted plan for students", 4.99);

    private final String displayName;
    private final String description;
    private final double monthlyPrice;

    AccountType(String displayName, String description, double monthlyPrice) {
        this.displayName = displayName;
        this.description = description;
        this.monthlyPrice = monthlyPrice;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }

    public double getMonthlyPrice() {
        return monthlyPrice;
    }

    public boolean isPremium() {
        return this != BASIC;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
