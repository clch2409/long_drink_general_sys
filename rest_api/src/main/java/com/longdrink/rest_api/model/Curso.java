package com.longdrink.rest_api.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ManyToAny;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@Entity(name = "Curso")
@Table(name = "curso")
public class Curso implements Serializable {
    @Id
    @Column(name = "cod_curso")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codCurso;
    @Column(length = 150)
    private String descripcion;
    private double mensualidad;
    private byte duracion;
    @Column(name = "cantidad_alumnos")
    private byte cantidadAlumnos;
    private boolean visibilidad;
    @Column(length = 50)
    private String frecuencia;
    private String imagen;

    @ManyToOne
    @JoinColumn(name = "cod_profesor")
    private Profesor profesor;

    @ManyToMany
    @JoinTable(name = "curso_turno",
            joinColumns = @JoinColumn(name = "cod_curso"),
            inverseJoinColumns = @JoinColumn(name = "cod_turno"))
    private List<Turno> turnos;

    @ManyToMany
    @JoinTable(name = "curso_tema",
            joinColumns = @JoinColumn(name = "cod_curso"),
            inverseJoinColumns = @JoinColumn(name = "cod_tema"))
    private List<Tema> temas;

    @OneToMany(mappedBy = "curso")
    private List<Inscripcion> inscripciones;
}
