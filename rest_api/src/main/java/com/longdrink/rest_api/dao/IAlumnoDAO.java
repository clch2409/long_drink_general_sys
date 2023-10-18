package com.longdrink.rest_api.dao;

import com.longdrink.rest_api.model.Alumno;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IAlumnoDAO extends CrudRepository<Alumno,Long> {
    @Query("SELECT A FROM Alumno A WHERE A.activo = true")
    List<Alumno> findAllByActivo();

    Optional<Alumno> findByDni(String dni);

    Optional<List<Alumno>> findAllByTelefono(String telefono);

    Optional<Alumno> findByUsuarioCodUsuario(Long codUsuario);
}
