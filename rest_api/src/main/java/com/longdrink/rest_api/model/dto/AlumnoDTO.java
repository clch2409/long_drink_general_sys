package com.longdrink.rest_api.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.longdrink.rest_api.model.Alumno;

import java.io.Serializable;

public class AlumnoDTO implements Serializable {
    private Long codAlumno;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String dni;
    private String telefono;
    private boolean activo;

    public AlumnoDTO(){}
    public AlumnoDTO(Long codAlumno, String nombre, String apellidoPaterno, String apellidoMaterno, String dni, String telefono, boolean activo) {
        this.codAlumno = codAlumno;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.dni = dni;
        this.telefono = telefono;
        this.activo = activo;
    }

    @JsonIgnore
    public void setProperties(Alumno a){
        this.codAlumno = a.getCodAlumno();
        this.nombre = a.getNombre();
        this.apellidoPaterno = a.getApellidoPaterno();
        this.apellidoMaterno = a.getApellidoMaterno();
        this.dni = a.getDni();
        this.telefono = a.getTelefono();
        this.activo = a.isActivo();
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
}
