package com.longdrink.rest_api.dao;

import com.longdrink.rest_api.model.Asistencia;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IAsistenciaDAO extends CrudRepository<Asistencia,Long> {
    List<Asistencia> findAllByInscripcionCodInscripcion(Long codInscripcion);
}
