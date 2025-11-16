package com.io.tunehub.dto;

import org.springframework.web.multipart.MultipartFile;

public record CreateUserDTO(String username, String email, String password, String gender, String role, String address,
                            MultipartFile profileImageFile) {
}
