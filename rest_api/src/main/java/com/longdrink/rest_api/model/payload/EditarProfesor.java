package com.longdrink.rest_api.model.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

public class EditarProfesor {
    private Long codProfesor;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String telefono;
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT-5")
    private Date fechaContratacion;
    private boolean activo;

    @JsonIgnore
    public EditarProfesor limpiarDatos(){
        try{
            return new EditarProfesor(this.codProfesor,this.nombre.trim().toUpperCase(),
                    this.apellidoPaterno.trim().toUpperCase(),
                    this.apellidoMaterno.trim().toUpperCase(),
                    this.telefono.trim(),this.fechaContratacion,
                    this.activo);
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

    public EditarProfesor(){}

    public EditarProfesor(Long codProfesor, String nombre, String apellidoPaterno, String apellidoMaterno, String telefono, Date fechaContratacion, boolean activo) {
        this.codProfesor = codProfesor;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.telefono = telefono;
        this.fechaContratacion = fechaContratacion;
        this.activo = activo;
    }

    public Long getCodProfesor() {
        return codProfesor;
    }

    public void setCodProfesor(Long codProfesor) {
        this.codProfesor = codProfesor;
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

    public Date getFechaContratacion() {
        return fechaContratacion;
    }

    public void setFechaContratacion(Date fechaContratacion) {
        this.fechaContratacion = fechaContratacion;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
