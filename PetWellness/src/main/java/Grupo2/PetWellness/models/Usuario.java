package Grupo2.PetWellness.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column (name = "owner_firstname", nullable = false)
    private String firstname;

    @Column (name = "owner_lastname", nullable = false)
    private String lastname;

    @Column (name = "owner_email", nullable = false, unique = true)
    private String email;

    @Column (name = "owner_password", nullable = false)
    private String password;

    @Column (name = "account_imgprofile")
    private String imgprofile;

}
