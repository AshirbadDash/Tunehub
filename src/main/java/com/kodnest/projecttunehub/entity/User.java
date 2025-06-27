package com.kodnest.projecttunehub.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * Represents a User entity in the application.
 * This class is a JPA entity that maps to the User table in the database.
 * Each instance of this class corresponds to a single row in the User table.
 */
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String userid; // The unique identifier for the user

    private String username; // The username of the user
    private String email; // The email of the user
    private String password; // The password of the user
    private String gender; // The gender of the user
    private String role; // The role of the user
    private String address; // The address of the user
    private boolean isPremium; // The premium status of the user

    /**
     * Default constructor.
     * Initializes a new instance of the User class.
     */
    public User() {
        super();
    }

    /**
     * Parameterized constructor.
     * Initializes a new instance of the User class with the given details.
     *
     * @param userid    The unique identifier for the user
     * @param username  The username of the user
     * @param email     The email of the user
     * @param password  The password of the user
     * @param gender    The gender of the user
     * @param role      The role of the user
     * @param address   The address of the user
     * @param isPremium The premium status of the user
     */
    public User(String userid, String username, String email, String password, String gender, String role, String address, boolean isPremium) {
        this.userid = userid;
        this.username = username;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.role = role;
        this.address = address;
        this.isPremium = isPremium;
    }

    // Getters and setters for each field

    /**
     * Returns the unique identifier of the user.
     *
     * @return The unique identifier of the user
     */
    public String getUserid() {
        return userid;
    }

    /**
     * Sets the unique identifier of the user.
     *
     * @param userid The unique identifier of the user
     */
    public void setUserid(String userid) {
        this.userid = userid;
    }

    /**
     * Returns the username of the user.
     *
     * @return The username of the user
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the user.
     *
     * @param username The username of the user
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Returns the email of the user.
     *
     * @return The email of the user
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of the user.
     *
     * @param email The email of the user
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the password of the user.
     *
     * @return The password of the user
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the user.
     *
     * @param password The password of the user
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns the gender of the user.
     *
     * @return The gender of the user
     */
    public String getGender() {
        return gender;
    }

    /**
     * Sets the gender of the user.
     *
     * @param gender The gender of the user
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * Returns the role of the user.
     *
     * @return The role of the user
     */
    public String getRole() {
        return role;
    }

    /**
     * Sets the role of the user.
     *
     * @param role The role of the user
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Returns the address of the user.
     *
     * @return The address of the user
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address of the user.
     *
     * @param address The address of the user
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Returns the premium status of the user.
     *
     * @return The premium status of the user
     */
    public boolean isPremium() {
        return isPremium;
    }

    /**
     * Sets the premium status of the user.
     *
     * @param isPremium The premium status of the user
     */
    public void setPremium(boolean isPremium) {
        this.isPremium = isPremium;
    }

    /**
     * Returns a string representation of the User object.
     *
     * @return A string representation of the User object
     */
    @Override
    public String toString() {
        return "User{" +
                "userid='" + userid + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", gender='" + gender + '\'' +
                ", role='" + role + '\'' +
                ", address='" + address + '\'' +
                ", isPremium=" + isPremium +
                '}';
    }
}