package com.longdrink.rest_api.controller;

import com.longdrink.rest_api.model.Profesor;
import com.longdrink.rest_api.model.payload.Mensaje;
import com.longdrink.rest_api.services.ProfesorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profesor")
public class ProfesorController {
    @Autowired
    private ProfesorService profesorService;

    @GetMapping()
    public ResponseEntity<?> get(){
        List<Profesor> listadoProfesores = profesorService.listarProfesores();
        if(listadoProfesores.isEmpty()){
            return new ResponseEntity<>(new Mensaje("Error! No se encontraron profesores.",404),
                    HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(listadoProfesores,HttpStatus.OK);
    }

    @GetMapping("/activos")
    public ResponseEntity<?> getActivos(){
        List<Profesor> listadoProfesores = profesorService.listarActivos();
        if(listadoProfesores.isEmpty()){
            return new ResponseEntity<>(new Mensaje("Error! No se encontraron profesores.",404),
                    HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(listadoProfesores,HttpStatus.OK);
    }

    @GetMapping("/{dni}")
    public ResponseEntity<?> getActivos(@PathVariable String dni){
        Profesor profesor = profesorService.getPorDNI(dni);
        if(profesor == null){
            return new ResponseEntity<>(new Mensaje("Error! No se encontro el profesor con DNI: "+dni,404),
                    HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(profesor,HttpStatus.OK);
    }

    @GetMapping("/disponibles")
    public ResponseEntity<?> getActivosDisponibles(){
        List<Profesor> listadoProfesores = profesorService.listarActivosNoAsignados();
        if(listadoProfesores.isEmpty()){
            return new ResponseEntity<>(new Mensaje("Error! No se encontraron profesores activos con cursos no asignados.",404),
                    HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(listadoProfesores,HttpStatus.OK);
    }
}
