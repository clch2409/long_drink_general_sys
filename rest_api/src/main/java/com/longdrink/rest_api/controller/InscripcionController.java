package com.longdrink.rest_api.controller;

import com.longdrink.rest_api.model.Alumno;
import com.longdrink.rest_api.model.Curso;
import com.longdrink.rest_api.model.Inscripcion;
import com.longdrink.rest_api.model.Turno;
import com.longdrink.rest_api.model.payload.DetalleInscripcion;
import com.longdrink.rest_api.model.payload.InsertInscripcion;
import com.longdrink.rest_api.model.payload.Mensaje;
import com.longdrink.rest_api.services.*;
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
    @Autowired
    private TurnoService turnoService;
    @Autowired(required = true)
    private EmailService emailService;

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

    @GetMapping("/por_cod")
    public ResponseEntity<?> getPorCod(@RequestParam Long codInscripcion){
        try{
            Inscripcion retorno = inscripcionService.buscarPorPk(codInscripcion).get();
            if(retorno.getCodInscripcion() != 0L){
                return new ResponseEntity<>(retorno,HttpStatus.OK);
            }
            return new ResponseEntity<>(new Mensaje("Ups! Datos de inscripción no encontrados.",404),HttpStatus.NOT_FOUND);
        }
        catch(Exception ex){
            return new ResponseEntity<>(new Mensaje("Ups! Datos de inscripción no encontrados.",404),HttpStatus.NOT_FOUND);
        }
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

    //Inscripción para alumno ya registrado. Procede en caso el alumno haya terminado cursos anteriores, se encuentre habilitado y el curso tenga vacantes disponibles.
    @PostMapping()
    public ResponseEntity<?> inscribirAlumnoExistente(@RequestBody InsertInscripcion ins){
        Alumno alumno = alumnoService.getPorCod(ins.getCodAlumno());
        Curso curso = cursoService.getPorCod(ins.getCodCurso());
        if(alumno == null){
            return new ResponseEntity<>(new Mensaje("Ups! Alumno no encontrado o deshabilitado.",400),HttpStatus.BAD_REQUEST);
        }
        if(curso == null){
            return new ResponseEntity<>(new Mensaje("Ups! Curso no encontrado!",400),HttpStatus.BAD_REQUEST);
        }
        if(!alumno.isActivo()){
            return new ResponseEntity<>(new Mensaje("Ups! Alumno deshabilitado.",400),HttpStatus.BAD_REQUEST);
        }
        boolean cursoLleno = cursoService.cursoLleno(curso.getCodCurso());
        if(cursoLleno){
            return new ResponseEntity<>(new Mensaje("Error! El curso seleccionado no cuenta con vacantes disponibles.",400),
                    HttpStatus.BAD_REQUEST);
        }
        List<Inscripcion> inscripcionesAlumno = inscripcionService.listarPorAlumno(alumno.getCodAlumno());
        boolean cursoEnProceso = true;
        if(!inscripcionesAlumno.isEmpty()){
            int c = 0;
            for(Inscripcion i: inscripcionesAlumno){
                if(i.getFechaTerminado() != null){
                    c++;
                }
            }
            if(c == inscripcionesAlumno.size()){
                cursoEnProceso = false;
            }
        }
        if(cursoEnProceso){
            return new ResponseEntity<>(new Mensaje("Error! El alumno seleccionado debe terminar sus cursos en proceso.",400),
                    HttpStatus.BAD_REQUEST);
        }
        List<Turno> listaTurnos = turnoService.listarTurnoPorCurso(curso.getCodCurso());
        if(listaTurnos.isEmpty()){
            return new ResponseEntity<>(new Mensaje("Error! El curso seleccionado no tiene turnos asignados.",400),
                    HttpStatus.BAD_REQUEST);
        }
        //Insert de datos!!
        try{
            Inscripcion i = new Inscripcion(0L,ins.getFechaInicio(),
                    ins.getFechaFinal(),ins.getFechaInscripcion(),
                    null,true,alumno,
                    curso,listaTurnos.get(0));
            Inscripcion inscripcionGuardada = inscripcionService.guardar(i);
            emailService.enviarEmailNuevaInscripcion(alumno,curso,inscripcionGuardada);
            return new ResponseEntity<>(inscripcionGuardada,HttpStatus.CREATED);
        }
        catch(Exception ex){
            return new ResponseEntity<>(new Mensaje("Error! Ha sucedido en error en el guardado de datos.",500),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
