package com.grupo2.PetWellness.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.grupo2.PetWellness.models.User;
import com.grupo2.PetWellness.services.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        User registeredUser = authService.registerUser(user);
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestParam String email, @RequestParam String password) {
        boolean isAuthenticated = authService.authenticateUser(email, password);
        if (isAuthenticated) {
            return ResponseEntity.ok("User authenticated successfully.");
        } else {
            return ResponseEntity.badRequest().body("Authentication failed.");
        }
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logoutUser() {
        // Assuming logout is handled at the client side by removing the session/token
        return ResponseEntity.ok("User logged out successfully.");
    }
}