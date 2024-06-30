package com.petwellnes.petwellnes_backend.mapper;

import com.petwellnes.petwellnes_backend.model.dto.petDto.PetDto;
import com.petwellnes.petwellnes_backend.model.entity.Pet;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PetMapper {

    @Autowired
    private ModelMapper modelMapper;

    public PetDto toDto(Pet pet) {
        return modelMapper.map(pet, PetDto.class);
    }

    public Pet toEntity(PetDto petDto) {
        return modelMapper.map(petDto, Pet.class);
    }
}
