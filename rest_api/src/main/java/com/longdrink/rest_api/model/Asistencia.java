package com.longdrink.rest_api.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;

@Entity(name = "Asistencia")
@Table(name = "asistencia")
public class Asistencia implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_asistencia")
    @JsonProperty("codAsistencia")
    private Long codAsistencia;
    @Column(name = "fecha_asistencia")
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT-5")
    @JsonProperty("fechaAsistencia")
    private Date fechaAsistencia;
    @Column(name = "hora_llegada")
    @Temporal(TemporalType.TIME)
    @JsonFormat(pattern="HH:mm",timezone = "GMT-5")
    @JsonProperty("horaLlegada")
    private Date horaLlegada;
    private byte estado;
    @JsonIgnore
    //@MapsId("codInscripcion")
    @ManyToOne
    @JoinColumn(name = "cod_inscripcion")
    private Inscripcion inscripcion;

    public Asistencia(){}

    public Asistencia(Long codAsistencia, Date fechaAsistencia, Date horaLlegada, byte estado, Inscripcion inscripcion) {
        this.codAsistencia = codAsistencia;
        this.fechaAsistencia = fechaAsistencia;
        this.horaLlegada = horaLlegada;
        this.estado = estado;
        this.inscripcion = inscripcion;
    }

    public Long getCodAsistencia() {
        return codAsistencia;
    }

    public void setCodAsistencia(Long codAsistencia) {
        this.codAsistencia = codAsistencia;
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

    public byte isEstado() {
        return estado;
    }

    public void setEstado(byte estado) {
        this.estado = estado;
    }

    public Inscripcion getInscripcion() {
        return inscripcion;
    }

    public void setInscripcion(Inscripcion inscripcion) {
        this.inscripcion = inscripcion;
    }
}
