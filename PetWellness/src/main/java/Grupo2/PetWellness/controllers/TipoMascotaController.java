package Grupo2.PetWellness.controllers;

import Grupo2.PetWellness.entitys.models.TipoMascota;
import Grupo2.PetWellness.services.TipoMascotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tiposmascota")
public class TipoMascotaController {

    @Autowired
    private TipoMascotaService tipoMascotaService;

    @GetMapping
    public List<TipoMascota> getAllTiposMascota() {
        return tipoMascotaService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoMascota> getTipoMascotaById(@PathVariable Long id) {
        Optional<TipoMascota> tipoMascota = tipoMascotaService.findById(id);
        return tipoMascota.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public TipoMascota createTipoMascota(@RequestBody TipoMascota tipoMascota) {
        return tipoMascotaService.save(tipoMascota);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoMascota> updateTipoMascota(@PathVariable Long id, @RequestBody TipoMascota tipoMascotaDetails) {
        Optional<TipoMascota> tipoMascota = tipoMascotaService.findById(id);
        if (tipoMascota.isPresent()) {
            TipoMascota updatedTipoMascota = tipoMascota.get();
            updatedTipoMascota.setNombre(tipoMascotaDetails.getNombre());
            updatedTipoMascota.setRazas(tipoMascotaDetails.getRazas());
            tipoMascotaService.save(updatedTipoMascota);
            return ResponseEntity.ok(updatedTipoMascota);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTipoMascota(@PathVariable Long id) {
        tipoMascotaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
