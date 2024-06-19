package Grupo2.PetWellness.controllers;

import Grupo2.PetWellness.entitys.models.Grupo;
import Grupo2.PetWellness.services.GrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/grupos")
public class GrupoController {

    @Autowired
    private GrupoService grupoService;

    @GetMapping
    public List<Grupo> getAllGrupos() {
        return grupoService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Grupo> getGrupoById(@PathVariable Long id) {
        Optional<Grupo> grupo = grupoService.findById(id);
        return grupo.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Grupo createGrupo(@RequestBody Grupo grupo) {
        return grupoService.save(grupo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Grupo> updateGrupo(@PathVariable Long id, @RequestBody Grupo grupoDetails) {
        Optional<Grupo> grupo = grupoService.findById(id);
        if (grupo.isPresent()) {
            Grupo updatedGrupo = grupo.get();
            updatedGrupo.setNombre(grupoDetails.getNombre());
            updatedGrupo.setDescription(grupoDetails.getDescription());
            updatedGrupo.setImg(grupoDetails.getImg());
            grupoService.save(updatedGrupo);
            return ResponseEntity.ok(updatedGrupo);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGrupo(@PathVariable Long id) {
        grupoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
