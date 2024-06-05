package Grupo2.PetWellness.controllers;

import Grupo2.PetWellness.entitys.models.RazaAnimal;
import Grupo2.PetWellness.services.RazaAnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/razas")
public class RazaAnimalController {

    @Autowired
    private RazaAnimalService razaAnimalService;

    @GetMapping
    public List<RazaAnimal> getAllRazas() {
        return razaAnimalService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RazaAnimal> getRazaById(@PathVariable Long id) {
        Optional<RazaAnimal> razaAnimal = razaAnimalService.findById(id);
        return razaAnimal.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public RazaAnimal createRaza(@RequestBody RazaAnimal razaAnimal) {
        return razaAnimalService.save(razaAnimal);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RazaAnimal> updateRaza(@PathVariable Long id, @RequestBody RazaAnimal razaDetails) {
        Optional<RazaAnimal> razaAnimal = razaAnimalService.findById(id);
        if (razaAnimal.isPresent()) {
            RazaAnimal updatedRaza = razaAnimal.get();
            updatedRaza.setNombre(razaDetails.getNombre());
            updatedRaza.setTipoMascota(razaDetails.getTipoMascota());
            razaAnimalService.save(updatedRaza);
            return ResponseEntity.ok(updatedRaza);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRaza(@PathVariable Long id) {
        razaAnimalService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
