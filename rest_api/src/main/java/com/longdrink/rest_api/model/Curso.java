package com.longdrink.rest_api.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

@Entity(name = "Curso")
@Table(name = "curso")
public class Curso implements Serializable {
    @Id
    @Column(name = "cod_curso")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("codCurso")
    private Long codCurso;
    @Column(length = 100)
    private String nombre;
    @Column(length = 150)
    private String descripcion;

    private double mensualidad;

    private byte duracion;

    @Column(name = "cantidad_alumnos")
    @JsonProperty("cantidadAlumnos")
    private byte cantidadAlumnos;

    private boolean visibilidad;

    @Column(length = 50)
    private String frecuencia;

    private String imagen;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "cod_profesor")
    private Profesor profesor;

    @JsonManagedReference
    @ManyToMany
    @JoinTable(name = "curso_turno",
            joinColumns = @JoinColumn(name = "cod_curso"),
            inverseJoinColumns = @JoinColumn(name = "cod_turno"))
    private List<Turno> turnos;

    @JsonManagedReference
    @ManyToMany
    @JoinTable(name = "curso_tema",
            joinColumns = @JoinColumn(name = "cod_curso"),
            inverseJoinColumns = @JoinColumn(name = "cod_tema"))
    private List<Tema> temas;

    @JsonBackReference
    @JsonIgnore
    @OneToMany(mappedBy = "curso")
    private List<Inscripcion> inscripciones;

    public Curso(){}

    public Curso(Long codCurso, String nombre, String descripcion, double mensualidad, byte duracion, byte cantidadAlumnos, boolean visibilidad, String frecuencia, String imagen, Profesor profesor, List<Turno> turnos, List<Tema> temas, List<Inscripcion> inscripciones) {
        this.codCurso = codCurso;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.mensualidad = mensualidad;
        this.duracion = duracion;
        this.cantidadAlumnos = cantidadAlumnos;
        this.visibilidad = visibilidad;
        this.frecuencia = frecuencia;
        this.imagen = imagen;
        this.profesor = profesor;
        this.turnos = turnos;
        this.temas = temas;
        this.inscripciones = inscripciones;
    }

    public Curso(Long codCurso, String nombre, String descripcion, double mensualidad, byte duracion, byte cantidadAlumnos, boolean visibilidad, String frecuencia, String imagen, Profesor profesor, List<Turno> turnos) {
        this.codCurso = codCurso;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.mensualidad = mensualidad;
        this.duracion = duracion;
        this.cantidadAlumnos = cantidadAlumnos;
        this.visibilidad = visibilidad;
        this.frecuencia = frecuencia;
        this.imagen = imagen;
        this.profesor = profesor;
        this.turnos = turnos;
    }

    public Long getCodCurso() {
        return codCurso;
    }

    public void setCodCurso(Long codCurso) {
        this.codCurso = codCurso;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getMensualidad() {
        return mensualidad;
    }

    public void setMensualidad(double mensualidad) {
        this.mensualidad = mensualidad;
    }

    public byte getDuracion() {
        return duracion;
    }

    public void setDuracion(byte duracion) {
        this.duracion = duracion;
    }

    public byte getCantidadAlumnos() {
        return cantidadAlumnos;
    }

    public void setCantidadAlumnos(byte cantidadAlumnos) {
        this.cantidadAlumnos = cantidadAlumnos;
    }

    public boolean isVisibilidad() {
        return visibilidad;
    }

    public void setVisibilidad(boolean visibilidad) {
        this.visibilidad = visibilidad;
    }

    public String getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(String frecuencia) {
        this.frecuencia = frecuencia;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    public List<Turno> getTurnos() {
        return turnos;
    }

    public void setTurnos(List<Turno> turnos) {
        this.turnos = turnos;
    }

    public List<Tema> getTemas() {
        return temas;
    }

    public void setTemas(List<Tema> temas) {
        this.temas = temas;
    }

    public List<Inscripcion> getInscripciones() {
        return inscripciones;
    }

    public void setInscripciones(List<Inscripcion> inscripciones) {
        this.inscripciones = inscripciones;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
