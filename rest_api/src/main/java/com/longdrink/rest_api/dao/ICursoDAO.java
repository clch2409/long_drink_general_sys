package com.longdrink.rest_api.dao;

import com.longdrink.rest_api.model.Curso;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ICursoDAO extends CrudRepository<Curso,Long> {
    @Query("SELECT C FROM Curso C WHERE C.visibilidad = true")
    List<Curso> findAllByVisibilidad();

    @Query(value = "SELECT * FROM curso WHERE NOT EXISTS(SELECT * FROM inscripcion WHERE inscripcion.cod_curso = curso.cod_curso) AND curso.visibilidad = 1;",nativeQuery = true)
    List<Curso> findSinInscripciones();
}
