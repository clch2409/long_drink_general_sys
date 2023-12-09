package com.longdrink.rest_api.services;

import com.longdrink.rest_api.dao.IAlumnoDAO;
import com.longdrink.rest_api.dao.IUsuarioDAO;
import com.longdrink.rest_api.model.Alumno;
import com.longdrink.rest_api.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlumnoService {
    @Autowired
    private IAlumnoDAO alumnoDAO;

    public List<Alumno> listarAlumnos(){ return (List<Alumno>) alumnoDAO.findAll(); }

    public List<Alumno> listarAlumnosActivos(){ return alumnoDAO.findAllByActivo(); }

    public Alumno getPorDNI(String dni){
        return alumnoDAO.findByDni(dni).orElse(null);
    }

    public Alumno getPorCod(Long cod){
        try{
            Alumno a = alumnoDAO.findById(cod).get();
            return a;
        }
        catch(Exception ex){ return null; }
    }

    public Alumno getPorCodUsuario(Long cod){
        try{
            Alumno a = alumnoDAO.findByUsuarioCodUsuario(cod).get();
            return a;
        }
        catch(Exception ex){ return null; }
    }

    public Optional<List<Alumno>> buscarPorTelefono(String telefono){ return alumnoDAO.findAllByTelefono(telefono); }

    public Alumno guardar(Alumno a){
        a.setCodAlumno(0L);
        return alumnoDAO.save(a);
    }

    public Alumno actualizar(Alumno a){
        return alumnoDAO.save(a);
    }

    public boolean eliminar(Long cod){
        try{
            Alumno a = alumnoDAO.findById(cod).get();
            if(a.isActivo()){
                a.setActivo(false);
                alumnoDAO.save(a);
                return true;
            }
        }
        catch(Exception ex){ return false; }
        return false;
    }
}
