package com.tunehub.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tunehub.common.StandardizedOffsetDateTime;
import com.tunehub.model.enums.AccountType;
import com.tunehub.model.enums.Gender;
import com.tunehub.model.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {@Index(name = "idx_user_email", columnList = "email"), @Index(name = "idx_user_username", columnList = "username")})
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @JsonIgnore
    @Column(nullable = false)
    private String hashedPassword;

    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private Role role = Role.CUSTOMER;

    private String address;

    @Enumerated(EnumType.STRING)
    private AccountType accountType = AccountType.BASIC;

    @Column(nullable = false)
    private boolean active = true;

    @Column(nullable = false)
    private boolean emailVerified = false;

    private OffsetDateTime premiumUntil;

    @Column(nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @Column(nullable = false)
    private OffsetDateTime updatedAt;

    private OffsetDateTime lastLogin;

    @PrePersist
    void onCreate() {
        createdAt = StandardizedOffsetDateTime.now();
        updatedAt = createdAt;
    }

    @PreUpdate
    void onUpdate() {
        updatedAt = StandardizedOffsetDateTime.now();
    }

    public void updateLastLogin() {
        lastLogin = StandardizedOffsetDateTime.now();
    }
}
