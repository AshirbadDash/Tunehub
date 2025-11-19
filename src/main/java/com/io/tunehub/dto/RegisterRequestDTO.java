package com.io.tunehub.dto;

import jakarta.validation.constraints.NotBlank;

public class RegisterRequestDTO {

    @NotBlank(message = "Username is required.")
    private String username;

    @NotBlank(message = "Email is required.")
    private String email;

    @NotBlank(message = "Password is required.")
    private String password;

    public RegisterRequestDTO(@NotBlank(message = "Username is required.") String username, @NotBlank(message = "Email is required.") String email, @NotBlank(message = "Password is required.") String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public RegisterRequestDTO() {
    }

    public @NotBlank(message = "Username is required.") String getUsername() {
        return this.username;
    }

    public void setUsername(@NotBlank(message = "Username is required.") String username) {
        this.username = username;
    }

    public @NotBlank(message = "Email is required.") String getEmail() {
        return this.email;
    }

    public void setEmail(@NotBlank(message = "Email is required.") String email) {
        this.email = email;
    }

    public @NotBlank(message = "Password is required.") String getPassword() {
        return this.password;
    }

    public void setPassword(@NotBlank(message = "Password is required.") String password) {
        this.password = password;
    }
}
