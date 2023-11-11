package com.longdrink.rest_api.services;

import com.longdrink.rest_api.dao.IAsistenciaDAO;
import com.longdrink.rest_api.model.Asistencia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AsistenciaService {
    @Autowired
    private IAsistenciaDAO asistenciaDAO;

    public List<Asistencia> listarAsistencias(){
        return (List<Asistencia>) asistenciaDAO.findAll();
    }

    public List<Asistencia> asistenciaPorAlumno(Long codInscripcion){
        return asistenciaDAO.findAllByInscripcionCodInscripcion(codInscripcion);
    }

    public Asistencia guardarAsistencia(Asistencia a){
        a.setCodAsistencia(0L);
        return asistenciaDAO.save(a);
    }

    public Asistencia actualizarAsistencia(Asistencia a){
        return asistenciaDAO.save(a);
    }
}
