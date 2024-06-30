package com.petwellnes.petwellnes_backend.service;

import com.petwellnes.petwellnes_backend.infra.email.PasswordResetToken;
import com.petwellnes.petwellnes_backend.infra.repository.PasswordResetTokenRepository;
import com.petwellnes.petwellnes_backend.infra.repository.UserRepository;
import com.petwellnes.petwellnes_backend.model.entity.User;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class PasswordResetService {

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String generateResetCode() {
        Random random = new Random();
        int resetCode = 100000 + random.nextInt(900000);
        return String.valueOf(resetCode);
    }

    @Async
    @Transactional
    public void sendPasswordResetEmail(String email) throws MessagingException {
        String resetCode = generateResetCode();
        tokenRepository.deleteByEmail(email);
        PasswordResetToken token = new PasswordResetToken();
        token.setEmail(email);
        token.setToken(resetCode);
        token.setExpiryDate(LocalDateTime.now().plusHours(1));
        tokenRepository.save(token);
        String subject = "Código de Restablecimiento de Contraseña";
        String text = "Tu código de restablecimiento de contraseña es: " + resetCode;
        emailService.sendEmail(email, subject, text);
    }

    public boolean validateResetToken(String token) {
        Optional<PasswordResetToken> resetToken = tokenRepository.findByToken(token);
        return resetToken.isPresent() && resetToken.get().getExpiryDate().isAfter(LocalDateTime.now());
    }

    @Transactional
    public void updatePassword(String token, String newPassword) {
        Optional<PasswordResetToken> resetToken = tokenRepository.findByToken(token);
        if (resetToken.isPresent()) {
            Optional<User> user = userRepository.findByEmail(resetToken.get().getEmail());
            if (user.isPresent()) {
                User usuario = user.get();
                usuario.setPassword(passwordEncoder.encode(newPassword));
                userRepository.save(usuario);
                tokenRepository.delete(resetToken.get());
            }
        }
    }
}

