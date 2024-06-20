package Grupo2.PetWellness.repositories;

import Grupo2.PetWellness.entitys.models.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComentarioRepository extends JpaRepository<Comentario, Long> {
}
