package com.petwellnes.petwellnes_backend.model.dto.userDto;

import com.petwellnes.petwellnes_backend.model.entity.User;

import java.time.LocalDate;

public record UserDetailsDTO(
        Long userId,
        String username,
        String name,
        String lastname,
        String email,
        String phone,
        String work,
        LocalDate birthday,
        String country,
        LocalDate registerday,
        String profileImageUrl,
        String bannerUrl
) {
    public UserDetailsDTO(User user) {
        this(user.getUserId(), user.getUsername(), user.getName(), user.getLastname(), user.getEmail(), user.getPhone(), user.getWork(), user.getBirthday(), user.getCountry(), user.getRegisterday(), user.getProfileImageUrl(), user.getBannerUrl());
    }
}
