package com.longdrink.rest_api.services;

import com.github.fracpete.romannumerals4j.RomanNumeralFormat;
import com.longdrink.rest_api.dao.ISeccionDAO;
import com.longdrink.rest_api.model.Inscripcion;
import com.longdrink.rest_api.model.Seccion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

@Service
public class SeccionService {
    @Autowired
    private ISeccionDAO seccionDAO;

    public List<Seccion> listarSecciones(){
        return (List<Seccion>) seccionDAO.findAll();
    }

    public Seccion obtenerSeccion(Long codSeccion){
        return seccionDAO.findById(codSeccion).orElse(null);
    }

    public List<Seccion> findAllByCodCursoAndEstado(Long codCurso, boolean estado){
        return seccionDAO.findAllByCursoCodCursoAndEstado(codCurso,estado);
    }

    public List<Seccion> findAllByCodCurso(Long codCurso){
        return seccionDAO.findAllByCursoCodCurso(codCurso);
    }

    public List<Seccion> findAllByCodTurno(Long codTurno){
        return seccionDAO.findAllByTurnoCodTurno(codTurno);
    }

    public List<Seccion> findAllByEstado(boolean estado){
        return seccionDAO.findAllByEstado(estado);
    }

    public List<Seccion> listarVacantesDisponibles(){
        List<Seccion> listaSecciones = seccionDAO.findAllByEstado(true);
        List<Seccion> retorno = new ArrayList<>();
        for(Seccion s : listaSecciones){
            List<Inscripcion> listaInscripciones = s.getInscripciones();
            if(listaInscripciones.isEmpty()){ //Sin inscripciones? Disponible.
                retorno.add(s);
            }
            else{
                int c = 0;
                for(Inscripcion i : listaInscripciones){
                    if(i.getFechaTerminado() == null){
                        c++;
                    }
                }
                if(c < s.getMaxAlumnos()){
                    retorno.add(s);
                }
            }
        }
        return retorno;
    }

    public boolean vacantesDisponibles(Long codSeccion){
        Seccion s = seccionDAO.findById(codSeccion).orElse(null);
        if(s != null && s.isEstado()){
            List<Inscripcion> listaInscripciones = s.getInscripciones();
            if(listaInscripciones.isEmpty()){ //Sin inscripciones? Disponible.
                return true;
            }
            else{
                int c = 0;
                for(Inscripcion i : listaInscripciones){
                    if(i.getFechaTerminado() == null){
                        c++;
                    }
                }
                if(c < s.getMaxAlumnos()){
                    return true;
                }else{ return false; }
            }
        }
        else{ return false; }
    }

    public Seccion guardar(Seccion s){
        s.setCodSeccion(0L);
        return seccionDAO.save(s);
    }

    public Seccion actualizar(Seccion s){
        return seccionDAO.save(s);
    }

    public Seccion eliminar(Long codSeccion){
        Seccion s = seccionDAO.findById(codSeccion).orElse(null);
        if(s != null){
            s.setEstado(false);
            return seccionDAO.save(s);
        }
        else{
            return s;
        }
    }

    public String generarNombre(Long codCurso){
        String retorno = "";
        List<Seccion> listaSecciones = seccionDAO.findAllByCursoCodCurso(codCurso);
        if(listaSecciones.isEmpty()){
            retorno = "I."+codCurso+"-"+ Year.now().getValue();
        }
        else{
            RomanNumeralFormat f = new RomanNumeralFormat();
            retorno = f.format(listaSecciones.size())+"."+codCurso+"-"+Year.now().getValue();
        }
        return retorno;
    }
}
