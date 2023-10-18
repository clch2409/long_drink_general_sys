package com.longdrink.rest_api.services;

import com.longdrink.rest_api.dao.IInscripcionDAO;
import com.longdrink.rest_api.model.Inscripcion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InscripcionService {
    @Autowired
    private IInscripcionDAO inscripcionDAO;
    public List<Inscripcion> listarPorAlumno(Long codAlumno){
        return inscripcionDAO.findAllByInscripcionPkCodAlumno(codAlumno);
    }

    public List<Inscripcion> listarPorCurso(Long codCurso){
        return inscripcionDAO.findAllByInscripcionPkCodCurso(codCurso);
    }

    public List<Inscripcion> listarPorEstado(){
        return inscripcionDAO.findAllByEstado();
    }

    public Inscripcion guardar(Inscripcion i){
        return inscripcionDAO.save(i);
    }

    public Inscripcion actualizar(Inscripcion i){
        return inscripcionDAO.save(i);
    }

    public Inscripcion modificarEstado(Inscripcion i,boolean estado){
        i.setEstado(estado);
        return inscripcionDAO.save(i);
    }
}
