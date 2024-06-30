package com.petwellnes.petwellnes_backend.service;

import com.petwellnes.petwellnes_backend.infra.config.security.ChangePasswordRequest;
import com.petwellnes.petwellnes_backend.infra.config.security.LoginRequest;
import com.petwellnes.petwellnes_backend.infra.config.security.TokenResponse;
import com.petwellnes.petwellnes_backend.model.dto.userDto.UserDetailsDTO;
import com.petwellnes.petwellnes_backend.model.dto.userDto.UserRegisterDTO;
import com.petwellnes.petwellnes_backend.model.dto.userDto.UserUpdateDTO;
import com.petwellnes.petwellnes_backend.model.entity.User;

public interface UserService {

    TokenResponse login(LoginRequest request);

    TokenResponse addUser(UserRegisterDTO user);

    User getAuthUser();

    UserDetailsDTO getUserDetails(Long userId);

    UserDetailsDTO updateUserDetails(Long userId, UserUpdateDTO userUpdateDTO);

    void updateUserProfileImage(Long userId, String imageUrl);

    void updateUserBannerImage(Long userId, String imageUrl);

    boolean changePassword(ChangePasswordRequest changePasswordRequest);
}