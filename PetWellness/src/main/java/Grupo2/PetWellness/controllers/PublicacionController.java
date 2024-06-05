package Grupo2.PetWellness.controllers;

import Grupo2.PetWellness.entitys.models.Publicacion;
import Grupo2.PetWellness.services.PublicacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/publicaciones")
public class PublicacionController {

    @Autowired
    private PublicacionService publicacionService;

    @GetMapping
    public List<Publicacion> getAllPublicaciones() {
        return publicacionService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Publicacion> getPublicacionById(@PathVariable Long id) {
        Optional<Publicacion> publicacion = publicacionService.findById(id);
        return publicacion.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Publicacion createPublicacion(@RequestBody Publicacion publicacion) {
        return publicacionService.save(publicacion);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Publicacion> updatePublicacion(@PathVariable Long id, @RequestBody Publicacion publicacionDetails) {
        Optional<Publicacion> publicacion = publicacionService.findById(id);
        if (publicacion.isPresent()) {
            Publicacion updatedPublicacion = publicacion.get();
            updatedPublicacion.setFecha(publicacionDetails.getFecha());
            updatedPublicacion.setHora(publicacionDetails.getHora());
            updatedPublicacion.setCategoria(publicacionDetails.getCategoria());
            updatedPublicacion.setUsuario(publicacionDetails.getUsuario());
            updatedPublicacion.setTema(publicacionDetails.getTema());
            updatedPublicacion.setImg(publicacionDetails.getImg());
            updatedPublicacion.setTipoMascota(publicacionDetails.getTipoMascota());
            updatedPublicacion.setGrupo(publicacionDetails.getGrupo());
            publicacionService.save(updatedPublicacion);
            return ResponseEntity.ok(updatedPublicacion);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePublicacion(@PathVariable Long id) {
        publicacionService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
