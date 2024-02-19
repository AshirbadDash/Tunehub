package com.kodnest.projecttunehub.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.hibernate.boot.beanvalidation.IntegrationException;

/**
 * Entity class for User.
 * This class is used to map the User table in the database.
 * Each instance of this class represents a single row in the User table.
 */
@Entity

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String userid; // Unique identifier for the user
    private String username; // Username of the user
    private String email; // Email of the user
    private String password; // Password of the user
    private String gender; // Gender of the user
    private String role; // Role of the user
    private String address; // Address of the user

    /**
     * Default constructor.
     * Initializes a new instance of the User class.
     */
    public User() {
        super();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Parameterized constructor.
     * Initializes a new instance of the User class with the given details.
     *
     * @param userid   Unique identifier for the user
     * @param username Username of the user
     * @param email    Email of the user
     * @param password Password of the user
     * @param gender   Gender of the user
     * @param role     Role of the user
     * @param address  Address of the user
     */
    public User(String userid, String username, String email, String password, String gender, String role, String address) {
        this.userid = userid;
        this.username = username;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.role = role;
        this.address = address;
    }

    // Getters and setters for each field with appropriate comments

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












    // Similar comments for other getters and setters...

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
                '}';
    }
}