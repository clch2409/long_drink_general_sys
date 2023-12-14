package com.longdrink.rest_api.model.payload;

import java.io.Serializable;

public class Mensaje implements Serializable{
    private String mensaje;
    private int estado;

    public Mensaje(){}

    public Mensaje(String mensaje, int estado) {
        this.mensaje = mensaje;
        this.estado = estado;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
    
}
