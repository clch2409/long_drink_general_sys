package com.longdrink.rest_api.dao;

import com.longdrink.rest_api.model.Profesor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface IProfesorDAO extends CrudRepository<Profesor,Long> {
    @Query("SELECT P FROM Profesor P WHERE P.activo = true")
    List<Profesor> findAllByActivo();

    Optional<Profesor> findByDni(String dni);

    Optional<Profesor> findByUsuarioCodUsuario(Long cod);
}
