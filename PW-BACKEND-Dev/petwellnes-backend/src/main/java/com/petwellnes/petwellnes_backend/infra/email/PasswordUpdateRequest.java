package com.petwellnes.petwellnes_backend.infra.email;

import lombok.Data;

@Data
public class PasswordUpdateRequest {
    private String email;
    private String token;
    private String newPassword;
}