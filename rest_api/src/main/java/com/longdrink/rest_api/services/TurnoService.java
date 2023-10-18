package com.longdrink.rest_api.services;

import com.longdrink.rest_api.dao.ITurnoDAO;
import com.longdrink.rest_api.model.Turno;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TurnoService {
    @Autowired
    private ITurnoDAO turnoDAO;

    public List<Turno> listarTurnos(){ return (List<Turno>) turnoDAO.findAll(); }

    public Turno guardar(Turno t){
        t.setCodTurno(0L);
        return turnoDAO.save(t);
    }

    public Turno actualizar(Turno t){
        return turnoDAO.save(t);
    }
}
