package com.longdrink.rest_api.controller;

import com.fasterxml.jackson.databind.util.BeanUtil;
import com.longdrink.rest_api.model.Turno;
import com.longdrink.rest_api.model.payload.InsertTurno;
import com.longdrink.rest_api.model.payload.Mensaje;
import com.longdrink.rest_api.services.TurnoService;
import org.apache.coyote.Response;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/turno")
public class TurnoController {
    @Autowired
    private TurnoService turnoService;

    @GetMapping
    public ResponseEntity<?> get(){
        List<Turno> listaTurnos = turnoService.listarTurnos();
        if(listaTurnos.isEmpty()){
            return new ResponseEntity<>(new Mensaje("Error! No se encontraron turnos.",404),
                    HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(listaTurnos,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> post(@RequestBody InsertTurno t){
        t.setCodTurno(0L);
        Turno turno = new Turno();
        if (!(t.getNombre().length() >=1 && t.getNombre().length() <=25)){
            return new ResponseEntity<>(new Mensaje("Error! Formato de turno incorrecto.",400),
                    HttpStatus.BAD_REQUEST);
        }
        try{
            BeanUtils.copyProperties(t,turno);
            turnoService.guardar(turno);
            return new ResponseEntity<>(new Mensaje("Exito! Turno guardado correctamente.",201),
                    HttpStatus.CREATED);
        }
        catch(Exception ex){
            return new ResponseEntity<>(new Mensaje("Error! Ha sucedido en error en el guardado de datos.",500),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
