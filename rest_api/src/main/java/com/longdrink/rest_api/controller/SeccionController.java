package com.longdrink.rest_api.controller;

import com.longdrink.rest_api.model.*;
import com.longdrink.rest_api.model.payload.InsertSeccion;
import com.longdrink.rest_api.model.payload.Mensaje;
import com.longdrink.rest_api.services.CursoService;
import com.longdrink.rest_api.services.ProfesorService;
import com.longdrink.rest_api.services.SeccionService;
import com.longdrink.rest_api.services.TurnoService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/seccion")
public class SeccionController {
    @Autowired
    private SeccionService seccionService;
    @Autowired
    private CursoService cursoService;
    @Autowired
    private ProfesorService profesorService;
    @Autowired
    private TurnoService turnoService;

    @GetMapping()
    public ResponseEntity<?> get(){
        List<Seccion> listaSecciones = seccionService.listarSecciones();
        if(listaSecciones.isEmpty()){
            return new ResponseEntity<>(new Mensaje("Error! No se encontraron secciones.",404),
                    HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(listaSecciones,HttpStatus.OK);
    }

    @GetMapping("/{codSeccion}")
    public ResponseEntity<?> getSeccion(@PathVariable Long codSeccion){
        Seccion s = seccionService.obtenerSeccion(codSeccion);
        if(s == null){
            return new ResponseEntity<>(new Mensaje("Error! No se encontro la seccion.",404),
                    HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(s,HttpStatus.OK);
    }

    @GetMapping("/por_curso")
    public ResponseEntity<?> getPorCurso(@RequestParam Long codCurso){
        List<Seccion> listaSecciones = seccionService.findAllByCodCursoAndEstado(codCurso,true);
        if(listaSecciones.isEmpty()){
            return new ResponseEntity<>(new Mensaje("Error! No se encontraron secciones para el curso ingresado.",404),
                    HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(listaSecciones,HttpStatus.OK);
    }

    @GetMapping("/por_turno")
    public ResponseEntity<?> getPorTurno(@RequestParam Long codTurno){
        List<Seccion> listaSecciones = seccionService.findAllByCodTurno(codTurno);
        if(listaSecciones.isEmpty()){
            return new ResponseEntity<>(new Mensaje("Error! No se encontraron secciones para el turno ingresado.",404),
                    HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(listaSecciones,HttpStatus.OK);
    }

    @GetMapping("/activas")
    public ResponseEntity<?> getActivas(){
        List<Seccion> listaSecciones = seccionService.findAllByEstado(true);
        if(listaSecciones.isEmpty()){
            return new ResponseEntity<>(new Mensaje("Error! No se encontraron secciones activas.",404),
                    HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(listaSecciones,HttpStatus.OK);
    }

    @GetMapping("/inactivas")
    public ResponseEntity<?> getInactivas(){
        List<Seccion> listaSecciones = seccionService.findAllByEstado(false);
        if(listaSecciones.isEmpty()){
            return new ResponseEntity<>(new Mensaje("Error! No se encontraron secciones inactivas.",404),
                    HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(listaSecciones,HttpStatus.OK);
    }

    //Seccion con vacantes disponibles
    @GetMapping("/disponible")
    public ResponseEntity<?> getDisponibles(){
        List<Seccion> listaSecciones = seccionService.listarVacantesDisponibles();
        if(listaSecciones.isEmpty()){
            return new ResponseEntity<>(new Mensaje("Ups! No se encontraron secciones con vacantes disponibles.",404),
                    HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(listaSecciones,HttpStatus.OK);
    }

    @GetMapping("/disponible/{codSeccion}")
    public ResponseEntity<?> disponible(@PathVariable Long codSeccion){
        boolean disponible = seccionService.vacantesDisponibles(codSeccion);
        if(!disponible){
            return new ResponseEntity<>(new Mensaje("Ups! La sección ingresada no cuenta con vacantes disponibles o no existe.",404),
                    HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(seccionService.obtenerSeccion(codSeccion),HttpStatus.OK);
    }

    //Apertura de sección para un curso existente -- Cursos nuevos nacen con una seccion.
    @PostMapping("/asignar_curso")
    public ResponseEntity<?> asignarSeccionCurso(@RequestBody InsertSeccion iS){
        Curso c = cursoService.getPorCod(iS.getCodCurso());
        Profesor p = profesorService.getPorCod(iS.getCodProfesor());
        Turno t = turnoService.getByCod(iS.getCodTurno());
        if(c == null || !c.isVisibilidad()){
            return new ResponseEntity<>(new Mensaje("Ups! El curso seleccionado no existe o no esta activo.",400),HttpStatus.BAD_REQUEST);
        }
        if(p == null || !p.isActivo()){
            return new ResponseEntity<>(new Mensaje("Ups! El profesor seleccionado no existe o no esta activo.",400),HttpStatus.BAD_REQUEST);
        }
        if(t == null){
            return new ResponseEntity<>(new Mensaje("Ups! El turno seleccionado no existe.",400),HttpStatus.BAD_REQUEST);
        }
        int conteoTurnos = 0;
        for(Seccion s: c.getSecciones()){
            if(s.getTurno().equals(t) && s.isEstado()){
                conteoTurnos++;
            }
        }
        if(conteoTurnos > 0){ //Si hay una seccion activa con el mismo turno....!
            return new ResponseEntity<>(new Mensaje("Ups! Ya hay una sección existente para el curso seleccionado con el mismo turno.",400),HttpStatus.BAD_REQUEST);
        }
        if(!profesorService.listarActivosNoAsignados().contains(p)){
            return new ResponseEntity<>(new Mensaje("Ups! El profesor seleccionado no esta disponible.",400),HttpStatus.BAD_REQUEST);
        }
        if(!iS.validarDatos()){
            return new ResponseEntity<>(new Mensaje("Ups! Los datos ingresados no tienen el formato correcto.",400),HttpStatus.BAD_REQUEST);
        }
        //Insert de datos!
        try{
            Seccion seccion = new Seccion(0L,seccionService.generarNombre(c.getCodCurso()),
                    iS.getFechaInicio(),iS.getFechaFinal(),
                    true,iS.getMaxAlumnos(),c,t,p);
            Seccion seccionGuardada = seccionService.guardar(seccion);
            return new ResponseEntity<>(seccionGuardada,HttpStatus.CREATED); //Seccion creada y asignada a curso.
        }
        catch(Exception ex){
            return new ResponseEntity<>(new Mensaje("Error! Imposible asignar sección a curso. Intente nuevamente.",500),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
