package com.longdrink.rest_api.dao;

import com.longdrink.rest_api.model.Turno;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ITurnoDAO extends CrudRepository<Turno,Long> {
    List<Turno> findAllByCursosCodCurso(Long codCurso);
}
