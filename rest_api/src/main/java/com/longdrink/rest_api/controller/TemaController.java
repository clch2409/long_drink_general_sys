package com.longdrink.rest_api.controller;

import com.longdrink.rest_api.model.Tema;
import com.longdrink.rest_api.model.payload.InsertTema;
import com.longdrink.rest_api.model.payload.Mensaje;
import com.longdrink.rest_api.services.TemaService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tema")
public class TemaController {
    @Autowired
    private TemaService temaService;

    @GetMapping
    public ResponseEntity<?> get(){
        List<Tema> listaTemas = temaService.listarTemas();
        if(listaTemas.isEmpty()){
            return new ResponseEntity<>(new Mensaje("Error! No se encontraron temas.",404),
                    HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(listaTemas,HttpStatus.OK);
    }

    @GetMapping("/por_curso")
    public ResponseEntity<?> getPorCurso(@RequestParam Long codCurso){
        List<Tema> listaTemas = temaService.listarTemasPorCurso(codCurso);
        if(listaTemas.isEmpty()){
            return new ResponseEntity<>(new Mensaje("Error! El curso seleccionado no tiene temas asociados.",404),
                    HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(listaTemas,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> post(@RequestBody InsertTema t){
        if(!(t.getNombre().length() >= 1 && t.getNombre().length() <=30 && t.getFicha().length() >=1)) {
            return new ResponseEntity<>(new Mensaje("Error! Formato de tema incorrecto.",400),
                    HttpStatus.BAD_REQUEST);
        }
        try{
            Tema tema = new Tema();
            BeanUtils.copyProperties(t,tema);
            tema.setCodTema(0L);
            tema = temaService.guardar(tema);
            return new ResponseEntity<>(tema,HttpStatus.OK);
        }
        catch(Exception ex){
            return new ResponseEntity<>(new Mensaje("Error! Ha sucedido en error en el guardado de datos.",500),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
