package Grupo2.PetWellness.DTOs;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

    @NotBlank(message = "El id de la cuenta no puede estar vacío")
    @Size(min = 9, max = 9, message = "Su ID debe tener 9 caracteres")
    @Pattern(regexp ="[0-9]+", message = "El ID solo debe contener solo dígitos")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "El  nombre no puede estar vacío")
    private String owner_FirstName;

    @NotBlank(message = "El  apellido no puede estar vacío")
    private String owner_LastName;

    @NotBlank(message = "El correo no puede estár vacío")
    @Email
    private String email;

    @NotBlank(message = "La contraseña no puede estar vacía")
    @Size(min = 6, max = 15, message = "Su contraseña debe tener entre 6 y 15 caracteres")
    private String password;


}





