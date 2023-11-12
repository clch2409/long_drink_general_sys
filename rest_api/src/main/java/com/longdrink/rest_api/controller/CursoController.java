package com.longdrink.rest_api.controller;

import com.longdrink.rest_api.model.Curso;
import com.longdrink.rest_api.model.payload.Mensaje;
import com.longdrink.rest_api.services.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/curso")
public class CursoController {
    @Autowired
    private CursoService cursoService;

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

}
