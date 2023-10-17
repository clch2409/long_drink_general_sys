package com.longdrink.rest_api.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@Entity(name = "Alumno")
@Table(name = "alumno")
public class Alumno implements Serializable {
    @Id
    @Column(name = "cod_alumno")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codAlumno;
    @Column(length = 50)
    private String nombre;
    @Column(name = "apellido_paterno",length = 25)
    private String apellidoPaterno;
    @Column(name = "apellido_materno",length = 25)
    private String apellidoMaterno;
    @Column(length = 12)
    private String dni;
    @Column(length = 15)
    private String telefono;
    private boolean activo;

    @OneToOne
    @JoinColumn(name = "cod_usuario")
    private Usuario usuario;

    @OneToMany(mappedBy = "alumno")
    private List<Inscripcion> inscripciones;
}
