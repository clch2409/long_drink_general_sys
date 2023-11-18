package com.longdrink.rest_api.controller;

import com.longdrink.rest_api.model.Alumno;
import com.longdrink.rest_api.model.payload.EditarAlumno;
import com.longdrink.rest_api.model.payload.Mensaje;
import com.longdrink.rest_api.services.AlumnoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/alumno")
public class AlumnoController {
    @Autowired
    private AlumnoService alumnoService;

    @GetMapping()
    public ResponseEntity<?> get(){
        List<Alumno> listaAlumnos = alumnoService.listarAlumnos();
        if(listaAlumnos.isEmpty()){
            return new ResponseEntity<>(new Mensaje("Error! No se encontraron alumnos.",404),
                    HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(listaAlumnos, HttpStatus.OK);
    }

    @GetMapping("/activos")
    public ResponseEntity<?> getActivos(){
        List<Alumno> listaAlumnos = alumnoService.listarAlumnosActivos();
        if(listaAlumnos.isEmpty()){
            return new ResponseEntity<>(new Mensaje("Error! No se encontraron alumnos activos.",404),
                    HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(listaAlumnos, HttpStatus.OK);
    }

    @GetMapping("/{dni}")
    public ResponseEntity<?> getPorDNI(@PathVariable String dni){
        Alumno alumno = alumnoService.getPorDNI(dni);
        if(alumno == null){
            return new ResponseEntity<>(new Mensaje("Error! El alumno con dni: "+dni+" no fue encontrado.",404),
                    HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(alumno,HttpStatus.OK);
    }

    @GetMapping("/por_cod")
    public ResponseEntity<?> getPorCod(@RequestParam Long codAlum){
        Alumno alumno = alumnoService.getPorCod(codAlum);
        if (alumno == null){
            return new ResponseEntity<>(new Mensaje("Error! El alumno con código: "+codAlum+" no fue encontrado.",404),
                    HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(alumno,HttpStatus.OK);
    }

    //Editar alumno: Permite actualizar nombre/apellidos/telefono/activo.
    @PutMapping()
    @CrossOrigin(allowedHeaders = "*",origins = "*")
    public ResponseEntity<?> editarAlumno(@RequestBody EditarAlumno carga){
        Alumno a = alumnoService.getPorCod(carga.getCodAlumno());
        if(a == null){
            return new ResponseEntity<>(new Mensaje("Ups! Alumno no encontrado.",404),HttpStatus.NOT_FOUND);
        }
        EditarAlumno limpiarDatos = carga.limpiarDatos();
        boolean datosValidos = limpiarDatos.validarDatos();
        if(!datosValidos){
            return new ResponseEntity<>(new Mensaje("Ups! Datos de formato incorrecto ingresados.",400),HttpStatus.BAD_REQUEST);
        }
        //Update de datos!!
        try{
            BeanUtils.copyProperties(limpiarDatos,a);
            Alumno alumnoActualizado = alumnoService.actualizar(a);
            return new ResponseEntity<>(alumnoActualizado,HttpStatus.OK);
        }
        catch(Exception ex){ return new ResponseEntity<>(new Mensaje("Error! Imposible actualizar datos. Intente nuevamente.",500),HttpStatus.INTERNAL_SERVER_ERROR); }
    }

    @DeleteMapping()
    public ResponseEntity<?> eliminar(@RequestParam Long codAlumno){
        boolean estado = alumnoService.eliminar(codAlumno);
        if(!estado){
            return new ResponseEntity<>(new Mensaje("Error! No se pudo eliminar el alumno de código: " + codAlumno,404),
                    HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new Mensaje("Alumno de código: "+codAlumno+" eliminado correctamente.",201),HttpStatus.OK);
    }

}
