package com.longdrink.rest_api.services;

import com.longdrink.rest_api.dao.ICursoDAO;
import com.longdrink.rest_api.dao.IInscripcionDAO;
import com.longdrink.rest_api.model.Curso;
import com.longdrink.rest_api.model.Inscripcion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CursoService {
    @Autowired
    private ICursoDAO cursoDAO;

    @Autowired
    private IInscripcionDAO inscripcionDAO;

    public List<Curso> listarCursos(){ return (List<Curso>) cursoDAO.findAll(); }

    public List<Curso> listarCursosVisibles(){ return cursoDAO.findAllByVisibilidad(); }

    public List<Curso> listarCursosSinInscripciones(){ return cursoDAO.findSinInscripciones(); }

    public Curso getPorCod(Long cod){
        try{
            Curso c = cursoDAO.findById(cod).get();
            return c;
        }
        catch(Exception ex){ return null; }
    }

    public Curso guardar(Curso c){
        c.setCodCurso(0L);
        return cursoDAO.save(c);
    }

    public Curso actualizar(Curso c){ return cursoDAO.save(c); }

    public boolean eliminar(Long cod){
        try{
            Curso curso = cursoDAO.findById(cod).get();
            if(curso.isVisibilidad()){
                curso.setVisibilidad(false);
                cursoDAO.save(curso);
                return true;
            }
        }
        catch(Exception ex){ return false; }
        return false;
    }

    //Comprobar curso con vacantes libres.
    public boolean cursoLleno(Long codCurso){
        List<Inscripcion> listaInscripciones = inscripcionDAO.findAllByEstadoAndFechaTerminadoAndCursoCodCurso(true,null,codCurso);
        if(listaInscripciones.isEmpty()){ //Lista vacia.
            return false; //El curso no esta lleno.
        }
        else{
            try{
                int c = 0;
                Curso curso = cursoDAO.findById(codCurso).get();
                for(Inscripcion i : listaInscripciones){
                    c++;
                }
                if(c < curso.getCantidadAlumnos()){
                    return false;
                }
                else{ return true; }
            }
            catch(Exception ex){ return false; } //Curso con vacantes disponibles o no existente.
        }
    }
}
