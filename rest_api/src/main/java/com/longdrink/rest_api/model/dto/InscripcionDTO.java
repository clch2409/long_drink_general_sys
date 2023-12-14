package com.longdrink.rest_api.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.longdrink.rest_api.model.Alumno;
import com.longdrink.rest_api.model.Asistencia;
import com.longdrink.rest_api.model.Inscripcion;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class InscripcionDTO implements Serializable {
    private Long codInscripcion;
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT-5")
    private Date fechaInscripcion;
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT-5")
    private Date fechaTerminado;
    private boolean estado;
    private AlumnoDTO alumno;
    private SeccionDTO seccion;
    private List<Asistencia> asistencias;

    public InscripcionDTO(){}

    public InscripcionDTO(Long codInscripcion, Date fechaInscripcion, Date fechaTerminado, boolean estado, AlumnoDTO alumno, SeccionDTO seccion, List<Asistencia> asistencias) {
        this.codInscripcion = codInscripcion;
        this.fechaInscripcion = fechaInscripcion;
        this.fechaTerminado = fechaTerminado;
        this.estado = estado;
        this.alumno = alumno;
        this.seccion = seccion;
        this.asistencias = asistencias;
    }

    @JsonIgnore
    public void setProperties(Inscripcion i){
        AlumnoDTO a = new AlumnoDTO();
        SeccionDTO s = new SeccionDTO();
        a.setProperties(i.getAlumno());
        s.setProperties(i.getSeccion());
        this.codInscripcion = i.getCodInscripcion();
        this.fechaInscripcion = i.getFechaInscripcion();
        this.fechaTerminado = i.getFechaTerminado();
        this.estado = i.isEstado();
        this.alumno = a;
        this.seccion = s;
        this.asistencias = i.getAsistencias();
    }

    public Long getCodInscripcion() {
        return codInscripcion;
    }

    public void setCodInscripcion(Long codInscripcion) {
        this.codInscripcion = codInscripcion;
    }

    public Date getFechaInscripcion() {
        return fechaInscripcion;
    }

    public void setFechaInscripcion(Date fechaInscripcion) {
        this.fechaInscripcion = fechaInscripcion;
    }

    public Date getFechaTerminado() {
        return fechaTerminado;
    }

    public void setFechaTerminado(Date fechaTerminado) {
        this.fechaTerminado = fechaTerminado;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public AlumnoDTO getAlumno() {
        return alumno;
    }

    public void setAlumno(AlumnoDTO alumno) {
        this.alumno = alumno;
    }

    public SeccionDTO getSeccion() {
        return seccion;
    }

    public void setSeccion(SeccionDTO seccion) {
        this.seccion = seccion;
    }

    public List<Asistencia> getAsistencias() {
        return asistencias;
    }

    public void setAsistencias(List<Asistencia> asistencias) {
        this.asistencias = asistencias;
    }
}
