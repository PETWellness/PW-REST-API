package com.petwellnes.petwellnes_backend.model.dto.userDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record UserRegisterDTO(
        @NotBlank(message = "Debe haber un username")
        @Length(max = 50)
        String username,
        @NotBlank(message = "Debes colocar un correo")
        @Length(max = 50)
        @Email
        String email,
        @NotBlank(message = "Debes colocar una contraseña")
        @Length(max = 50)
        String password) {
}
