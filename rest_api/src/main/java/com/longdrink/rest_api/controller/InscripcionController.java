package com.longdrink.rest_api.controller;

import com.longdrink.rest_api.model.*;
import com.longdrink.rest_api.model.dto.InscripcionDTO;
import com.longdrink.rest_api.model.payload.DetalleInscripcion;
import com.longdrink.rest_api.model.payload.InsertInscripcion;
import com.longdrink.rest_api.model.payload.Mensaje;
import com.longdrink.rest_api.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

    @Autowired
    private SeccionService seccionService;

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
    // ->> "Cursos en  proceso"
    @GetMapping("/en_proceso")
    public ResponseEntity<?> listarInscripcionesPendientes(){
        List<Inscripcion> listaInscripciones = inscripcionService.listarEnCurso();
        if(listaInscripciones.isEmpty()){
            return new ResponseEntity<>(
                    new Mensaje("Ups! No hemos podido recuperar datos de inscripciones con cursos en proceso.", 404),
                    HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(listaInscripciones,HttpStatus.OK);
    }

    @GetMapping("/retirado_terminado")
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


    @GetMapping("/por_seccion")
    public ResponseEntity<?> listarPorSeccion(@RequestParam Long codSeccion){
        List<Inscripcion> listaInscripciones = inscripcionService.listarPorSeccion(codSeccion);
        List<InscripcionDTO> retorno = new ArrayList<>();
        if(listaInscripciones.isEmpty()){
            return new ResponseEntity<>(
                    new Mensaje("Ups! La sección ingresada no posee alumnos inscritos.", 404),
                    HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(listaInscripciones,HttpStatus.OK); //convertListToDTO(listaInscripciones)
    }

    @GetMapping("/por_cod")
    public ResponseEntity<?> getPorCod(@RequestParam Long codInscripcion){
        Inscripcion retorno = inscripcionService.buscarPorPk(codInscripcion);
        if(retorno != null){
            return new ResponseEntity<>(retorno,HttpStatus.OK);
        }
        return new ResponseEntity<>(new Mensaje("Ups! Datos de inscripción no encontrados.",404),HttpStatus.NOT_FOUND);

    }

    @GetMapping("/detalle")
    public ResponseEntity<?> detalleInscripcion(@RequestParam Long codInscripcion){
        Inscripcion ins = inscripcionService.buscarPorPk(codInscripcion);
        if(ins == null){
            return new ResponseEntity<>(
                    new Mensaje("Error! Datos de inscripción no encontrados.", 404),
                    HttpStatus.NOT_FOUND);
        }
        DetalleInscripcion retorno = new DetalleInscripcion();
        retorno.setProperties(ins);
        return new ResponseEntity<>(retorno,HttpStatus.OK);
    }

    //Inscripción para alumno ya registrado. Procede en caso el alumno haya terminado cursos anteriores, se encuentre habilitado y el curso tenga vacantes disponibles.
    //TODO: SPT3 - R.C MODIFICAR EN FRONT.
    @PostMapping()
    public ResponseEntity<?> inscribirAlumnoExistente(@RequestBody InsertInscripcion ins){
        Alumno alumno = alumnoService.getPorCod(ins.getCodAlumno());
        Seccion seccion = seccionService.obtenerSeccion(ins.getCodSeccion());
        if(alumno == null){
            return new ResponseEntity<>(new Mensaje("Ups! Alumno no encontrado o deshabilitado.",400),HttpStatus.BAD_REQUEST);
        }
        if(seccion == null){
            return new ResponseEntity<>(new Mensaje("Ups! Seccion no encontrado!",400),HttpStatus.BAD_REQUEST);
        }
        if(!alumno.isActivo()){
            return new ResponseEntity<>(new Mensaje("Ups! Alumno deshabilitado.",400),HttpStatus.BAD_REQUEST);
        }
        boolean seccionVacia = seccionService.vacantesDisponibles(seccion.getCodSeccion());
        if(!seccionVacia){
            return new ResponseEntity<>(new Mensaje("Error! La sección seleccionada no cuenta con vacantes disponibles.",400),
                    HttpStatus.BAD_REQUEST);
        }
        List<Inscripcion> inscripcionesAlumno = inscripcionService.listarPorAlumno(alumno.getCodAlumno());
        boolean cursoEnProceso = false;
        if(!inscripcionesAlumno.isEmpty()){
            int c = 0;
            for(Inscripcion i: inscripcionesAlumno){
                if(i.getFechaTerminado() == null){
                    c++;
                }
            }
            if(c > 0){
                cursoEnProceso = true;
            }
        }
        if(cursoEnProceso){
            return new ResponseEntity<>(new Mensaje("Error! El alumno seleccionado debe terminar sus cursos en proceso.",400),
                    HttpStatus.BAD_REQUEST);
        }
        List<Turno> listaTurnos = turnoService.listarTurnoPorCurso(seccion.getCurso().getCodCurso());
        if(listaTurnos.isEmpty()){
            return new ResponseEntity<>(new Mensaje("Error! La sección seleccionada no tiene turno asignados.",400),
                    HttpStatus.BAD_REQUEST);
        }
        //Insert de datos!!
        try{
            Inscripcion i = new Inscripcion(0L,ins.getFechaInscripcion(),
                    null,true,alumno,seccion);
            Inscripcion inscripcionGuardada = inscripcionService.guardar(i);
            emailService.enviarEmailNuevaInscripcion(alumno,seccion,inscripcionGuardada);
            return new ResponseEntity<>(inscripcionGuardada,HttpStatus.CREATED);
        }
        catch(Exception ex){
            return new ResponseEntity<>(new Mensaje("Error! Ha sucedido en error en el guardado de datos.",500),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //TODO: SPT3 - C.H TESTEAR EN FRONT.
    @PutMapping("/retirar")
    @CrossOrigin(allowedHeaders = "*",origins = "*")
    public ResponseEntity<?> retirarAlumno(@RequestParam Long codInscripcion){
        Inscripcion ins = inscripcionService.buscarPorPk(codInscripcion);
        if(ins == null){
            return new ResponseEntity<>(new Mensaje("Ups! Inscripcion no encontrada.",404),HttpStatus.NOT_FOUND);
        }
        ins.setFechaTerminado(ins.getSeccion().getFechaInicio());
        Inscripcion insActualizada = inscripcionService.actualizar(ins);
        return new ResponseEntity<>(insActualizada,HttpStatus.OK);
    }

    public List<InscripcionDTO> convertListToDTO(List<Inscripcion> listaInscripcion){
        List<InscripcionDTO> retorno = new ArrayList<>();
        for(Inscripcion i: listaInscripcion){
            InscripcionDTO ins = new InscripcionDTO();
            ins.setProperties(i);
            retorno.add(ins);
        }
        return retorno;
    }

}
