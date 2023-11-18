package com.longdrink.rest_api.model.payload;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class EditarAlumno {
    private Long codAlumno;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String telefono;
    private boolean activo;

    @JsonIgnore
    public EditarAlumno limpiarDatos(){
        try{
            return new EditarAlumno(this.codAlumno,this.nombre.trim().toUpperCase(),
                    this.apellidoPaterno.trim().toUpperCase(),
                    this.apellidoMaterno.trim().toUpperCase(),
                    this.telefono.trim(),this.activo);
        }
        catch(Exception ex){ return null; }
    }

    @JsonIgnore
    public boolean validarDatos(){
        return this.nombre.length() >= 1 && this.nombre.length() <= 50 &&
                this.apellidoPaterno.length() >= 1 && this.apellidoPaterno.length() <= 25 &&
                this.apellidoMaterno.length() >= 1 && this.apellidoMaterno.length() <= 25 &&
                this.telefono.length() >= 9;
    }
    public EditarAlumno(){}

    public EditarAlumno(Long codAlumno, String nombre, String apellidoPaterno, String apellidoMaterno, String telefono, boolean activo) {
        this.codAlumno = codAlumno;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.telefono = telefono;
        this.activo = activo;
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
