package Grupo2.PetWellness.services;

import Grupo2.PetWellness.entitys.models.RazaAnimal;
import Grupo2.PetWellness.repositories.RazaAnimalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RazaAnimalService {

    @Autowired
    private RazaAnimalRepository razaAnimalRepository;

    public List<RazaAnimal> findAll() {
        return razaAnimalRepository.findAll();
    }

    public Optional<RazaAnimal> findById(Long id) {
        return razaAnimalRepository.findById(id);
    }

    public RazaAnimal save(RazaAnimal razaAnimal) {
        return razaAnimalRepository.save(razaAnimal);
    }

    public void deleteById(Long id) {
        razaAnimalRepository.deleteById(id);
    }
}
