package Grupo2.PetWellness.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private long id;
    private String ownerName;
    private String email;
    private String password;
    private String imgprofile;


}
