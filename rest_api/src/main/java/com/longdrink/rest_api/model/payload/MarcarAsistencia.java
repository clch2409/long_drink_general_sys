package com.longdrink.rest_api.model.payload;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

public class MarcarAsistencia implements Serializable {
    private Long codInscripcion;
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT-5")
    private Date fechaAsistencia;
    @JsonFormat(pattern="HH:mm",timezone = "GMT-5")
    private Date horaLlegada;
    private boolean estado;

    public MarcarAsistencia(){}

    public MarcarAsistencia(Long codInscripcion, Date fechaAsistencia, Date horaLlegada, boolean estado) {
        this.codInscripcion = codInscripcion;
        this.fechaAsistencia = fechaAsistencia;
        this.horaLlegada = horaLlegada;
        this.estado = estado;
    }

    public Long getCodInscripcion() {
        return codInscripcion;
    }

    public void setCodInscripcion(Long codInscripcion) {
        this.codInscripcion = codInscripcion;
    }

    public Date getFechaAsistencia() {
        return fechaAsistencia;
    }

    public void setFechaAsistencia(Date fechaAsistencia) {
        this.fechaAsistencia = fechaAsistencia;
    }

    public Date getHoraLlegada() {
        return horaLlegada;
    }

    public void setHoraLlegada(Date horaLlegada) {
        this.horaLlegada = horaLlegada;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
}
