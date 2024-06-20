package Grupo2.PetWellness.controllers;

import Grupo2.PetWellness.entitys.models.Comentario;
import Grupo2.PetWellness.services.ComentarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/comentarios")
public class ComentarioController {

    @Autowired
    private ComentarioService comentarioService;

    @GetMapping
    public List<Comentario> getAllComentarios() {
        return comentarioService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comentario> getComentarioById(@PathVariable Long id) {
        Optional<Comentario> comentario = comentarioService.findById(id);
        return comentario.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Comentario createComentario(@RequestBody Comentario comentario) {
        return comentarioService.save(comentario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Comentario> updateComentario(@PathVariable Long id, @RequestBody Comentario comentarioDetails) {
        Optional<Comentario> comentario = comentarioService.findById(id);
        if (comentario.isPresent()) {
            Comentario updatedComentario = comentario.get();
            updatedComentario.setContenido(comentarioDetails.getContenido());
            updatedComentario.setUsuario(comentarioDetails.getUsuario());
            updatedComentario.setComentarioPadre(comentarioDetails.getComentarioPadre());
            comentarioService.save(updatedComentario);
            return ResponseEntity.ok(updatedComentario);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComentario(@PathVariable Long id) {
        comentarioService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
