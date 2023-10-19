package com.longdrink.rest_api.model.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

public class RegistroDocente {
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String dni;
    private String telefono;
    @JsonIgnore
    private String nombreUsuario;
    private String email;
    private String contrasena;
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT-5")
    private Date fechaContratacion;

    public RegistroDocente(){}

    public RegistroDocente(String nombre, String apellidoPaterno, String apellidoMaterno, String dni, String telefono, String nombreUsuario, String email, String contrasena, Date fechaContratacion) {
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.dni = dni;
        this.telefono = telefono;
        this.nombreUsuario = nombreUsuario;
        this.email = email;
        this.contrasena = contrasena;
        this.fechaContratacion = fechaContratacion;
    }

    @JsonIgnore
    public String generarNombreUsuario(){
        try{
            return this.nombre.trim().toUpperCase().substring(0,1) +
                    this.apellidoPaterno.trim().toUpperCase().substring(0,1) +
                    this.apellidoMaterno.trim().toUpperCase().substring(0,1) +
                    this.dni;
        }
        catch(Exception ex){ return ""; }
    }

    @JsonIgnore
    public RegistroDocente limpiarDatos(){
        try{
            return new RegistroDocente(this.nombre.trim().toUpperCase(),this.apellidoPaterno.trim().toUpperCase(),
                    this.apellidoMaterno.trim().toUpperCase(),this.dni.trim(),this.telefono.trim(),
                    this.generarNombreUsuario(),this.email.trim().toUpperCase(),this.contrasena,
                    this.fechaContratacion);
        }
        catch(Exception ex){ return null; }
    }
    @JsonIgnore
    public boolean validarDatos(){
        return this.nombre.length() >= 1 && this.nombre.length() <= 50 &&
                this.apellidoPaterno.length() >= 1 && this.apellidoPaterno.length() <= 25 &&
                this.apellidoMaterno.length() >= 1 && this.apellidoMaterno.length() <= 25 &&
                this.dni.length() >= 8 && this.dni.length() <= 12 && this.telefono.length() >= 9 &&
                this.telefono.length() <= 15 && this.nombreUsuario.length() <= 50 && this.email.length() <= 50 &&
                this.contrasena.length() >= 5 && this.contrasena.length() <=30;
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

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public Date getFechaContratacion() {
        return fechaContratacion;
    }

    public void setFechaContratacion(Date fechaContratacion) {
        this.fechaContratacion = fechaContratacion;
    }
}
