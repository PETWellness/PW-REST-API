package com.petwellnes.petwellnes_backend.controller;

import com.petwellnes.petwellnes_backend.infra.config.security.ChangePasswordRequest;
import com.petwellnes.petwellnes_backend.infra.config.security.LoginRequest;
import com.petwellnes.petwellnes_backend.infra.config.security.TokenResponse;
import com.petwellnes.petwellnes_backend.model.dto.userDto.UserDetailsDTO;
import com.petwellnes.petwellnes_backend.model.dto.userDto.UserRegisterDTO;
import com.petwellnes.petwellnes_backend.model.dto.userDto.UserUpdateDTO;
import com.petwellnes.petwellnes_backend.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    @Transactional
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            TokenResponse tokenResponse = userService.login(loginRequest);
            Map<String, Object> response = new HashMap<>();
            response.put("token", tokenResponse.getToken());
            response.put("userId", tokenResponse.getUserId());
            return ResponseEntity.ok(response);
        } catch (DisabledException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Cuenta deshabilitada.");
        } catch (UsernameNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no encontrado.");
        } catch (AuthenticationException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error en la autenticación.");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor");
        }
    }

    @PostMapping("/register")
    @Transactional
    public ResponseEntity<?> addUser(@RequestBody @Valid UserRegisterDTO data) {
        try {
            TokenResponse tokenResponse = userService.addUser(data);
            Map<String, Object> response = new HashMap<>();
            response.put("userId", tokenResponse.getUserId());
            response.put("token", tokenResponse.getToken());
            return ResponseEntity.ok(tokenResponse);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al registrar el usuario.");
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDetailsDTO> getUserDetails(@PathVariable Long userId) {
        try {
            UserDetailsDTO userDetails = userService.getUserDetails(userId);
            return ResponseEntity.ok(userDetails);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDetailsDTO> updateUserDetails(@PathVariable Long userId, @RequestBody @Valid UserUpdateDTO userUpdateDTO) {
        try {
            UserDetailsDTO updatedUser = userService.updateUserDetails(userId, userUpdateDTO);
            return ResponseEntity.ok(updatedUser);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/upload-profile-image")
    public ResponseEntity<?> uploadProfileImage(@RequestParam("file") MultipartFile file, @RequestParam("userId") Long userId) {
        if (file.isEmpty() || !file.getContentType().startsWith("image/")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please select an image file");
        }

        try {
            Path directory = Paths.get(uploadPath);
            if (!Files.exists(directory)) {
                Files.createDirectories(directory);
            }

            byte[] bytes = file.getBytes();
            Path path = directory.resolve(file.getOriginalFilename());
            Files.write(path, bytes);

            String imageUrl = "/uploads/" + file.getOriginalFilename();
            userService.updateUserProfileImage(userId, imageUrl);

            Map<String, String> response = new HashMap<>();
            response.put("imageUrl", imageUrl);
            return ResponseEntity.ok().body(response);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading file");
        }
    }

    @PostMapping("/upload-banner-image")
    public ResponseEntity<?> uploadBannerImage(@RequestParam("file") MultipartFile file, @RequestParam("userId") Long userId) {
        if (file.isEmpty() || !file.getContentType().startsWith("image/")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please select an image file");
        }

        try {
            Path directory = Paths.get(uploadPath);
            if (!Files.exists(directory)) {
                Files.createDirectories(directory);
            }

            byte[] bytes = file.getBytes();
            Path path = directory.resolve(file.getOriginalFilename());
            Files.write(path, bytes);

            String imageUrl = "/uploads/" + file.getOriginalFilename();
            userService.updateUserBannerImage(userId, imageUrl);

            Map<String, String> response = new HashMap<>();
            response.put("imageUrl", imageUrl);
            return ResponseEntity.ok().body(response);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading file");
        }
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
        try {
            boolean isChanged = userService.changePassword(changePasswordRequest);
            if (isChanged) {
                Map<String, String> response = new HashMap<>();
                response.put("message", "Contraseña cambiada correctamente");
                return ResponseEntity.ok(response);
            } else {
                Map<String, String> response = new HashMap<>();
                response.put("message", "La contraseña actual es incorrecta");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Map<String, String> response = new HashMap<>();
            response.put("message", "Error interno del servidor");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}