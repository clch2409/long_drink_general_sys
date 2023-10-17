package com.longdrink.rest_api.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@Entity(name = "Turno")
@Table(name = "turno")
public class Turno implements Serializable {
    @Id
    @Column(name = "cod_turno")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codTurno;
    @Column(length = 25)
    private String nombre;
    @Column(name = "hora_inicio")
    @Temporal(TemporalType.TIME)
    private Date horaInicio;
    @Column(name = "hora_fin")
    @Temporal(TemporalType.TIME)
    private Date horaFin;

    @ManyToMany(mappedBy = "turnos")
    private List<Curso> cursos;

}
