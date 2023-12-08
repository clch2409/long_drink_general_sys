package com.longdrink.rest_api.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.longdrink.rest_api.model.Seccion;

import java.io.Serializable;
import java.util.Date;

public class SeccionDTO implements Serializable {
    private Long codSeccion;
    private String nombre;
    private Date fechaInicio;
    private Date fechaFinal;
    private int maxAlumnos;
    private boolean estado;

    public SeccionDTO(){}

    public SeccionDTO(Long codSeccion, String nombre, Date fechaInicio, Date fechaFinal, int maxAlumnos, boolean estado) {
        this.codSeccion = codSeccion;
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        this.fechaFinal = fechaFinal;
        this.maxAlumnos = maxAlumnos;
        this.estado = estado;
    }

    @JsonIgnore
    public void setProperties(Seccion s){
        this.codSeccion = s.getCodSeccion();
        this.nombre = s.getNombre();
        this.fechaInicio = s.getFechaInicio();
        this.fechaFinal = s.getFechaFinal();
        this.maxAlumnos = s.getMaxAlumnos();
        this.estado = s.isEstado();
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

    public int getMaxAlumnos() {
        return maxAlumnos;
    }

    public void setMaxAlumnos(int maxAlumnos) {
        this.maxAlumnos = maxAlumnos;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
}
