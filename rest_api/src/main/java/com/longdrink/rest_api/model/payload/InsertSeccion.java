package com.longdrink.rest_api.model.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.Date;

public class InsertSeccion implements Serializable {

    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT-5")
    private Date fechaInicio;
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT-5")
    private Date fechaFinal; //**Calcular en front segun fecha de inicio seleccionada.
    private boolean estado;
    private int maxAlumnos;
    private Long codCurso;
    private Long codProfesor;
    private Long codTurno;

    @JsonIgnore
    public boolean validarDatos(){
        return this.maxAlumnos >= 1 && this.maxAlumnos <= 30 &&
                this.fechaFinal.after(this.fechaInicio);
    }
    public InsertSeccion(){}

    public InsertSeccion(Date fechaInicio, Date fechaFinal, boolean estado, int maxAlumnos, Long codCurso, Long codProfesor, Long codTurno) {

        this.fechaInicio = fechaInicio;
        this.fechaFinal = fechaFinal;
        this.estado = estado;
        this.maxAlumnos = maxAlumnos;
        this.codCurso = codCurso;
        this.codProfesor = codProfesor;
        this.codTurno = codTurno;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public int getMaxAlumnos() {
        return maxAlumnos;
    }

    public void setMaxAlumnos(int maxAlumnos) {
        this.maxAlumnos = maxAlumnos;
    }

    public Long getCodCurso() {
        return codCurso;
    }

    public void setCodCurso(Long codCurso) {
        this.codCurso = codCurso;
    }

    public Long getCodProfesor() {
        return codProfesor;
    }

    public void setCodProfesor(Long codProfesor) {
        this.codProfesor = codProfesor;
    }

    public Long getCodTurno() {
        return codTurno;
    }

    public void setCodTurno(Long codTurno) {
        this.codTurno = codTurno;
    }
}
