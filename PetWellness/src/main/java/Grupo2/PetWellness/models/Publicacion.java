package Grupo2.PetWellness.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class Publicacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Temporal(TemporalType.DATE)
    private Date fecha;

    @NotNull
    @Temporal(TemporalType.TIME)
    private Date hora;

    @NotBlank
    private String categoria;

    @ManyToOne
    @NotNull
    private Usuario usuario;

    @NotBlank
    private String tema;

    private String img;

    @ManyToOne
    private TipoMascota tipoMascota;

    @ManyToOne
    private Grupo grupo;

    @OneToMany(mappedBy = "publicacion", cascade = CascadeType.ALL)
    private List<Comentario> comentarios = new ArrayList<>();
}
