package com.longdrink.rest_api.controller;

import com.longdrink.rest_api.model.Asistencia;
import com.longdrink.rest_api.model.payload.Mensaje;
import com.longdrink.rest_api.services.AsistenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/asistencia")
public class AsistenciaController {
    @Autowired
    private AsistenciaService asistenciaService;

    @GetMapping
    public ResponseEntity<?> asistenciaPorAlumno(@RequestParam Long codInscripcion){
        List<Asistencia> listaAsistencias = asistenciaService.asistenciaPorAlumno(codInscripcion);
        if(listaAsistencias.isEmpty()){
            return new ResponseEntity<>(new Mensaje("Ups! Alumno no registra asistencias en el curso especificado.",404), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(listaAsistencias,HttpStatus.OK);
    }
}
