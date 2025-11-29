package com.tunehub.dto;

import org.springframework.web.multipart.MultipartFile;

public record RegisterResponseDTO(String username, String email, String password, String gender, String role,
                                  String address,
                                  MultipartFile profileImageFile){
}
