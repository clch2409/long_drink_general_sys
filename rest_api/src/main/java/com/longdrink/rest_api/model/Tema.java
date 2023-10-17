package com.longdrink.rest_api.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@Entity(name = "Tema")
@Table(name = "tema")
public class Tema implements Serializable {
    @Id
    @Column(name = "cod_tema")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codTema;
    @Column(length = 30)
    private String nombre;
    private String ficha; //Url?

    @ManyToMany(mappedBy = "temas")
    private List<Curso> cursos;
}
