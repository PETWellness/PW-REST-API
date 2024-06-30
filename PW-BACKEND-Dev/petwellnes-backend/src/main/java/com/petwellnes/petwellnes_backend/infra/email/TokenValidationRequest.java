package com.petwellnes.petwellnes_backend.infra.email;

import lombok.Data;

@Data
public class TokenValidationRequest {
    private String email;
    private String token;
}