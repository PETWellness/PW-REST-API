package com.petwellnes.petwellnes_backend.controller;

import com.petwellnes.petwellnes_backend.model.dto.petDto.PetDto;
import com.petwellnes.petwellnes_backend.model.entity.Pet;
import com.petwellnes.petwellnes_backend.service.PetService;
import com.petwellnes.petwellnes_backend.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/pets")
public class PetController {

    @Autowired
    private PetService petService;

    @PostMapping("/create")
    public ResponseEntity<Pet> createPet(@RequestBody PetDto petDto, @AuthenticationPrincipal User user) {
        Pet newPet = petService.createPet(petDto, user.getUserId());
        return ResponseEntity.ok(newPet);
    }

    @GetMapping("/selectbyuser")
    public ResponseEntity<List<Pet>> getPetsByUser(@AuthenticationPrincipal User user) {
        List<Pet> pets = petService.getPetsByUserId(user.getUserId());
        return ResponseEntity.ok(pets);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pet> getPetById(@PathVariable Long id, @AuthenticationPrincipal User user) {
        Pet pet = petService.getPetByIdAndUserId(id, user.getUserId());
        return ResponseEntity.ok(pet);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pet> updatePet(@PathVariable Long id, @RequestBody PetDto petDto, @AuthenticationPrincipal User user) {
        Pet updatedPet = petService.updatePet(id, petDto, user.getUserId());
        return ResponseEntity.ok(updatedPet);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePet(@PathVariable Long id, @AuthenticationPrincipal User user) {
        petService.deletePet(id, user.getUserId());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/uploadProfilePhoto")
    public ResponseEntity<Pet> uploadProfilePhoto(@PathVariable Long id, @RequestParam("file") MultipartFile file, @AuthenticationPrincipal User user) throws IOException {
        Pet updatedPet = petService.uploadProfilePhoto(id, file, user.getUserId());
        return ResponseEntity.ok(updatedPet);
    }
}
