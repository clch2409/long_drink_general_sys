package com.longdrink.rest_api.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;


import java.io.Serializable;
import java.util.List;


@Entity(name = "Tema")
@Table(name = "tema")
public class Tema implements Serializable {
    @Id
    @Column(name = "cod_tema")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("codTema")
    private Long codTema;
    @Column(length = 30)
    private String nombre;
    private String ficha; //Localización de archivo en sistema de archivos.

    @JsonBackReference
    @ManyToMany(mappedBy = "temas")
    private List<Curso> cursos;

    public Tema(){}

    public Tema(Long codTema, String nombre, String ficha, List<Curso> cursos) {
        this.codTema = codTema;
        this.nombre = nombre;
        this.ficha = ficha;
        this.cursos = cursos;
    }

    public Tema(Long codTema, String nombre, String ficha) {
        this.codTema = codTema;
        this.nombre = nombre;
        this.ficha = ficha;
    }

    public Long getCodTema() {
        return codTema;
    }

    public void setCodTema(Long codTema) {
        this.codTema = codTema;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFicha() {
        return ficha;
    }

    public void setFicha(String ficha) {
        this.ficha = ficha;
    }

    public List<Curso> getCursos() {
        return cursos;
    }

    public void setCursos(List<Curso> cursos) {
        this.cursos = cursos;
    }
}
