package com.longdrink.rest_api.controller;

import com.longdrink.rest_api.model.Curso;
import com.longdrink.rest_api.model.Inscripcion;
import com.longdrink.rest_api.model.Profesor;
import com.longdrink.rest_api.model.Turno;
import com.longdrink.rest_api.model.payload.InsertCurso;
import com.longdrink.rest_api.model.payload.Mensaje;
import com.longdrink.rest_api.services.CursoService;
import com.longdrink.rest_api.services.InscripcionService;
import com.longdrink.rest_api.services.ProfesorService;
import com.longdrink.rest_api.services.TurnoService;
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

    //Cursos con vacantes disponibles.
    @GetMapping("/disponibles")
    public ResponseEntity<?> getDisponibles(){
        List<Inscripcion> listaInscripcion = inscripcionService.listarPorEstado_FechaTerminado(true,null);
        List<Inscripcion> filtro = new ArrayList<>();
        List<Curso> listaCursos = cursoService.listarCursosSinInscripciones();
        List<Curso> retorno = new ArrayList<>();
        int c = 0;
        LocalDateTime fechaActual = LocalDateTime.now();
        Instant instant = fechaActual.atZone(ZoneId.systemDefault()).toInstant();
        Date fecha = Date.from(instant);
        for(Inscripcion i: listaInscripcion){
            if(i.getFechaFinal().after(fecha)){
                filtro.add(i);
            }
        }
        for(Inscripcion i: filtro){
            if(!cursoService.cursoLleno(i.getCurso().getCodCurso())){
                retorno.add(i.getCurso());
            }
        }
        if(!listaCursos.isEmpty()){
            retorno.addAll(listaCursos);
        }
        if(retorno.isEmpty()){
            return new ResponseEntity<>(new Mensaje("Ups! No se encontraron cursos con vacantes disponibles.",404),HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(retorno,HttpStatus.OK);
    }

    //Nuevo curso, con profesor asignado.
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
                    limpiarDatos.getDuracion(),limpiarDatos.getCantidadAlumnos(),
                    limpiarDatos.isVisibilidad(),limpiarDatos.getFrecuencia(),
                    "https://i.imgur.com/APbzr19.jpg",profesor,turnos);
            Curso cursoGuardado = cursoService.guardar(curso);
            return new ResponseEntity<>(cursoGuardado,HttpStatus.CREATED);
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
    //TODO: Asignacion de temas a cursos ---> Subida de archivos pdf??
}
