package com.longdrink.rest_api.services;

import com.longdrink.rest_api.dao.IProfesorDAO;
import com.longdrink.rest_api.dao.ISeccionDAO;
import com.longdrink.rest_api.model.Curso;
import com.longdrink.rest_api.model.Profesor;
import com.longdrink.rest_api.model.Seccion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProfesorService {
    @Autowired
    private IProfesorDAO profesorDAO;
    @Autowired
    private ISeccionDAO seccionDAO;

    public List<Profesor> listarProfesores(){ return (List<Profesor>) profesorDAO.findAll(); }

    public List<Profesor> listarActivos(){ return profesorDAO.findAllByActivo(); }

    public List<Profesor> listarActivosNoAsignados(){
        List<Profesor> listaProfesores = (List<Profesor>) profesorDAO.findAll();
        List<Profesor> retorno = new ArrayList<Profesor>();
        for(Profesor p: listaProfesores){
            List<Seccion> listaSecciones = p.getSecciones();
            if(listaSecciones.isEmpty()){ //Sin secciones asignadas? Profesor disponible.
                retorno.add(p);
            }
            else{
                int c = 0;
                for(Seccion s: listaSecciones){ //Contar secciones en proceso/activas.
                    if(s.isEstado()){
                        c++;
                    }
                }
                if(c <= 0){ //Si no hay secciones activas, el profesor esta disponible.
                    retorno.add(p);
                }
            }
        }
        return retorno;
    }

    public Profesor getPorCod(Long cod){
        return profesorDAO.findById(cod).orElse(null);
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
