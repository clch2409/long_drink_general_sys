package com.longdrink.rest_api.controller;

import com.longdrink.rest_api.model.Asistencia;
import com.longdrink.rest_api.model.Inscripcion;
import com.longdrink.rest_api.model.payload.MarcarAsistencia;
import com.longdrink.rest_api.model.payload.Mensaje;
import com.longdrink.rest_api.services.AsistenciaService;
import com.longdrink.rest_api.services.InscripcionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/asistencia")
public class AsistenciaController {
    @Autowired
    private AsistenciaService asistenciaService;
    @Autowired
    private InscripcionService inscripcionService;

    @GetMapping
    public ResponseEntity<?> asistenciaPorAlumno(@RequestParam Long codInscripcion){
        List<Asistencia> listaAsistencias = asistenciaService.asistenciaPorAlumno(codInscripcion);
        if(listaAsistencias.isEmpty()){
            return new ResponseEntity<>(new Mensaje("Ups! Alumno no registra asistencias en el curso especificado.",404), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(listaAsistencias,HttpStatus.OK);
    }

    /**
     *
     * @param carga Recibe carga de tipo MarcarAsistencia
     * @return Tipo de asistencia. 0 -> Falta | 1 -> Asistio a tiempo | 2 -> Llego tarde.
     */
    @PostMapping
    public ResponseEntity<?> marcarAsistencia(@RequestBody MarcarAsistencia carga){
        Inscripcion i = inscripcionService.buscarPorPk(carga.getCodInscripcion());
        Asistencia asistencia = asistenciaService.asistenciaPorFecha(carga.getFechaAsistencia());
        if(i == null){
            return new ResponseEntity<>(new Mensaje("Error! Imposible marcar asistencia, datos de inscripción no encontrados.",404), HttpStatus.NOT_FOUND);
        }
        if(asistencia != null){
            return new ResponseEntity<>(new Mensaje("Error! Asistencia ya marcada.",400),HttpStatus.BAD_REQUEST);
        }
        if(!i.isEstado()){
            return new ResponseEntity<>(new Mensaje("Error! Imposible marcar asistencia, alumno retirado o curso terminado.",404), HttpStatus.NOT_FOUND);
        }
        if(carga.getFechaAsistencia().compareTo(i.getSeccion().getFechaFinal()) > 0){ //Luego de fecha final.
            return new ResponseEntity<>(new Mensaje("Error! Imposible marcar asistencia, el curso ya ha terminado.",400),HttpStatus.BAD_REQUEST);
        }
        if(carga.getFechaAsistencia().compareTo(i.getSeccion().getFechaInicio()) < 0){ //Antes de inicio.
            return new ResponseEntity<>(new Mensaje("Error! Imposible marcar asistencia, el curso no ha comenzado.",400),HttpStatus.BAD_REQUEST);
        }
        //Insert de datos.
        try{
            byte tipoAsistencia = 1;
            if(carga.getHoraLlegada().compareTo(i.getSeccion().getTurno().getHoraInicio()) > 0){ tipoAsistencia = 2; } //Luego de hora inicio. -- Llego tarde (2)
            if(carga.getHoraLlegada().compareTo(i.getSeccion().getTurno().getHoraFin()) > 0){ tipoAsistencia = 0; } //Luego de hora de fin. -- Ni siquiera llego (0 - FALTA)
            Asistencia a = new Asistencia(0L,carga.getFechaAsistencia(),carga.getHoraLlegada(),tipoAsistencia,i);
            Asistencia asistenciaGuardada = asistenciaService.guardarAsistencia(a);
            return new ResponseEntity<>(asistenciaGuardada,HttpStatus.CREATED);
        }
        catch(Exception ex){
            return new ResponseEntity<>(new Mensaje("Error! Imposible marcar asistencia. Comuniquese con administración.",500),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
