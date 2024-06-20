package Grupo2.PetWellness.services;

import Grupo2.PetWellness.entitys.models.Grupo;
import Grupo2.PetWellness.repositories.GrupoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GrupoService {

    @Autowired
    private GrupoRepository grupoRepository;

    public List<Grupo> findAll() {
        return grupoRepository.findAll();
    }

    public Optional<Grupo> findById(Long id) {
        return grupoRepository.findById(id);
    }

    public Grupo save(Grupo grupo) {
        return grupoRepository.save(grupo);
    }

    public void deleteById(Long id) {
        grupoRepository.deleteById(id);
    }
}
