package com.petwellnes.petwellnes_backend.service;

import com.petwellnes.petwellnes_backend.model.dto.petDto.PetDto;
import com.petwellnes.petwellnes_backend.model.entity.Pet;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PetService {
    Pet createPet(PetDto petDto, Long userId);
    List<Pet> getPetsByUserId(Long userId);
    Pet getPetByIdAndUserId(Long id, Long userId);
    Pet updatePet(Long id, PetDto petDto, Long userId);
    void deletePet(Long id, Long userId);
    Pet uploadProfilePhoto(Long id, MultipartFile file, Long userId) throws IOException;
}
