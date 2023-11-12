package com.longdrink.rest_api.dao;

import com.longdrink.rest_api.model.DetallePago;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface IDetallePagoDAO extends CrudRepository<DetallePago,Long> {
    Optional<DetallePago> findByPagoCodPago(Long codPago);
}
