package com.longdrink.rest_api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@Embeddable
public class InscripcionPk implements Serializable {
    @Column(name = "cod_alumno")
    private Long codAlumno;
    @Column(name = "cod_curso")
    private Long codCurso;
}
