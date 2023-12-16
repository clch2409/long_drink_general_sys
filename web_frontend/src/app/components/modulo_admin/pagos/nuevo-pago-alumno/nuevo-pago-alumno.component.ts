import { formatDate } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { Alumno } from 'src/app/models/alumno.model';
import { DetallePago } from 'src/app/models/detalle.pago.model';
import { Inscripcion } from 'src/app/models/inscripcion.model';
import { Pago } from 'src/app/models/pago.model';
import { AlumnoService } from 'src/app/services/alumno.service';
import { InscripcionService } from 'src/app/services/inscripcion.service';
import { PagoService } from 'src/app/services/pago.service';
import { StorageService } from 'src/app/services/storage.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-nuevo-pago-alumno',
  templateUrl: './nuevo-pago-alumno.component.html',
  styleUrls: ['./nuevo-pago-alumno.component.css']
})
export class NuevoPagoAlumnoComponent implements OnInit {
  /*
  * Entendía este código hace pocas horas, cuando lo estaba escribiendo.
  * Actualmente, solo Dios y el compilador de TypeScript saben como funciona...
  * -->> NOTA: Luego de buscar un alumno por DNI, SI NO tiene pagos pendientes debes dar click en RESETEAR.
  * para que vuelva a cargar el formulario y recien ahi buscar a otro alumno.
  * De no hacerse explota el formulario entero y comienza a mostrar los pagos que no son.
  * Y por NADA del mundo intenten poner aqui un DNI de un alumno que tenga un curso terminado y uno en proceso
  * muy probablemente suceda un terremoto de grado 9.
  */
  inscripcionesAlumno: Inscripcion[] = [];
  estadoSelectCurso = true;
  datosAlumno?: Alumno; //Por obra del señor esto tambien trae los pagos!
  apellidosAlumno = "-------";
  telefonoAlumno = "-------";
  codInscripcionSeleccionada = 0;
  listaPendientes?: Pago[] = [];

  frmSubtotal = 0.00;
  frmMora = 0.00;
  frmTotal = 0.00;
  frmFechaPago = new Date();
  frmVencimiento = new Date();
  frmNombreCurso = '';
  frmNombrePago = '';
  estadoBtnPago = true;
  pagoRegistrado?: Pago;
  conteoBuscar = 0;

  frmSelectPago = '';

  constructor(private storageService: StorageService,
              private inscripcionService: InscripcionService,
              private alumnoService: AlumnoService,
              private pagoService: PagoService) { }

  ngOnInit(): void {
    this.storageService.comprobarSesion();
    this.storageService.denegarAcceso('ALUMNOyDOCENTE');
  }

  /*
  * Si el curso ya termino se lo elimina de los pagos pendientes.
  * Debe ser rediseñado con la implementación de Tareas en la API y cambios en la base de datos.
  * Mejor aun, migrar la API a Laravel o Express con uso de JWT / sesiones para autenticación
  * -->> Cada vez que un alumno se inscriba, generar pagos pendientes.
  * -->> Para pagar simplemente obtener pagos pendientes y cambiarles el estado en la BD al pagar.
  * -->> Ups.....
  */
  getInscripcionesAlumno(dniAlum: string) {
    this.listaPendientes = [];
    this.inscripcionesAlumno = [];
    if (dniAlum.length != 8) {
      Swal.fire('Error!', 'Debe llenar los datos con formato correcto. El DNI debe tener una longitud de 8.', 'warning');
    } else {
      this.getDatosAlumno(dniAlum);
      this.inscripcionService.getInscripcionesDniAlumno(dniAlum).subscribe({
        next: (data) => {
          //this.inscripcionesAlumno.length = 0; //TODO: Si algo falla es aca!
          this.inscripcionesAlumno = data;
          console.log(this.inscripcionesAlumno);
          if (this.inscripcionesAlumno.length >= 1) {
            this.inscripcionesAlumno.forEach((e) => {
              if (e.fechaTerminado != null) {
                const index = this.inscripcionesAlumno.indexOf(e);
                if (index > -1) {
                  this.inscripcionesAlumno.splice(index, 1);
                }
              }
            })
            if (this.inscripcionesAlumno.length >= 1) {
              this.estadoSelectCurso = false;
              this.llenarPagosDisponibles();
            }
            else {
              Swal.fire('Ups!', 'El alumno ingresado no tiene pagos pendientes.', 'info').then((event) => {
                if (event.dismiss || event.isConfirmed) {
                  window.location.reload();
                }
              })
            }
          }

        },
        error: (err) => {
          Swal.fire('Ups! Ha sucedido un error.', err.error.mensaje, 'error').then((event) => {
            if (event.dismiss || event.isConfirmed) {
              window.location.reload();
            }
          });
        }
      })
    }
  }

  getDatosAlumno(dniAlum: string) {
    this.alumnoService.getAlumnoDni(dniAlum).subscribe({
      next: (data) => {
        this.datosAlumno = data;
        this.apellidosAlumno = this.datosAlumno.apellidoPaterno + " " + this.datosAlumno.apellidoMaterno;
        this.telefonoAlumno = this.datosAlumno.telefono!!;
      },
      error: (err) => console.log('Datos de alumno no encontrados.')
    })
  }


  llenarPagosDisponibles() {
    //this.listaPendientes = []; //Limpiar lista cada vez que se llenan pagos disponibles.
    let cantidadCuotas = 1;
    let vencimiento: Date;
    let mora = 0;
    let nombreCurso = '';
    let montoPago = 0;
    this.inscripcionesAlumno.forEach((e) => {
      if (Number(e.codInscripcion) != this.codInscripcionSeleccionada) {
        cantidadCuotas = e.seccion?.curso?.duracion!! / 4;
        vencimiento = e.seccion?.fechaFinal!!;
        nombreCurso = e.seccion?.curso?.nombre!!;
        montoPago = e.seccion?.curso?.mensualidad!!;
      }
    })
    this.datosAlumno?.pagos?.forEach((p) => {
      if (!p.descripcion?.includes("MATRICULA")) {
        let fechaActual = new Date();
        if (fechaActual > vencimiento!!) {
          mora = 50;
        }
        let pagoMatricula = new Pago({
          codPago: 0,
          descripcion: `MATRICULA`,
          fechaPago: undefined,
          fechaVencimiento: vencimiento!!,
          total: 150 + mora,
          auxSubTotal: 150,
          estado: false,
        });
        this.listaPendientes!!.push(pagoMatricula);
      }


      if (!p.descripcion?.includes("CUOTA")) {
        let mora = 0;
        let fechaActual = new Date();
        if (fechaActual.getTime() >= new Date(vencimiento).getTime()) {
          mora = 50;
        }
        for (let c = 0; c < cantidadCuotas!!; c++) {
          let pagoCuota = new Pago({
            codPago: c + 1,
            descripcion: `CUOTA ${c + 1}`,
            fechaPago: undefined,
            fechaVencimiento: vencimiento!!,
            total: montoPago + mora,
            auxSubTotal: montoPago,
            estado: false,
          });
          this.listaPendientes!!.push(pagoCuota);
        }
      }
    })

    //Llenar pagos pendientes en caso de que el alumno no tenga pagos....
    if (this.datosAlumno?.pagos?.length!! <= 0) {
      this.inscripcionesAlumno.forEach((e) => {
        if(e.codInscripcion == this.codInscripcionSeleccionada) {
          cantidadCuotas = e.seccion?.curso?.duracion!! / 4;
          vencimiento = e.seccion?.fechaFinal!!;
          nombreCurso = e.seccion?.curso?.nombre!!;
          montoPago = e.seccion?.curso?.mensualidad!!;
        }
      });

      let mora = 0;
      let fechaActual = new Date();
      if (fechaActual > vencimiento!!) {
        mora = 50;
      }
      let pagoMatricula = new Pago({
        codPago: 0,
        descripcion: `MATRICULA`,
        fechaPago: undefined,
        fechaVencimiento: vencimiento!!,
        total: 150 + mora,
        auxSubTotal: 150,
        estado: false,
      });
      this.listaPendientes!!.push(pagoMatricula);

      if (fechaActual.getTime() >= new Date(vencimiento!!).getTime()) {
        mora = 50;
      }
      for (let c = 0; c < cantidadCuotas!!; c++) {
        let pagoCuota = new Pago({
          codPago: c + 1,
          descripcion: `CUOTA ${c + 1}`,
          fechaPago: undefined,
          fechaVencimiento: vencimiento!!,
          auxSubTotal: montoPago,
          total: montoPago + mora,
          estado: false,
        });
        this.listaPendientes!!.push(pagoCuota);
      }

    }
  }

  setInscripcionSeleccionada(codIns: string) {
    this.codInscripcionSeleccionada = Number(codIns);
  }

  setValoresPago(desc: string){
    this.listaPendientes?.forEach((e) =>{
      if(e.descripcion == desc){
        this.frmTotal = e.total!!;
        this.frmVencimiento = e.fechaVencimiento!!;
        this.frmSubtotal = e.auxSubTotal!!;
        this.frmNombrePago = e.descripcion;
        this.frmNombreCurso = this.inscripcionesAlumno[0].seccion?.curso?.nombre!!;
      }
    })
    if(this.frmTotal >= 1){
      this.estadoBtnPago = false;
    }
  }


  enviarPago() {
    let fechaPago = formatDate(new Date(), 'yyyy-MM-dd', 'en-US');
    let vencimiento = formatDate(this.frmVencimiento, 'yyyy-MM-dd', 'en-US');
    const datosPago = {
      codAlumno: this.datosAlumno?.codAlumno,
      descripcion: this.frmNombrePago + " - " + this.frmNombreCurso,
      fechaPago: new Date(),
      fechaVencimiento: vencimiento,
      total: this.frmTotal,
      concepto: this.frmNombrePago,
      monto: this.frmTotal,
      montoMora: this.frmMora,
      subTotal: this.frmSubtotal
    }
    if(datosPago.codAlumno != 0 && datosPago.total >= 1){
      this.pagoService.nuevoPago(datosPago).subscribe({
        next: (data) => Swal.fire('Datos Guardados','Pago registrado con exito!','info').then((event) => { if(event.dismiss || event.isConfirmed){ this.resetearForm(); } }),
        error: (err) => Swal.fire('Ups!',err.error.mensaje,'error')
      })
    }
    console.log(datosPago);
  }

  pendientesSize(): boolean{
    if(this.listaPendientes == undefined || this.listaPendientes == null || this.listaPendientes.length <= 0){
      return true;
    }
    else if(this.listaPendientes.length >=1 ){
      return false;
    }
    else{
      return false;
    }
  }

  resetearForm(){
    window.location.reload();
  }


}
