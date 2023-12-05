package com.longdrink.rest_api.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity(name = "Seccion")
@Table(name = "seccion")
public class Seccion implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_seccion")
    @JsonProperty("codSeccion")
    private Long codSeccion;
    @Column(name = "nombre",length = 30)
    @JsonProperty("nombre")
    private String nombre;
    @Column(name = "fecha_inicio")
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT-5")
    @JsonProperty("fechaInicio")
    private Date fechaInicio;

    @Column(name = "fecha_final")
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT-5")
    @JsonProperty("fechaFinal")
    private Date fechaFinal;
    private boolean estado;
    private int maxAlumnos;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "cod_curso")
    private Curso curso;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "cod_turno")
    private Turno turno;

    @JsonBackReference
    @OneToMany(mappedBy = "seccion")
    private List<Inscripcion> inscripciones;

    public Seccion(){}

    public Seccion(Long codSeccion, String nombre, Date fechaInicio, Date fechaFinal, boolean estado, int maxAlumnos, Curso curso, Turno turno, List<Inscripcion> inscripciones) {
        this.codSeccion = codSeccion;
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        this.fechaFinal = fechaFinal;
        this.estado = estado;
        this.maxAlumnos = maxAlumnos;
        this.curso = curso;
        this.turno = turno;
        this.inscripciones = inscripciones;
    }

    public Seccion(Long codSeccion, String nombre, Date fechaInicio, Date fechaFinal, boolean estado, int maxAlumnos) {
        this.codSeccion = codSeccion;
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        this.fechaFinal = fechaFinal;
        this.estado = estado;
        this.maxAlumnos = maxAlumnos;
    }

    public Long getCodSeccion() {
        return codSeccion;
    }

    public void setCodSeccion(Long codSeccion) {
        this.codSeccion = codSeccion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public int getMaxAlumnos() {
        return maxAlumnos;
    }

    public void setMaxAlumnos(int maxAlumnos) {
        this.maxAlumnos = maxAlumnos;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public Turno getTurno() {
        return turno;
    }

    public void setTurno(Turno turno) {
        this.turno = turno;
    }

    public List<Inscripcion> getInscripciones() {
        return inscripciones;
    }

    public void setInscripciones(List<Inscripcion> inscripciones) {
        this.inscripciones = inscripciones;
    }
}
