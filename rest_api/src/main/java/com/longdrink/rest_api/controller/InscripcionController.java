package com.longdrink.rest_api.controller;

import com.longdrink.rest_api.model.Inscripcion;
import com.longdrink.rest_api.model.payload.DetalleInscripcion;
import com.longdrink.rest_api.model.payload.Mensaje;
import com.longdrink.rest_api.services.AlumnoService;
import com.longdrink.rest_api.services.CursoService;
import com.longdrink.rest_api.services.InscripcionService;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inscripcion")
public class InscripcionController {
    @Autowired
    private InscripcionService inscripcionService;
    @Autowired
    private AlumnoService alumnoService;
    @Autowired
    private CursoService cursoService;

    @GetMapping
    public ResponseEntity<?> listarInscripciones(){
        List<Inscripcion> listaInscripciones = inscripcionService.listarInscripciones();
        if(listaInscripciones.isEmpty()){
            return new ResponseEntity<>(
                    new Mensaje("Ups! No hemos encontrado ningun alumno inscrito en la institución.", 404),
                    HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(listaInscripciones,HttpStatus.OK);
    }
    @GetMapping("/pendientes")
    public ResponseEntity<?> listarInscripcionesPendientes(){
        List<Inscripcion> listaInscripciones = inscripcionService.listarPendientes();
        if(listaInscripciones.isEmpty()){
            return new ResponseEntity<>(
                    new Mensaje("Ups! No hemos podido recuperar solicitudes de inscripcion pendientes. Parece ser que estas al día!", 404),
                    HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(listaInscripciones,HttpStatus.OK);
    }

    @GetMapping("/aceptadas")
    public ResponseEntity<?> listarInscripcionesAceptadas(){
        List<Inscripcion> listaInscripciones = inscripcionService.listarPorEstado();
        if(listaInscripciones.isEmpty()){
            return new ResponseEntity<>(
                    new Mensaje("Ups! No hemos podido recuperar solicitudes de inscripcion aceptadas.", 404),
                    HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(listaInscripciones,HttpStatus.OK);
    }

    @GetMapping("/por_alumno")
    public ResponseEntity<?> listarPorAlumno(@RequestParam Long codAlumno){
        List<Inscripcion> listaInscripciones = inscripcionService.listarPorAlumno(codAlumno);
        if(listaInscripciones.isEmpty()){
            return new ResponseEntity<>(
                    new Mensaje("Ups! El alumno ingresado no posee inscripciones en la institución.", 404),
                    HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(listaInscripciones,HttpStatus.OK);
    }

    @GetMapping("/por_dni")
    public ResponseEntity<?> listarPorDNIAlumno(@RequestParam String dni){
        List<Inscripcion> listaInscripciones = inscripcionService.listarPorDniAlumno(dni);
        if (listaInscripciones.isEmpty()){
            return new ResponseEntity<>(
                    new Mensaje("Ups! El alumno ingresado no posee inscripciones en la institución.", 404),
                    HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(listaInscripciones,HttpStatus.OK);
    }

    @GetMapping("/por_curso")
    public ResponseEntity<?> listarPorCurso(@RequestParam Long codCurso){
        List<Inscripcion> listaInscripciones = inscripcionService.listarPorCurso(codCurso);
        if(listaInscripciones.isEmpty()){
            return new ResponseEntity<>(
                    new Mensaje("Ups! El curso ingresado no posee alumnos inscritos.", 404),
                    HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(listaInscripciones,HttpStatus.OK);
    }
    /* TODO: Eliminar en próximos días.
    @PostMapping() //Diseñado para app Movil.
    public ResponseEntity<?> agregarInscripcion(@RequestBody InsertInscripcion ins){
        Alumno alumno = alumnoService.getPorCod(ins.getCodAlumno());
        Curso curso = cursoService.getPorCod(ins.getCodCurso());
        if(alumno == null || curso == null){
            return new ResponseEntity<>(
                    new Mensaje("Ups! Imposible registrar, alumno o curso no encontrados.", 404),
                    HttpStatus.NOT_FOUND);
        }
        List<Inscripcion> conteoIns = inscripcionService.listarPorEstado_Curso(true,curso.getCodCurso());
        if(!(conteoIns.size() < curso.getCantidadAlumnos())){ //Comprobar si el curso tiene capacidad para otro registro.
            return new ResponseEntity<>(
                    new Mensaje("Ups! Capacidad maxima de alumnos alcanzada. Curso de ID: "+curso.getCantidadAlumnos(), 404),
                    HttpStatus.BAD_REQUEST);
        }
        //Insertar Inscripcion...!
        try{
            Inscripcion registrarInscripcion = new Inscripcion(new InscripcionPk(alumno.getCodAlumno(),curso.getCodCurso()),ins.getFechaInicio(),ins.getFechaFinal(),ins.getFechaInscripcion(),null,false,alumno,curso);
            Inscripcion registro = inscripcionService.guardar(registrarInscripcion);
            return new ResponseEntity<>(registro,HttpStatus.CREATED);
        }
        catch(Exception ex){
            return new ResponseEntity<>(
                    new Mensaje("Error! Ha sucedido un error en el guardado de datos.", 500),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    } */

    /* TODO: Eliminar en próximos días.
    @PostMapping("/confirmar_inscripcion")
    public ResponseEntity<?> confirmarInscripcion(@RequestParam Long codInscripcion){
        try{
            Inscripcion ins = inscripcionService.buscarPorPk(codInscripcion).get();
            ins.setEstado(true);
            inscripcionService.guardar(ins);
            return new ResponseEntity<>(ins,HttpStatus.OK);
        }
        catch(Exception ex){
            return new ResponseEntity<>(
                    new Mensaje("Error! Datos de inscripción no encontrados.", 404),
                    HttpStatus.NOT_FOUND);
        }
    } */

    /* TODO: Eliminar en próximos días.
    @PostMapping("/rechazar_inscripcion")
    public ResponseEntity<?> rechazarInscripcion(@RequestParam Long codInscripcion){
        try{
            Inscripcion ins = inscripcionService.buscarPorPk(codInscripcion).get();
            ins.setEstado(false);
            ins.setFechaTerminado(ins.getFechaInscripcion());
            inscripcionService.guardar(ins);
            return new ResponseEntity<>(ins,HttpStatus.OK);
        }
        catch(Exception ex){
            return new ResponseEntity<>(
                    new Mensaje("Error! Datos de inscripción no encontrados.", 404),
                    HttpStatus.NOT_FOUND);
        }
    } */

    @GetMapping("/detalle")
    public ResponseEntity<?> detalleInscripcion(@RequestParam Long codInscripcion){
        try{
            Inscripcion ins = inscripcionService.buscarPorPk(codInscripcion).get();
            DetalleInscripcion retorno = new DetalleInscripcion();
            BeanUtils.copyProperties(retorno,ins.getAlumno());
            BeanUtils.copyProperties(retorno,ins.getCurso());
            BeanUtils.copyProperties(retorno,ins);
            return new ResponseEntity<>(retorno,HttpStatus.OK);
        }
        catch(Exception ex){
            return new ResponseEntity<>(
                    new Mensaje("Error! Datos de inscripción no encontrados.", 404),
                    HttpStatus.NOT_FOUND);
        }
    }

}
