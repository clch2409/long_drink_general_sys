package com.longdrink.rest_api.controller;

import com.longdrink.rest_api.model.Alumno;
import com.longdrink.rest_api.model.DetallePago;
import com.longdrink.rest_api.model.Inscripcion;
import com.longdrink.rest_api.model.Pago;
import com.longdrink.rest_api.model.payload.InsertPago;
import com.longdrink.rest_api.model.payload.Mensaje;
import com.longdrink.rest_api.services.AlumnoService;
import com.longdrink.rest_api.services.DetallePagoService;
import com.longdrink.rest_api.services.InscripcionService;
import com.longdrink.rest_api.services.PagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pago")
public class PagoController {

    @Autowired
    private PagoService pagoService;
    @Autowired
    private DetallePagoService detallePagoService;
    @Autowired
    private AlumnoService alumnoService;
    @Autowired
    private InscripcionService inscripcionService;

    @GetMapping
    public ResponseEntity<?> listarPagosAlumno(@RequestParam Long codAlumno){
        List<Pago> listaPagos = pagoService.listarPagosAlumno(codAlumno);
        if(listaPagos.isEmpty()){
            return new ResponseEntity<>(new Mensaje("Ups! El alumno no registra pagos en la institución.",404), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(listaPagos,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> registrarPago(@RequestBody InsertPago carga){
        Alumno a = alumnoService.getPorCod(carga.getCodAlumno());
        if(a == null){
            return new ResponseEntity<>(new Mensaje("Error! Alumno no encontrado.",404),HttpStatus.NOT_FOUND);
        }
        List<Inscripcion> inscripciones = inscripcionService.listarPorAlumno(a.getCodAlumno());
        if(inscripciones.isEmpty()){
            return new ResponseEntity<>(new Mensaje("Error! Alumno no posee pagos pendientes.",400),HttpStatus.BAD_REQUEST);
        }
        boolean pendientePago = true;
        for(Inscripcion i: inscripciones){
            if(i.getFechaTerminado() != null){
                pendientePago = false;
            }
        }
        if(!pendientePago){
            return new ResponseEntity<>(new Mensaje("Error! Alumno no posee pagos pendientes. Todas sus inscripciones registradas estan finalizadas.",400),HttpStatus.BAD_REQUEST);
        }
        InsertPago limpiarDatos = carga.limpiarDatos();
        boolean datosValidos = limpiarDatos.validarDatos();
        if(!datosValidos){
            return new ResponseEntity<>(new Mensaje("Ups! Datos llenados con formato incorrecto.",400),HttpStatus.BAD_REQUEST);
        }
        //Insert de datos.
        try{
            Pago p = new Pago(0L,limpiarDatos.getFechaPago(),
                    limpiarDatos.getFechaVencimiento(),true,
                    limpiarDatos.getDescripcion(),
                    limpiarDatos.getTotal(),a);
            Pago pagoGuardado = pagoService.guardarPago(p);
            DetallePago dp = new DetallePago(0L,limpiarDatos.getConcepto(), limpiarDatos.getMonto(),limpiarDatos.getMontoMora(),limpiarDatos.getSubTotal(),pagoGuardado);
            DetallePago dpGuardado = detallePagoService.guardarDetallePago(dp);
            return new ResponseEntity<>(limpiarDatos,HttpStatus.CREATED);
        }
        catch(Exception ex){ return new ResponseEntity<>(new Mensaje("Error! Imposible registrar pago. Comuniquese con administración.",500),HttpStatus.INTERNAL_SERVER_ERROR); }
    }

}
