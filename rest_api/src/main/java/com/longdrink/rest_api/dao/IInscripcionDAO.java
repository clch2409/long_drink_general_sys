package com.longdrink.rest_api.dao;

import com.longdrink.rest_api.model.Inscripcion;
import com.longdrink.rest_api.model.InscripcionPk;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IInscripcionDAO extends CrudRepository<Inscripcion,InscripcionPk> {
    List<Inscripcion> findAllByInscripcionPkCodAlumno(Long codAlumno);

    List<Inscripcion> findAllByInscripcionPkCodCurso(Long codCurso);

    @Query("SELECT I FROM Inscripcion I WHERE I.estado = true")
    List<Inscripcion> findAllByEstado();
}
