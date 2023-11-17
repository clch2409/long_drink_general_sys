package com.longdrink.rest_api.model.payload;

import com.longdrink.rest_api.model.Curso;


import java.io.Serializable;
import java.util.List;

public class TemaCurso implements Serializable {
    private Long codTema;
    private String nombre;
    private String ficha;
    private List<Curso> cursos;

    public TemaCurso(){}

    public TemaCurso(Long codTema, String nombre, String ficha, List<Curso> cursos) {
        this.codTema = codTema;
        this.nombre = nombre;
        this.ficha = ficha;
        this.cursos = cursos;
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
