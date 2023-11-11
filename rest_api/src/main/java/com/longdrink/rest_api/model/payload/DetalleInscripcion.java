package com.longdrink.rest_api.model.payload;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class DetalleInscripcion {
    /*
    * Deberias ignorarme totalmente de ahora en adelante, anda. Cierra la pestaña del editor...
    * Nada importante que ver aqui.
    * */
    //Atributos curso
    private Long codCurso;
    private String descripcion;
    private double mensualidad;
    private byte duracion;
    private byte cantidadAlumnos;
    private boolean visibilidad;
    private String imagen;
    private String frecuencia;
    //Atributos alumno
    private Long codAlumno;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String dni;
    private String telefono;
    private boolean activo;
    //Atributos inscripcion
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT-5")
    private Date fechaInicio;
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT-5")
    private Date fechaFinal;
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT-5")
    private Date fechaInscripcion;
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT-5")
    private Date fechaTerminado;
    private boolean estado;

    public DetalleInscripcion(){}

    public DetalleInscripcion(Long codCurso, String descripcion, double mensualidad, byte duracion, byte cantidadAlumnos, boolean visibilidad, String imagen, String frecuencia, Long codAlumno, String nombre, String apellidoPaterno, String apellidoMaterno, String dni, String telefono, boolean activo, Date fechaInicio, Date fechaFinal, Date fechaInscripcion, Date fechaTerminado, boolean estado) {
        this.codCurso = codCurso;
        this.descripcion = descripcion;
        this.mensualidad = mensualidad;
        this.duracion = duracion;
        this.cantidadAlumnos = cantidadAlumnos;
        this.visibilidad = visibilidad;
        this.imagen = imagen;
        this.frecuencia = frecuencia;
        this.codAlumno = codAlumno;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.dni = dni;
        this.telefono = telefono;
        this.activo = activo;
        this.fechaInicio = fechaInicio;
        this.fechaFinal = fechaFinal;
        this.fechaInscripcion = fechaInscripcion;
        this.fechaTerminado = fechaTerminado;
        this.estado = estado;
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

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(String frecuencia) {
        this.frecuencia = frecuencia;
    }

    public Long getCodAlumno() {
        return codAlumno;
    }

    public void setCodAlumno(Long codAlumno) {
        this.codAlumno = codAlumno;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
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
}
