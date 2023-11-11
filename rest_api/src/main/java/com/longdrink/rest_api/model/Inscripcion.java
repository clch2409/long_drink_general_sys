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

    @Column(name = "fecha_inicio")
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT-5")
    @JsonProperty("fechaInicio")
    private Date fechaInicio;

    @Column(name = "fecha_final")
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT-5")
    @JsonProperty("fechaFinal")
    private Date fechaFinal;

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
    @MapsId("codAlumno")
    @ManyToOne
    @JoinColumn(name = "cod_alumno")
    private Alumno alumno;

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "codCurso")
    @JsonIdentityReference(alwaysAsId = true)
    @JsonManagedReference
    @MapsId("codCurso")
    @ManyToOne
    @JoinColumn(name = "cod_curso")
    private Curso curso;


    @JsonManagedReference
    @MapsId("codTurno")
    @ManyToOne
    @JoinColumn(name = "cod_turno")
    private Turno turno;

    @OneToMany(mappedBy = "inscripcion")
    private List<Asistencia> asistencias;

    public Inscripcion(){}

    public Inscripcion(Long codInscripcion, Date fechaInicio, Date fechaFinal, Date fechaInscripcion, Date fechaTerminado, boolean estado, Alumno alumno, Curso curso, Turno turno, List<Asistencia> asistencias) {
        this.codInscripcion = codInscripcion;
        this.fechaInicio = fechaInicio;
        this.fechaFinal = fechaFinal;
        this.fechaInscripcion = fechaInscripcion;
        this.fechaTerminado = fechaTerminado;
        this.estado = estado;
        this.alumno = alumno;
        this.curso = curso;
        this.turno = turno;
        this.asistencias = asistencias;
    }

    public Inscripcion(Date fechaInicio, Date fechaFinal, Date fechaInscripcion, Date fechaTerminado, boolean estado, Alumno alumno, Curso curso) {
        this.fechaInicio = fechaInicio;
        this.fechaFinal = fechaFinal;
        this.fechaInscripcion = fechaInscripcion;
        this.fechaTerminado = fechaTerminado;
        this.estado = estado;
        this.alumno = alumno;
        this.curso = curso;
    }

    public Long getCodInscripcion() {
        return codInscripcion;
    }

    public void setCodInscripcion(Long codInscripcion) {
        this.codInscripcion = codInscripcion;
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

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public Turno getTurno() {
        return turno;
    }

    public void setTurno(Turno turno) {
        this.turno = turno;
    }

    public List<Asistencia> getAsistencias() {
        return asistencias;
    }

    public void setAsistencias(List<Asistencia> asistencias) {
        this.asistencias = asistencias;
    }
}
