package com.longdrink.rest_api.model.payload;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class InsertInscripcion {
    private Long codAlumno;
    private Long codSeccion;

    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT-5")
    private Date fechaInscripcion;

    public InsertInscripcion(){}

    public InsertInscripcion(Long codAlumno, Long codSeccion, Date fechaInscripcion) {
        this.codAlumno = codAlumno;
        this.codSeccion = codSeccion;
        this.fechaInscripcion = fechaInscripcion;
    }

    public Long getCodAlumno() {
        return codAlumno;
    }

    public void setCodAlumno(Long codAlumno) {
        this.codAlumno = codAlumno;
    }

    public Long getCodSeccion() {
        return codSeccion;
    }

    public void setCodSeccion(Long codSeccion) {
        this.codSeccion = codSeccion;
    }

    public Date getFechaInscripcion() {
        return fechaInscripcion;
    }

    public void setFechaInscripcion(Date fechaInscripcion) {
        this.fechaInscripcion = fechaInscripcion;
    }
}
