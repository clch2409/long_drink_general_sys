package com.longdrink.rest_api.dao;

import com.longdrink.rest_api.model.Rol;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface IRolDAO extends CrudRepository<Rol,Long> {
}
