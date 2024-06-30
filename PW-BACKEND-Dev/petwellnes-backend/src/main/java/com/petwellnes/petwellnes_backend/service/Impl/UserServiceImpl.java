package com.petwellnes.petwellnes_backend.service.Impl;

import com.petwellnes.petwellnes_backend.infra.config.security.ChangePasswordRequest;
import com.petwellnes.petwellnes_backend.infra.config.security.LoginRequest;
import com.petwellnes.petwellnes_backend.infra.config.security.TokenResponse;
import com.petwellnes.petwellnes_backend.infra.exception.UsernameNotFoundException;
import com.petwellnes.petwellnes_backend.infra.repository.UserRepository;
import com.petwellnes.petwellnes_backend.model.dto.userDto.UserDetailsDTO;
import com.petwellnes.petwellnes_backend.model.dto.userDto.UserRegisterDTO;
import com.petwellnes.petwellnes_backend.model.dto.userDto.UserUpdateDTO;
import com.petwellnes.petwellnes_backend.model.entity.User;
import com.petwellnes.petwellnes_backend.infra.config.security.JwtService;
import com.petwellnes.petwellnes_backend.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private static final String DEFAULT_PROFILE_IMAGE_URL = "default-profile.png";
    private static final String DEFAULT_BANNER_URL = "default-banner.png";

    @Override
    public TokenResponse login(LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            User user = userRepository.findByUsername(loginRequest.getUsername())
                    .orElseThrow(() -> new UsernameNotFoundException("El siguiente username es incorrecto o no existe :" + loginRequest.getUsername()));

            String token = jwtService.getToken(userDetails, user);
            return TokenResponse.builder()
                    .token(token)
                    .userId(user.getUserId())
                    .build();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    @Override
    @Transactional
    public TokenResponse addUser(UserRegisterDTO data) {
        User user = new User();
        user.setUsername(data.username());
        user.setEmail(data.email());
        user.setPassword(passwordEncoder.encode(data.password()));
        user.setEnabled(true);
        user.setRegisterday(LocalDate.now());

        userRepository.save(user);

        String token = jwtService.getToken(user, user);
        return TokenResponse.builder()
                .token(token)
                .userId(user.getUserId())
                .build();
    }

    @Override
    public User getAuthUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Contexto de seguridad: " + SecurityContextHolder.getContext());

        if (auth == null || !auth.isAuthenticated()) {
            System.out.println("No hay usuario autenticado");
            throw new RuntimeException("No hay usuario autenticado");
        }

        String currentUserName = auth.getName();
        System.out.println("Usuario autenticado: " + currentUserName);
        return userRepository.findByUsername(currentUserName)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
    }

    @Override
    public UserDetailsDTO getUserDetails(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        return new UserDetailsDTO(user);
    }

    @Override
    @Transactional
    public UserDetailsDTO updateUserDetails(Long userId, UserUpdateDTO userUpdateDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        user.setUsername(userUpdateDTO.username());
        user.setEmail(userUpdateDTO.email());
        user.setName(userUpdateDTO.name());
        user.setLastname(userUpdateDTO.lastname());
        user.setPhone(userUpdateDTO.phone());
        user.setWork(userUpdateDTO.work());
        user.setBirthday(userUpdateDTO.birthday());
        user.setCountry(userUpdateDTO.country());
        user.setProfileImageUrl(userUpdateDTO.profileImageUrl());
        user.setBannerUrl(userUpdateDTO.bannerUrl());
        if (userUpdateDTO.password() != null && !userUpdateDTO.password().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userUpdateDTO.password()));
        }

        userRepository.save(user);

        return new UserDetailsDTO(user);
    }

    @Override
    public void updateUserProfileImage(Long userId, String imageUrl) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        user.setProfileImageUrl(imageUrl);
        userRepository.save(user);
    }

    private void UserValidate(User user) {
        if (user.getUsername() == null || user.getUsername().isEmpty()) {
            throw new UsernameNotFoundException("El username no puede ser vacío");
        }
    }

    @Override
    public void updateUserBannerImage(Long userId, String imageUrl) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        user.setBannerUrl(imageUrl);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public boolean changePassword(ChangePasswordRequest changePasswordRequest) {
        try {
            User user = getAuthUser();
            if (passwordEncoder.matches(changePasswordRequest.getCurrentPassword(), user.getPassword())) {
                user.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
                userRepository.save(user);
                return true;
            } else {
                return false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }
}
