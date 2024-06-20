package Grupo2.PetWellness.repositories;

import Grupo2.PetWellness.entitys.models.Tema;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TemaRepository extends JpaRepository<Tema, Long> {
}
