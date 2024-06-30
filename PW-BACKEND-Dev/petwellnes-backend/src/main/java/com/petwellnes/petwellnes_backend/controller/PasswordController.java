package com.petwellnes.petwellnes_backend.controller;

import com.petwellnes.petwellnes_backend.infra.email.EmailRequest;
import com.petwellnes.petwellnes_backend.infra.email.PasswordUpdateRequest;
import com.petwellnes.petwellnes_backend.infra.email.TokenValidationRequest;
import com.petwellnes.petwellnes_backend.service.PasswordResetService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/password")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class PasswordController {

    private static final Logger logger = LoggerFactory.getLogger(PasswordController.class);
    private final PasswordResetService passwordResetService;

    @PostMapping("/reset")
    public ResponseEntity<Map<String, String>> resetPassword(@Valid @RequestBody EmailRequest request) {
        Map<String, String> response = new HashMap<>();
        try {
            passwordResetService.sendPasswordResetEmail(request.getEmail());
            response.put("message", "Correo de restablecimiento de contraseña enviado exitosamente");
            return ResponseEntity.ok(response);
        } catch (MessagingException e) {
            logger.error("Error al enviar el correo de restablecimiento de contraseña: ", e);
            response.put("message", "Error al enviar el correo de restablecimiento de contraseña");
            return ResponseEntity.status(500).body(response);
        } catch (Exception e) {
            logger.error("Error inesperado: ", e);
            response.put("message", "Error inesperado");
            return ResponseEntity.status(500).body(response);
        }
    }

    @PostMapping("/validate")
    public ResponseEntity<Boolean> validateResetToken(@Valid @RequestBody TokenValidationRequest request) {
        boolean isValid = passwordResetService.validateResetToken(request.getToken());
        return ResponseEntity.ok(isValid);
    }

    @PostMapping("/update")
    public ResponseEntity<Map<String, String>> updatePassword(@Valid @RequestBody PasswordUpdateRequest request) {
        Map<String, String> response = new HashMap<>();
        try {
            boolean isValid = passwordResetService.validateResetToken(request.getToken());
            if (isValid) {
                passwordResetService.updatePassword(request.getToken(), request.getNewPassword());
                response.put("message", "Contraseña actualizada exitosamente");
                return ResponseEntity.ok(response);
            } else {
                response.put("message", "Token de restablecimiento de contraseña inválido");
                return ResponseEntity.status(400).body(response);
            }
        } catch (Exception e) {
            logger.error("Error al actualizar la contraseña: ", e);
            response.put("message", "Error al actualizar la contraseña");
            return ResponseEntity.status(500).body(response);
        }
    }
}
