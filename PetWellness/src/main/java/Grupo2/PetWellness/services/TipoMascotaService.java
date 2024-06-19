package Grupo2.PetWellness.services;

import Grupo2.PetWellness.entitys.models.TipoMascota;
import Grupo2.PetWellness.repositories.TipoMascotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TipoMascotaService {

    @Autowired
    private TipoMascotaRepository tipoMascotaRepository;

    public List<TipoMascota> findAll() {
        return tipoMascotaRepository.findAll();
    }

    public Optional<TipoMascota> findById(Long id) {
        return tipoMascotaRepository.findById(id);
    }

    public TipoMascota save(TipoMascota tipoMascota) {
        return tipoMascotaRepository.save(tipoMascota);
    }

    public void deleteById(Long id) {
        tipoMascotaRepository.deleteById(id);
    }
}
