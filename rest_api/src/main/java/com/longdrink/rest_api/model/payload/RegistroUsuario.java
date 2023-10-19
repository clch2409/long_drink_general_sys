package com.longdrink.rest_api.model.payload;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class RegistroUsuario {
    @JsonIgnore
    private String nombreUsuario;
    private String email;
    private String contrasena;
    private String rol;

    public RegistroUsuario(){}

    public RegistroUsuario(String nombreUsuario, String email, String contrasena, String rol) {
        this.nombreUsuario = nombreUsuario;
        this.email = email;
        this.contrasena = contrasena;
        this.rol = rol;
    }

    @JsonIgnore
    public String generarNombreUsuario(){
        try{
            return "ADM_"+this.email.trim().toUpperCase().
                    substring(0,this.email.trim().toUpperCase().indexOf('@'));
        }
        catch(Exception ex){ return ""; }
    }

    @JsonIgnore
    public RegistroUsuario limpiarDatos(){
        try{
            return new RegistroUsuario(this.generarNombreUsuario(),this.email.trim().toUpperCase(),this.contrasena,this.rol.trim().toUpperCase());
        }
        catch(Exception ex){ return null; }
    }

    @JsonIgnore
    public boolean validarDatos(){
        return this.nombreUsuario.length() >=1 && this.nombreUsuario.length() <=50 &&
                this.email.length() >=1 && this.email.length() <=50 && this.contrasena.length() >=5 && this.contrasena.length() <=30;
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

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
