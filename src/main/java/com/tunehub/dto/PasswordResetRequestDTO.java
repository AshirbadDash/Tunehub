package com.tunehub.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class PasswordResetRequestDTO {
    @NotBlank(message = "Email is required.")
    @Email(message = "Email should be valid.")
    private String email;

}

