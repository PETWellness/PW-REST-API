package Grupo2.PetWellness.entitys.DTOs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class CrearUsuarioDTO {
    @NotBlank
    private String ownerFirstName;
    @NotBlank
    private String ownerLastName;
    @Email
    @NotBlank
    private String email;
    @NotBlank
    private String password;
    private String imgprofile;
}
