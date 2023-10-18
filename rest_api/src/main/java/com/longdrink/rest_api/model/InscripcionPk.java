package com.longdrink.rest_api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;


@Embeddable
public class InscripcionPk implements Serializable {
    @Column(name = "cod_alumno")
    private Long codAlumno;

    @Column(name = "cod_curso")
    private Long codCurso;

    public InscripcionPk(){}

    public InscripcionPk(Long codAlumno, Long codCurso) {
        this.codAlumno = codAlumno;
        this.codCurso = codCurso;
    }

    public Long getCodAlumno() {
        return codAlumno;
    }

    public void setCodAlumno(Long codAlumno) {
        this.codAlumno = codAlumno;
    }

    public Long getCodCurso() {
        return codCurso;
    }

    public void setCodCurso(Long codCurso) {
        this.codCurso = codCurso;
    }
}
