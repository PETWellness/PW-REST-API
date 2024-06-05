package Grupo2.PetWellness.entitys.DTOs;

import lombok.Data;

@Data
public class UsuarioDTO {
    private long id;
    private String ownerFirstName;
    private String ownerLastName;
    private String email;
    private String imgprofile;
}
