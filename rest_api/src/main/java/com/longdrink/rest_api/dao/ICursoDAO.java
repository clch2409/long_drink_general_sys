package com.longdrink.rest_api.dao;

import com.longdrink.rest_api.model.Curso;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ICursoDAO extends CrudRepository<Curso,Long> {
    @Query("SELECT C FROM Curso C WHERE C.visibilidad = true")
    List<Curso> findAllByVisibilidad();
}
