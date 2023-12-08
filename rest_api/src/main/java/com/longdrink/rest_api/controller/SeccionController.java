package com.longdrink.rest_api.controller;

import com.longdrink.rest_api.model.Inscripcion;
import com.longdrink.rest_api.model.Seccion;
import com.longdrink.rest_api.model.payload.Mensaje;
import com.longdrink.rest_api.services.SeccionService;
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

}
