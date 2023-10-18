package com.longdrink.rest_api.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;

@Entity(name = "Inscripcion")
@Table(name = "inscripcion")
public class Inscripcion implements Serializable {
    @EmbeddedId
    private InscripcionPk inscripcionPk;

    @Column(name = "fecha_inicio")
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern="yyyy-MM-dd")
    @JsonProperty("fechaInicio")
    private Date fechaInicio;

    @Column(name = "fecha_final")
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern="yyyy-MM-dd")
    @JsonProperty("fechaFinal")
    private Date fechaFinal;

    @Column(name = "fecha_inscripcion")
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern="yyyy-MM-dd")
    @JsonProperty("fechaInscripcion")
    private Date fechaInscripcion;

    @Column(name = "fecha_terminado")
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern="yyyy-MM-dd")
    @JsonProperty("fechaTerminado")
    private Date fechaTerminado;

    private boolean estado;

    @MapsId("codAlumno")
    @ManyToOne
    @JoinColumn(name = "cod_alumno")
    private Alumno alumno;

    @MapsId("codCurso")
    @ManyToOne
    @JoinColumn(name = "cod_curso")
    private Curso curso;

    public Inscripcion(){}

    public Inscripcion(InscripcionPk inscripcionPk, Date fechaInicio, Date fechaFinal, Date fechaInscripcion, Date fechaTerminado, boolean estado, Alumno alumno, Curso curso) {
        this.inscripcionPk = inscripcionPk;
        this.fechaInicio = fechaInicio;
        this.fechaFinal = fechaFinal;
        this.fechaInscripcion = fechaInscripcion;
        this.fechaTerminado = fechaTerminado;
        this.estado = estado;
        this.alumno = alumno;
        this.curso = curso;
    }

    public Inscripcion(Date fechaInicio, Date fechaFinal, Date fechaInscripcion, Date fechaTerminado, boolean estado, Alumno alumno, Curso curso) {
        this.fechaInicio = fechaInicio;
        this.fechaFinal = fechaFinal;
        this.fechaInscripcion = fechaInscripcion;
        this.fechaTerminado = fechaTerminado;
        this.estado = estado;
        this.alumno = alumno;
        this.curso = curso;
    }

    public InscripcionPk getInscripcionPk() {
        return inscripcionPk;
    }

    public void setInscripcionPk(InscripcionPk inscripcionPk) {
        this.inscripcionPk = inscripcionPk;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public Date getFechaInscripcion() {
        return fechaInscripcion;
    }

    public void setFechaInscripcion(Date fechaInscripcion) {
        this.fechaInscripcion = fechaInscripcion;
    }

    public Date getFechaTerminado() {
        return fechaTerminado;
    }

    public void setFechaTerminado(Date fechaTerminado) {
        this.fechaTerminado = fechaTerminado;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }
}
