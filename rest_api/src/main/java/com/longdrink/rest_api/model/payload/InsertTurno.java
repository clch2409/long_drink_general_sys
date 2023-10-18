package com.longdrink.rest_api.model.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Date;

public class InsertTurno implements Serializable {
    private Long codTurno;
    private String nombre;
    @JsonProperty("horaInicio")
    @JsonFormat(pattern = "HH:mm:ss",timezone = "GMT-5")
    private Date horaInicio;
    @JsonProperty("horaFin")
    @JsonFormat(pattern = "HH:mm:ss",timezone = "GMT-5")
    private Date horaFin;

    public InsertTurno(){}

    public InsertTurno(Long codTurno, String nombre, Date horaInicio, Date horaFin) {
        this.codTurno = codTurno;
        this.nombre = nombre;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }

    public Long getCodTurno() {
        return codTurno;
    }

    public void setCodTurno(Long codTurno) {
        this.codTurno = codTurno;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Date horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Date getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(Date horaFin) {
        this.horaFin = horaFin;
    }
}
