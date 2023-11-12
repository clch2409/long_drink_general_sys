package com.longdrink.rest_api.controller;

import com.longdrink.rest_api.model.Pago;
import com.longdrink.rest_api.model.payload.Mensaje;
import com.longdrink.rest_api.services.DetallePagoService;
import com.longdrink.rest_api.services.PagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pago")
public class PagoController {

    @Autowired
    private PagoService pagoService;
    @Autowired
    private DetallePagoService detallePagoService;

    @GetMapping
    public ResponseEntity<?> listarPagosAlumno(@RequestParam Long codAlumno){
        List<Pago> listaPagos = pagoService.listarPagosAlumno(codAlumno);
        if(listaPagos.isEmpty()){
            return new ResponseEntity<>(new Mensaje("Ups! El alumno no registra pagos en la institución.",404), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(listaPagos,HttpStatus.OK);
    }

    //TODO: Nuevo pago, actualizar y cambiar estado -- Incluye DetallePago en una sola solicitud.
}
