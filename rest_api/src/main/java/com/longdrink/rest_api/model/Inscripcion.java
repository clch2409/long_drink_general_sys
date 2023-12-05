package com.longdrink.rest_api.model;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity(name = "Inscripcion")
@Table(name = "inscripcion")
public class Inscripcion implements Serializable {
    @Id
    @Column(name = "cod_inscripcion")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("codInscripcion")
    private Long codInscripcion;

    @Column(name = "fecha_inscripcion")
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT-5")
    @JsonProperty("fechaInscripcion")
    private Date fechaInscripcion;

    @Column(name = "fecha_terminado")
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT-5")
    @JsonProperty("fechaTerminado")
    private Date fechaTerminado;

    private boolean estado;

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "codAlumno")
    @JsonIdentityReference(alwaysAsId = true)
    //@JsonManagedReference
    //@MapsId("codAlumno") ->> TODO.
    @ManyToOne
    @JoinColumn(name = "cod_alumno")
    private Alumno alumno;

    @OneToMany(mappedBy = "inscripcion")
    private List<Asistencia> asistencias;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "cod_seccion")
    private Seccion seccion;

    public Inscripcion(){}

    public Inscripcion(Long codInscripcion, Date fechaInscripcion, Date fechaTerminado, boolean estado, Alumno alumno, List<Asistencia> asistencias, Seccion seccion) {
        this.codInscripcion = codInscripcion;
        this.fechaInscripcion = fechaInscripcion;
        this.fechaTerminado = fechaTerminado;
        this.estado = estado;
        this.alumno = alumno;
        this.asistencias = asistencias;
        this.seccion = seccion;
    }

    public Inscripcion(Long codInscripcion, Date fechaInscripcion, Date fechaTerminado, boolean estado, Alumno alumno, Seccion seccion) {
        this.codInscripcion = codInscripcion;
        this.fechaInscripcion = fechaInscripcion;
        this.fechaTerminado = fechaTerminado;
        this.estado = estado;
        this.alumno = alumno;
        this.seccion = seccion;
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

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }


    public List<Asistencia> getAsistencias() {
        return asistencias;
    }

    public void setAsistencias(List<Asistencia> asistencias) {
        this.asistencias = asistencias;
    }

    public Seccion getSeccion() {
        return seccion;
    }

    public void setSeccion(Seccion seccion) {
        this.seccion = seccion;
    }
}
