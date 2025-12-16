package com.tunehub.mapper;

import com.tunehub.dto.UserRegisterRequestDTO;
import com.tunehub.model.entity.User;
import com.tunehub.model.enums.AccountType;
import com.tunehub.model.enums.Role;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Locale;
import java.util.Objects;

public final class UserMapper {

    private UserMapper() {}

    public static User toUser(UserRegisterRequestDTO dto) {
        Objects.requireNonNull(dto, "Register request must not be null");

        User user = new User();

        // Required fields (fail fast)
        user.setUsername(requireAndTrim(dto.getUsername(), "username"));
        user.setEmail(
                requireAndTrim(dto.getEmail(), "email").toLowerCase(Locale.ROOT)
        );

        user.setHashedPassword(
                BCrypt.hashpw(
                        requireAndTrim(dto.getPassword(), "password"),
                        BCrypt.gensalt(12)
                )
        );

        // Optional fields
        String phone = dto.getPhoneNumber();
        user.setPhoneNumber(
                (phone == null || phone.isBlank()) ? null : phone.trim()
        );

        // Defaults
        user.setRole(Role.CUSTOMER);
        user.setAccountType(AccountType.BASIC);
        user.setActive(true);
        user.setEmailVerified(false);

        return user;
    }

    // -------- helpers --------

    private static String requireAndTrim(String value, String field) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(field + " must not be null or blank");
        }
        return value.trim();
    }

    private static String trimToNull(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}
