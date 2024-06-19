package Grupo2.PetWellness.services;

import Grupo2.PetWellness.entitys.models.Tema;
import Grupo2.PetWellness.repositories.TemaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TemaService {

    @Autowired
    private TemaRepository temaRepository;

    public List<Tema> findAll() {
        return temaRepository.findAll();
    }

    public Optional<Tema> findById(Long id) {
        return temaRepository.findById(id);
    }

    public Tema save(Tema tema) {
        return temaRepository.save(tema);
    }

    public void deleteById(Long id) {
        temaRepository.deleteById(id);
    }
}
