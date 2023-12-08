package com.longdrink.rest_api.services;

import com.longdrink.rest_api.dao.IDetallePagoDAO;
import com.longdrink.rest_api.model.DetallePago;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DetallePagoService {
    @Autowired
    private IDetallePagoDAO detallePagoDAO;

    public List<DetallePago> listarDetallePagos(){ return (List<DetallePago>) detallePagoDAO.findAll(); }

    public Optional<DetallePago> verDetallePago(Long codPago){ return detallePagoDAO.findByPagoCodPago(codPago); }

    public DetallePago guardarDetallePago(DetallePago dp){
        dp.setCodDetallePago(0L);
        return detallePagoDAO.save(dp);
    }

    public DetallePago actualizarDetallePago(DetallePago dp){
        return detallePagoDAO.save(dp);
    }

}
