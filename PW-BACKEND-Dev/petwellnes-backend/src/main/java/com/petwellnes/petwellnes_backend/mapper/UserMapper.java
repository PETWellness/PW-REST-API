package com.petwellnes.petwellnes_backend.mapper;

import com.petwellnes.petwellnes_backend.model.dto.userDto.UserRegisterDTO;
import com.petwellnes.petwellnes_backend.model.entity.User;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class UserMapper {

    private final ModelMapper modelMapper;

    public User convertToEntity(UserRegisterDTO userRegisterDTO) {
        return modelMapper.map(userRegisterDTO, User.class);
    }
    public UserRegisterDTO convertToDTO(User user) {
        return modelMapper.map(user, UserRegisterDTO.class);
    }
    public List<UserRegisterDTO> convertToDTO(List<User> users) {
        return users.stream()
                .map(this::convertToDTO)
                .toList();
    }
}
