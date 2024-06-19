package Grupo2.PetWellness.controllers;

import Grupo2.PetWellness.entitys.models.Tema;
import Grupo2.PetWellness.services.TemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/temas")
public class TemaController {

    @Autowired
    private TemaService temaService;

    @GetMapping
    public List<Tema> getAllTemas() {
        return temaService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tema> getTemaById(@PathVariable Long id) {
        Optional<Tema> tema = temaService.findById(id);
        return tema.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Tema createTema(@RequestBody Tema tema) {
        return temaService.save(tema);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tema> updateTema(@PathVariable Long id, @RequestBody Tema temaDetails) {
        Optional<Tema> tema = temaService.findById(id);
        if (tema.isPresent()) {
            Tema updatedTema = tema.get();
            updatedTema.setTema(temaDetails.getTema());
            temaService.save(updatedTema);
            return ResponseEntity.ok(updatedTema);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTema(@PathVariable Long id) {
        temaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
