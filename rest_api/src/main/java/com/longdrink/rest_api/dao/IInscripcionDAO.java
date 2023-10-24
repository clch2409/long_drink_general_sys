package com.longdrink.rest_api.dao;

import com.longdrink.rest_api.model.Inscripcion;
import com.longdrink.rest_api.model.InscripcionPk;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface IInscripcionDAO extends CrudRepository<Inscripcion,InscripcionPk> {
    List<Inscripcion> findAllByInscripcionPkCodAlumno(Long codAlumno);

    List<Inscripcion> findAllByInscripcionPkCodCurso(Long codCurso);

    List<Inscripcion> findAllByAlumnoDni(String dni);

    @Query("SELECT I FROM Inscripcion I WHERE I.estado = true")
    List<Inscripcion> findAllByEstado();

    @Query("SELECT I FROM Inscripcion I WHERE I.estado = false")
    List<Inscripcion> findAllByPendiente();

    List<Inscripcion> findAllByEstadoAndInscripcionPkCodCurso(boolean Estado, Long CodCurso);

    Optional<Inscripcion> findByInscripcionPk(InscripcionPk inscripcionPk);

}
