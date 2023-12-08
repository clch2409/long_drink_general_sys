package com.longdrink.rest_api.controller;

import com.longdrink.rest_api.model.*;
import com.longdrink.rest_api.model.payload.EditarCurso;
import com.longdrink.rest_api.model.payload.InsertCurso;
import com.longdrink.rest_api.model.payload.Mensaje;
import com.longdrink.rest_api.services.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/curso")
public class CursoController {
    @Autowired
    private CursoService cursoService;
    @Autowired
    private InscripcionService inscripcionService;
    @Autowired
    private ProfesorService profesorService;
    @Autowired
    private TurnoService turnoService;
    @Autowired
    private SeccionService seccionService;

    @GetMapping
    public ResponseEntity<?> get(){
        List<Curso> listaCursos = cursoService.listarCursos();
        if(listaCursos.isEmpty()){
            return new ResponseEntity<>(new Mensaje("Error! No se encontraron cursos.",404),
                    HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(listaCursos,HttpStatus.OK);
    }

    @GetMapping("/activos")
    public ResponseEntity<?> getVisibles(){
        List<Curso> listaCursos = cursoService.listarCursosVisibles();
        if(listaCursos.isEmpty()){
            return new ResponseEntity<>(new Mensaje("Error! No se encontraron cursos visibles por los estudiantes.",404),
                    HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(listaCursos,HttpStatus.OK);
    }

    @GetMapping("/{cod}")
    public ResponseEntity<?> getPorCodigo(@PathVariable Long cod){
        Curso curso = cursoService.getPorCod(cod);
        if(curso == null){
            return new ResponseEntity<>(new Mensaje("Error! No se encontro el curso de código: "+cod+".",404),
                    HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(curso,HttpStatus.OK);
    }

    //Nuevo curso, con profesor y sección asignada. TODO: SPT3 - RC. MODIFICAR FRONT.
    @PostMapping
    public ResponseEntity<?> nuevoCurso(@RequestBody InsertCurso c){
        List<Profesor> profesoresDisponibles = profesorService.listarActivosNoAsignados();
        List<Turno> listaTurnos = turnoService.listarTurnos();
        Profesor profesor = profesorService.getPorCod(c.getCodProfesor());
        Turno turno = turnoService.getByCod(c.getCodTurno());
        if(profesor == null || !profesor.isActivo()){
            return new ResponseEntity<>(new Mensaje("Ups! El profesor seleccionado no existe o no esta activo.",400),HttpStatus.BAD_REQUEST);
        }
        if(!profesoresDisponibles.contains(profesor)){
            return new ResponseEntity<>(new Mensaje("Ups! El profesor seleccionado no esta disponible.",400),HttpStatus.BAD_REQUEST);
        }
        if(turno == null) {
            return new ResponseEntity<>(new Mensaje("Ups! El turno seleccionado no existe.", 400), HttpStatus.BAD_REQUEST);
        }
        InsertCurso limpiarDatos = c.limpiarDatos();
        boolean datosValidos = limpiarDatos.validarDatos();
        if(!datosValidos){
            return new ResponseEntity<>(new Mensaje("Ups! Los datos de curso ingresados no cumplen el formato correcto.", 400), HttpStatus.BAD_REQUEST);
        }
        //Insert de datos.
        try{
            List<Turno> turnos = new ArrayList<>();
            turnos.add(turno);
            Curso curso = new Curso(0L,limpiarDatos.getNombre(),
                    limpiarDatos.getDescripcion(),limpiarDatos.getMensualidad(),
                    limpiarDatos.getDuracion(), limpiarDatos.isVisibilidad(),limpiarDatos.getFrecuencia(),
                    "https://i.imgur.com/APbzr19.jpg",turnos);
            Curso cursoGuardado = cursoService.guardar(curso);

            Seccion seccion = new Seccion(0L,seccionService.generarNombre(cursoGuardado.getCodCurso()),
                    limpiarDatos.getFechaInicio(), limpiarDatos.getFechaFinal(),
                    true,limpiarDatos.getMaxAlumnos(),
                    cursoGuardado,cursoGuardado.getTurnos().get(0),profesor);
            seccionService.guardar(seccion);
            return new ResponseEntity<>(cursoGuardado,HttpStatus.CREATED); //cursoGuardado
        }
        catch(Exception ex){
            return new ResponseEntity<>(new Mensaje("Ups! Ha sucedido un error interno del servidor",500),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/visibilidad")
    public ResponseEntity<?> cambiarVisibilidad(@RequestParam boolean visibilidad, @RequestParam Long codCurso){
        Curso curso = cursoService.getPorCod(codCurso);
        if(curso == null){
            return new ResponseEntity<>(new Mensaje("Ups! El curso ingresado no existe.", 404), HttpStatus.NOT_FOUND);
        }
        curso.setVisibilidad(visibilidad);
        Curso cursoGuardado = cursoService.actualizar(curso);
        return new ResponseEntity<>(cursoGuardado,HttpStatus.OK);
    }

    @PutMapping() //TODO: SPT3 - R.C MODIFICAR FRONT.
    @CrossOrigin(allowedHeaders = "*",origins = "*")
    public ResponseEntity<?> editarCurso(@RequestBody EditarCurso carga){
        Curso c = cursoService.getPorCod(carga.getCodCurso());
        if(c == null){
            return new ResponseEntity<>(new Mensaje("Ups! Curso o profesor no encontrados",400),HttpStatus.BAD_REQUEST);
        }
        EditarCurso limpiarDatos = carga.limpiarDatos();
        boolean datosValidos = limpiarDatos.validarDatos();
        if(!datosValidos){
            return new ResponseEntity<>(new Mensaje("Ups! Datos de formato incorrecto ingresados.",400),HttpStatus.BAD_REQUEST);
        }
        //Update de datos!!
        try{
            c.setNombre(limpiarDatos.getNombre());
            c.setDescripcion(limpiarDatos.getDescripcion());
            c.setFrecuencia(limpiarDatos.getFrecuencia());
            c.setMensualidad(limpiarDatos.getMensualidad());
            c.setVisibilidad(limpiarDatos.isVisibilidad());
            Curso cursoActualizado = cursoService.actualizar(c);
            return new ResponseEntity<>(cursoActualizado,HttpStatus.OK);
        }
        catch(Exception ex){
            return new ResponseEntity<>(new Mensaje("Error! Imposible actualizar datos. Intente nuevamente.",500),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
