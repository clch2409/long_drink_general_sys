package com.longdrink.rest_api.dao;

import com.longdrink.rest_api.model.Tema;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ITemaDAO extends CrudRepository<Tema,Long> {
    List<Tema> findAllByCursosCodCurso(Long codCurso);
}
