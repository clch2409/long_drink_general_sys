package com.longdrink.rest_api.dao;

import com.longdrink.rest_api.model.Pago;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IPagoDAO extends CrudRepository<Pago,Long> {
    List<Pago> findAllByAlumnoCodAlumno(Long codAlumno);

}
