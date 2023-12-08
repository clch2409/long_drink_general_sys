package com.longdrink.rest_api.model.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.sql.Insert;

import java.util.Date;

public class InsertCurso {
    private String nombre;
    private String descripcion;
    private String frecuencia;
    private String imagen;
    private byte duracion;
    private double mensualidad;
    private boolean visibilidad;
    private Long codProfesor;
    private Long codTurno;
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT-5")
    private Date fechaInicio;
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT-5")
    private Date fechaFinal;
    private int maxAlumnos;

    @JsonIgnore
    public InsertCurso limpiarDatos(){
        try {
            nombre = nombre.trim();
            descripcion = descripcion.trim();
            frecuencia = frecuencia.trim();
            return new InsertCurso(StringUtils.capitalize(nombre), StringUtils.capitalize(descripcion),
                    StringUtils.capitalize(frecuencia), imagen.trim(),
                    duracion, mensualidad, visibilidad, codProfesor, codTurno, fechaInicio, fechaFinal, maxAlumnos);
        }
        catch(Exception ex){ return null; }
    }

    @JsonIgnore
    public boolean validarDatos(){
        return this.nombre.length() >= 1 && this.nombre.length() <= 100 &&
                this.descripcion.length() >= 1 && this.descripcion.length() <= 150 &&
                this.frecuencia.length() >= 1 && this.frecuencia.length() <= 50 &&
                this.imagen.length() >= 1 && this.imagen.length() <= 255 &&
                this.duracion >= 1 && this.duracion <= 50 &&
                this.mensualidad >= 1 && this.mensualidad <= 1000 &&
                this.codProfesor > 0L && this.codTurno > 0L &&
                this.maxAlumnos >= 1 && this.maxAlumnos <=30;
    }

    public InsertCurso(){}

    public InsertCurso(String nombre, String descripcion, String frecuencia, String imagen, byte duracion, double mensualidad, boolean visibilidad, Long codProfesor, Long codTurno, Date fechaInicio, Date fechaFinal, int maxAlumnos) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.frecuencia = frecuencia;
        this.imagen = imagen;
        this.duracion = duracion;
        this.mensualidad = mensualidad;
        this.visibilidad = visibilidad;
        this.codProfesor = codProfesor;
        this.codTurno = codTurno;
        this.fechaInicio = fechaInicio;
        this.fechaFinal = fechaFinal;
        this.maxAlumnos = maxAlumnos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    public byte getDuracion() {
        return duracion;
    }

    public void setDuracion(byte duracion) {
        this.duracion = duracion;
    }

    public double getMensualidad() {
        return mensualidad;
    }

    public void setMensualidad(double mensualidad) {
        this.mensualidad = mensualidad;
    }

    public boolean isVisibilidad() {
        return visibilidad;
    }

    public void setVisibilidad(boolean visibilidad) {
        this.visibilidad = visibilidad;
    }

    public Long getCodProfesor() {
        return codProfesor;
    }

    public void setCodProfesor(Long codProfesor) {
        this.codProfesor = codProfesor;
    }

    public Long getCodTurno() {
        return codTurno;
    }

    public void setCodTurno(Long codTurno) {
        this.codTurno = codTurno;
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
}
