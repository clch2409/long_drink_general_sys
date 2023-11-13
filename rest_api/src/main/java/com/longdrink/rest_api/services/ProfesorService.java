package com.longdrink.rest_api.services;

import com.longdrink.rest_api.dao.IProfesorDAO;
import com.longdrink.rest_api.model.Profesor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfesorService {
    @Autowired
    private IProfesorDAO profesorDAO;

    public List<Profesor> listarProfesores(){ return (List<Profesor>) profesorDAO.findAll(); }

    public List<Profesor> listarActivos(){ return profesorDAO.findAllByActivo(); }

    public List<Profesor> listarActivosNoAsignados(){ return profesorDAO.findActivosNoAsignados(); }

    public Profesor getPorCod(Long cod){
        try{
            return profesorDAO.findById(cod).get();
        }
        catch(Exception ex){ return null; }
    }

    public Profesor getPorDNI(String dni){
        try{
            Profesor p = profesorDAO.findByDni(dni).get();
            return p;
        }
        catch(Exception ex){ return null; }
    }

    public Profesor getPorCodUsuario(Long cod){
        try{
            Profesor p = profesorDAO.findByUsuarioCodUsuario(cod).get();
            return p;
        }
        catch(Exception ex){ return null; }
    }

    public Profesor guardar(Profesor p){
        p.setCodProfesor(0L);
        return profesorDAO.save(p);
    }

    public Profesor actualizar(Profesor p){ return profesorDAO.save(p); }

    public boolean eliminar(Long cod){
        try{
            Profesor profesor = profesorDAO.findById(cod).get();
            if(profesor.isActivo()){
                profesor.setActivo(false);
                profesorDAO.save(profesor);
                return true;
            }
        }
        catch(Exception ex){ return false; }
        return false;
    }
}
