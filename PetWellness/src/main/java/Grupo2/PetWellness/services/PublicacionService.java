package Grupo2.PetWellness.services;

import Grupo2.PetWellness.entitys.models.Publicacion;
import Grupo2.PetWellness.repositories.PublicacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PublicacionService {

    @Autowired
    private PublicacionRepository publicacionRepository;

    public List<Publicacion> findAll() {
        return publicacionRepository.findAll();
    }

    public Optional<Publicacion> findById(Long id) {
        return publicacionRepository.findById(id);
    }

    public Publicacion save(Publicacion publicacion) {
        return publicacionRepository.save(publicacion);
    }

    public void deleteById(Long id) {
        publicacionRepository.deleteById(id);
    }
}
