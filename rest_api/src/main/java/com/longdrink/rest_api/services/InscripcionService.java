package com.longdrink.rest_api.services;

import com.longdrink.rest_api.dao.IInscripcionDAO;
import com.longdrink.rest_api.model.Inscripcion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InscripcionService {
    @Autowired
    private IInscripcionDAO inscripcionDAO;
    public List<Inscripcion> listarPorAlumno(Long codAlumno){
        return inscripcionDAO.findAllByAlumnoCodAlumno(codAlumno);
    }

    public List<Inscripcion> listarPorCurso(Long codCurso){
        return inscripcionDAO.findAllByCursoCodCurso(codCurso);
    }

    public List<Inscripcion> listarPorEstado(){
        return inscripcionDAO.findAllByEstado();
    }

    public List<Inscripcion> listarPendientes(){ return inscripcionDAO.findAllByPendiente(); }

    public List<Inscripcion> listarInscripciones(){ return (List<Inscripcion>) inscripcionDAO.findAll(); }

    public List<Inscripcion> listarPorEstado_Curso(boolean estado, Long codCurso){ return inscripcionDAO.findAllByEstadoAndCursoCodCurso(estado,codCurso); }

    public List<Inscripcion> listarPorDniAlumno(String dni){ return inscripcionDAO.findAllByAlumnoDni(dni); }

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

    public Optional<Inscripcion> buscarPorPk(Long codInscripcion){
        return inscripcionDAO.findByCodInscripcion(codInscripcion);
    }
}
