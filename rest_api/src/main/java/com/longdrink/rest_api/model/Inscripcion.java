package com.longdrink.rest_api.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


@Data
@NoArgsConstructor
@Entity(name = "Inscripcion")
@Table(name = "inscripcion")
public class Inscripcion implements Serializable {
    @EmbeddedId
    private InscripcionPk inscripcionPk;

    @MapsId("codAlumno")
    @ManyToOne
    @JoinColumn(name = "cod_alumno")
    private Alumno alumno;

    @MapsId("codCurso")
    @ManyToOne
    @JoinColumn(name = "cod_curso")
    private Curso curso;

    @Column(name = "fecha_inicio")
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;
    @Column(name = "fecha_final")
    @Temporal(TemporalType.DATE)
    private Date fechaFinal;
    @Column(name = "fecha_inscripcion")
    @Temporal(TemporalType.DATE)
    private Date fechaInscripcion;
    @Column(name = "fecha_terminado")
    @Temporal(TemporalType.DATE)
    private Date fechaTerminado;
    private boolean estado;
}
