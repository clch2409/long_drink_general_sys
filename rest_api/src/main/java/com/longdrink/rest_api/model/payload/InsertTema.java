package com.longdrink.rest_api.model.payload;

import java.io.Serializable;

public class InsertTema implements Serializable {
    private Long codTema;
    private String nombre;
    private String ficha;

    public InsertTema(){}

    public InsertTema(Long codTema, String nombre, String ficha) {
        this.codTema = codTema;
        this.nombre = nombre;
        this.ficha = ficha;
    }

    public Long getCodTema() {
        return codTema;
    }

    public void setCodTema(Long codTema) {
        this.codTema = codTema;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFicha() {
        return ficha;
    }

    public void setFicha(String ficha) {
        this.ficha = ficha;
    }
}
