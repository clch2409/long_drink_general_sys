package com.longdrink.rest_api.services;

import com.longdrink.rest_api.dao.IPagoDAO;
import com.longdrink.rest_api.model.Pago;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PagoService {
    @Autowired
    private IPagoDAO pagoDAO;

    public List<Pago> listarPagos(){ return (List<Pago>) pagoDAO.findAll(); }

    public List<Pago> listarPagosAlumno(Long codAlumno){ return pagoDAO.findAllByAlumnoCodAlumno(codAlumno); }

    public Pago guardarPago(Pago p){
        p.setCodPago(0L);
        return pagoDAO.save(p);
    }

    public Pago actualizarPago(Pago p){ return pagoDAO.save(p); }

    public Pago cambiarEstado(boolean estado, Long codPago){
        try{
            Pago p = pagoDAO.findById(codPago).get();
            if(p.getCodPago() != 0L){
                p.setEstado(estado);
                return pagoDAO.save(p);
            }
        }
        catch(Exception ex){ return null; }
        return null;
    }
}
