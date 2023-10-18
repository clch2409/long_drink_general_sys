package com.longdrink.rest_api.dao;

import com.longdrink.rest_api.model.Turno;
import org.springframework.data.repository.CrudRepository;

public interface ITurnoDAO extends CrudRepository<Turno,Long> {
}
