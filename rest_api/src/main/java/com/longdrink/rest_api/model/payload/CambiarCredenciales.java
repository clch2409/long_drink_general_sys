package com.longdrink.rest_api.model.payload;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class CambiarCredenciales {
    private String contrasenaAntigua;
    private String nuevaContrasena;
    private String emailAntiguo;
    private String emailNuevo;

    public CambiarCredenciales(){}

    public CambiarCredenciales(String contrasenaAntigua, String nuevaContrasena, String emailAntiguo, String emailNuevo) {
        this.contrasenaAntigua = contrasenaAntigua;
        this.nuevaContrasena = nuevaContrasena;
        this.emailAntiguo = emailAntiguo;
        this.emailNuevo = emailNuevo;
    }

    @JsonIgnore
    public boolean validarDatos(){
        return nuevaContrasena.length() >= 5 && nuevaContrasena.length() <=30 && emailAntiguo.length() <= 50 && emailNuevo.length() <=50;
    }

    @JsonIgnore
    public CambiarCredenciales limpiarDatos(){
        try{
            return new CambiarCredenciales(this.contrasenaAntigua,this.nuevaContrasena,this.emailAntiguo.trim().toUpperCase(),this.emailNuevo.trim().toUpperCase());
        }
        catch(Exception ex){ return null; }
    }

    public String getContrasenaAntigua() {
        return contrasenaAntigua;
    }

    public void setContrasenaAntigua(String contrasenaAntigua) {
        this.contrasenaAntigua = contrasenaAntigua;
    }

    public String getNuevaContrasena() {
        return nuevaContrasena;
    }

    public void setNuevaContrasena(String nuevaContrasena) {
        this.nuevaContrasena = nuevaContrasena;
    }

    public String getEmailAntiguo() {
        return emailAntiguo;
    }

    public void setEmailAntiguo(String emailAntiguo) {
        this.emailAntiguo = emailAntiguo;
    }

    public String getEmailNuevo() {
        return emailNuevo;
    }

    public void setEmailNuevo(String emailNuevo) {
        this.emailNuevo = emailNuevo;
    }
}
