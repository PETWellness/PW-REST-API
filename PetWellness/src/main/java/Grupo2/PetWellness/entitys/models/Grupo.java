package Grupo2.PetWellness.entitys.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Groups")
public class Grupo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "group_name", nullable = false)
    private String nombre;

    @Column(name = "group_description", nullable = false)
    private String description;

    @Column(name = "group_img")
    private String img;

    @ManyToMany(mappedBy = "grupos")
    private List<Usuario> miembros = new ArrayList<>();
}
