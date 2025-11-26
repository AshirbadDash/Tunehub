package com.bloomwave.dto;

public class PasswordResetRequestDTO {
    public class ResetRequest {
        private String email;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }
}
