package com.tunehub.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tunehub.validation.ValidationOrder;
import jakarta.validation.GroupSequence;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * User registration request DTO with ordered validation.
 * Validation runs in sequence: First checks required fields, then format/constraints.
 */
@Getter
@Setter
@NoArgsConstructor
@GroupSequence({ValidationOrder.First.class, ValidationOrder.Second.class, UserRegisterRequestDTO.class})
public class UserRegisterRequestDTO {

    @NotBlank(message = "Username is required", groups = ValidationOrder.First.class)
    @Pattern(
        regexp = "^[a-zA-Z0-9_-]{3,50}$", 
        message = "Username must be 3-50 characters and can only contain letters, numbers, underscores, and hyphens",
        groups = ValidationOrder.Second.class
    )
    private String username;

    @NotBlank(message = "Email is required", groups = ValidationOrder.First.class)
    @Pattern(
        regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", 
        message = "Please provide a valid email address",
        groups = ValidationOrder.Second.class
    )
    @Size(max = 100, message = "Email must not exceed 100 characters", groups = ValidationOrder.Second.class)
    private String email;

    @NotBlank(message = "Password is required", groups = ValidationOrder.First.class)
    @Pattern(
        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,100}$",
        message = "Password must be 8-100 characters with at least one uppercase, one lowercase, one digit, and one special character (@$!%*?&)",
        groups = ValidationOrder.Second.class
    )
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @NotBlank(message = "Password confirmation is required", groups = ValidationOrder.First.class)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String confirmPassword;

    @Pattern(
            regexp = "^$|^[0-9]{10,15}$",
            message = "Phone number must be 10-15 digits"
    )
    private String phoneNumber;

    /**
     * Validates that password and confirmPassword match
     */
    public boolean isPasswordMatch() {
        return password != null && password.equals(confirmPassword);
    }
}
