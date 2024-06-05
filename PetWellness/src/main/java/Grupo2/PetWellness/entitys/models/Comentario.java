package Grupo2.PetWellness.entitys.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Comentario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String contenido;

    @ManyToOne
    @NotNull
    private Usuario usuario;

    @OneToMany(mappedBy = "comentarioPadre", cascade = CascadeType.ALL)
    private List<Comentario> respuestas = new ArrayList<>();

    @ManyToOne
    private Comentario comentarioPadre;
}
