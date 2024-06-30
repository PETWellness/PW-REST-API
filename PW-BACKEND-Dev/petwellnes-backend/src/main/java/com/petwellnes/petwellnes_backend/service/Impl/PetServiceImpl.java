package com.petwellnes.petwellnes_backend.service.Impl;

import com.petwellnes.petwellnes_backend.model.dto.petDto.PetDto;
import com.petwellnes.petwellnes_backend.model.entity.Pet;
import com.petwellnes.petwellnes_backend.model.entity.User;
import com.petwellnes.petwellnes_backend.infra.repository.PetRepository;
import com.petwellnes.petwellnes_backend.infra.repository.UserRepository;
import com.petwellnes.petwellnes_backend.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class PetServiceImpl implements PetService {

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private UserRepository userRepository;

    private final Path root = Paths.get("uploads");

    @Override
    public Pet createPet(PetDto petDto, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Pet pet = new Pet();
        pet.setName(petDto.getName());
        pet.setSpecies(petDto.getSpecies());
        pet.setBreed(petDto.getBreed());
        pet.setAge(petDto.getAge());
        pet.setPhoto(petDto.getPhoto());
        pet.setUser(user);

        return petRepository.save(pet);
    }

    @Override
    public List<Pet> getPetsByUserId(Long userId) {
        return petRepository.findByUserId(userId);
    }

    @Override
    public Pet getPetByIdAndUserId(Long id, Long userId) {
        return petRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new RuntimeException("Pet not found"));
    }

    @Override
    public Pet updatePet(Long id, PetDto petDto, Long userId) {
        Pet existingPet = petRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new RuntimeException("Pet not found"));

        existingPet.setName(petDto.getName());
        existingPet.setSpecies(petDto.getSpecies());
        existingPet.setBreed(petDto.getBreed());
        existingPet.setAge(petDto.getAge());
        existingPet.setPhoto(petDto.getPhoto());

        return petRepository.save(existingPet);
    }

    @Override
    public void deletePet(Long id, Long userId) {
        Pet existingPet = petRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new RuntimeException("Pet not found"));
        petRepository.delete(existingPet);
    }

    @Override
    public Pet uploadProfilePhoto(Long id, MultipartFile file, Long userId) throws IOException {
        Pet existingPet = petRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new RuntimeException("Pet not found"));

        // Crear el directorio si no existe
        if (!Files.exists(root)) {
            Files.createDirectory(root);
        }

        // Generar un nombre único para el archivo
        String filename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path filePath = root.resolve(filename);

        // Guardar el archivo en el sistema de archivos
        Files.copy(file.getInputStream(), filePath);

        // Actualizar la foto de perfil de la mascota
        existingPet.setProfilePhoto(filename);
        return petRepository.save(existingPet);
    }
}
