package com.io.tunehub.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id", nullable = false, updatable = false)
    private String id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    /**
     * Store hashed password only. Never print this in toString() or logs.
     */
    @Column(name = "hashed_password", nullable = false)
    private String hashedPassword;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String address;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_type", nullable = false)
    private AccountType accountType = AccountType.BASIC;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Lob
    @Column(name = "profile_picture_data", columnDefinition = "LONGBLOB")
    private byte[] profilePictureData;

    // ---------- Constructors ----------

    public User() {
    }

    /**
     * Convenience constructor (note: do not pass profile picture here).
     */
    public User(String id, String username, String email, String hashedPassword,
                Gender gender, Role role, String address, AccountType accountType) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.hashedPassword = hashedPassword;
        this.gender = gender;
        this.role = role;
        this.address = address;
        this.accountType = accountType == null ? AccountType.BASIC : accountType;
    }

    // ---------- Lifecycle hooks ----------

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    // ---------- Getters & Setters ----------

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    /**
     * Returns the hashed password. Use with care.
     */
    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType == null ? AccountType.BASIC : accountType;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public byte[] getProfilePictureData() {
        return profilePictureData;
    }

    public void setProfilePictureData(byte[] profilePictureData) {
        this.profilePictureData = profilePictureData;
    }

    public String getAvatarBase64() {
        if (profilePictureData == null) return null;
        return Base64.getEncoder().encodeToString(profilePictureData);
    }

    // Convenience boolean
    public boolean isPremium() {
        return this.accountType == AccountType.PREMIUM;
    }

    // ---------- toString (SAFE) ----------
    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", gender=" + gender +
                ", role=" + role +
                ", address='" + address + '\'' +
                ", accountType=" + accountType +
                ", createdAt=" + createdAt +
                '}';
    }
}
