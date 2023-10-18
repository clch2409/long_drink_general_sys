package com.longdrink.rest_api.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity(name = "Turno")
@Table(name = "turno")
public class Turno implements Serializable {
    @Id
    @Column(name = "cod_turno")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("codTurno")
    private Long codTurno;

    @Column(length = 25)
    private String nombre;

    @Column(name = "hora_inicio")
    @Temporal(TemporalType.TIME)
    @JsonProperty("horaInicio")
    private Date horaInicio;

    @Column(name = "hora_fin")
    @Temporal(TemporalType.TIME)
    @JsonProperty("horaFin")
    private Date horaFin;

    @JsonBackReference
    @ManyToMany(mappedBy = "turnos")
    private List<Curso> cursos;

    public Turno(){}

    public Turno(Long codTurno, String nombre, Date horaInicio, Date horaFin, List<Curso> cursos) {
        this.codTurno = codTurno;
        this.nombre = nombre;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.cursos = cursos;
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

    public List<Curso> getCursos() {
        return cursos;
    }

    public void setCursos(List<Curso> cursos) {
        this.cursos = cursos;
    }
}
