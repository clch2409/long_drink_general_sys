package com.longdrink.rest_api.services;

import com.longdrink.rest_api.dao.IInscripcionDAO;
import com.longdrink.rest_api.model.Inscripcion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class InscripcionService {
    @Autowired
    private IInscripcionDAO inscripcionDAO;

    public List<Inscripcion> listarPorAlumno(Long codAlumno){
        return inscripcionDAO.findAllByAlumnoCodAlumno(codAlumno);
    }

    public List<Inscripcion> listarPorEstado(){
        return inscripcionDAO.findAllByEstado();
    }

    public List<Inscripcion> listarEnCurso(){ return inscripcionDAO.findAllByEnCurso(); }

    public List<Inscripcion> listarInscripciones(){ return (List<Inscripcion>) inscripcionDAO.findAll(); }

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

    public Inscripcion buscarPorPk(Long codInscripcion){
        return inscripcionDAO.findById(codInscripcion).orElse(null);
    }

    public List<Inscripcion> listarPorEstado_FechaTerminado_Curso(boolean estado, Date fechaTerminado){
        return inscripcionDAO.findAllByEstadoAndFechaTerminado(estado,fechaTerminado);
    }

    public List<Inscripcion> listarPorEstado_FechaTerminado(boolean estado, Date fechaTerminado){
        return inscripcionDAO.findAllByEstadoAndFechaTerminado(estado,fechaTerminado);
    }

    public List<Inscripcion> listarPorSeccion(Long codSeccion){
        return inscripcionDAO.findAllBySeccionCodSeccion(codSeccion);
    }

    public List<Inscripcion> findAllBySeccionCodCurso(Long codCurso){
        return inscripcionDAO.findAllBySeccionCursoCodCurso(codCurso);
    }
}
