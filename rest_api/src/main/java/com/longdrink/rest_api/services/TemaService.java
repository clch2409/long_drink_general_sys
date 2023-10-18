package com.longdrink.rest_api.services;

import com.longdrink.rest_api.dao.ITemaDAO;
import com.longdrink.rest_api.model.Tema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TemaService {
    @Autowired
    private ITemaDAO temaDAO;

    public List<Tema> listarTemas(){ return (List<Tema>) temaDAO.findAll(); }

    public Tema guardar(Tema t){
        t.setCodTema(0L);
        return temaDAO.save(t);
    }

    public Tema actualizar(Tema t){
        return temaDAO.save(t);
    }
}
