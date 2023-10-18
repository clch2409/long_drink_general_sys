package com.longdrink.rest_api.dao;

import com.longdrink.rest_api.model.Tema;
import org.springframework.data.repository.CrudRepository;

public interface ITemaDAO extends CrudRepository<Tema,Long> {
}
