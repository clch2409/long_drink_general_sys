package com.longdrink.rest_api.dao;

import com.longdrink.rest_api.model.Seccion;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ISeccionDAO extends CrudRepository<Seccion,Long> {

    List<Seccion> findAllByCursoCodCursoAndEstado(Long codCurso, boolean estado);

    List<Seccion> findAllByTurnoCodTurno(Long codTurno);

    List<Seccion> findAllByEstado(boolean estado);

    List<Seccion> findAllByCursoCodCursoAndTurnoCodTurno(Long codCurso, Long codTurno);

    List<Seccion> findAllByCursoCodCurso(Long codCurso);
}
