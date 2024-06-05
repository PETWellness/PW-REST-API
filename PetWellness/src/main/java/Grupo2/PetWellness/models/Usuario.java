package Grupo2.PetWellness.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Users")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "owner_FirstName", nullable = false)
    private String ownerFirstName;

    @Column(name = "owner_LastName", nullable = false)
    private String ownerLastName;

    @Column(name = "owner_email", nullable = false, unique = true)
    private String email;

    @Column(name = "owner_password", nullable = false)
    private String password;

    @Column(name = "account_imgprofile")
    private String imgprofile;

    @ManyToMany
    @JoinTable(
            name = "usuario_grupo",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "grupo_id")
    )
    private List<Grupo> grupos = new ArrayList<>();
}
