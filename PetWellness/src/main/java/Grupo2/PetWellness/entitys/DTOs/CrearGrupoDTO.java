package Grupo2.PetWellness.entitys.DTOs;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class CrearGrupoDTO {
    @NotBlank
    private String nombre;
    @NotBlank
    private String descripcion;
    private String img;
}
