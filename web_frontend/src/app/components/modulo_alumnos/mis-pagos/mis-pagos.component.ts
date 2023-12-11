import { Component, OnInit } from '@angular/core';
import { DetallePago } from 'src/app/models/detalle.pago.model';
import { Inscripcion } from 'src/app/models/inscripcion.model';
import { Pago } from 'src/app/models/pago.model';
import { CursoService } from 'src/app/services/curso.service';
import { InscripcionService } from 'src/app/services/inscripcion.service';
import { PagoService } from 'src/app/services/pago.service';
import { StorageService } from 'src/app/services/storage.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-mis-pagos',
  templateUrl: './mis-pagos.component.html',
  styleUrls: ['./mis-pagos.component.css']
})
export class MisPagosComponent implements OnInit {
  codAlumno?: number;
  listaPagados: Pago[] = [];
  listaPendientes: Pago[] = [];
  listaInscripciones: Inscripcion[] = [];

  constructor(private storageService: StorageService, private inscripcionService: InscripcionService,
              private cursoService: CursoService, private pagoService: PagoService){}
  ngOnInit(): void {
    this.storageService.comprobarSesion();
    this.storageService.denegarAcceso("ADMINISTRADORyDOCENTE");
    this.codAlumno = this.storageService.obtenerUsuario().codAlumno;
    this.getPagos();
    this.getInscripciones();

  }

  getPagos(): void{
    this.pagoService.getPagosAlumno(this.codAlumno).subscribe({
      next: (data) => {
        this.listaPagados = data;
      },
      error: (err) =>{
        //Swal.fire('Ups! Ha sucedido un error','Imposible recuperar los pagos actualmente. Intente de nuevo.','error');
      }
    })
  }

  getInscripciones(): void{
    this.inscripcionService.getInscripcionesPorAlumno(this.codAlumno).subscribe({
      next: (data) =>{
        this.listaInscripciones = data;
        console.log(this.listaInscripciones);
        this.llenarPagosPendientes();
      },
      error: (err) =>{
        console.log(err);
      }
    })
  }

  llenarPagosPendientes(): void{
    let duracionCurso,cuotas,nombreCurso,mensualidad;
    let vencimiento: Date;
    this.listaInscripciones.forEach((e) =>{
      if(e.seccion?.estado == true){
        duracionCurso = e.seccion.curso?.duracion;
        nombreCurso = e.seccion.curso?.nombre;
        vencimiento =  e.seccion.fechaFinal!!;
        mensualidad = e.seccion.curso?.mensualidad;
      }
    });
    cuotas = duracionCurso!! / 4;
    if(cuotas >= 1){
      let mora = 0;
      this.listaPagados.forEach((e) =>{
        if(!e.descripcion?.includes("MATRICULA")){
          let fechaActual = new Date();
          if(fechaActual > vencimiento!!){
            mora = 50;
          }
          let detallePM = [
            new DetallePago({
              codDetallePago: 0,
              concepto: 'MATRICULA',
              monto: 150,
              montoMora: mora,
              subTotal: 150+mora,
            })
          ];
          let pagoMatricula = new Pago({
              codPago: 0,
              descripcion: `MATRICULA - ${nombreCurso!!}`,
              fechaPago: undefined,
              fechaVencimiento: vencimiento!!,
              total: detallePM[0].subTotal,
              estado: false,
              detallePagos: detallePM
            });
          this.listaPendientes.push(pagoMatricula);
        }

        if(!e.descripcion?.includes("CUOTA")){
          let mora = 0;
          let fechaActual = new Date();
          if(fechaActual.getTime() >= new Date(vencimiento).getTime()){
            mora = 50;
          }
          for(let c = 0; c<cuotas!!; c++){
            let detallePM = [
              new DetallePago({
                codDetallePago: c+1,
                concepto: `CUOTA ${c+1}`,
                monto: mensualidad!!,
                montoMora: mora,
                subTotal: mensualidad!!+mora,
              })
            ];
            let pagoMatricula = new Pago({
                codPago: c+1,
                descripcion: `CUOTA ${c+1} - ${nombreCurso!!}`,
                fechaPago: undefined,
                fechaVencimiento: vencimiento!!,
                total: detallePM[0].subTotal,
                estado: false,
                detallePagos: detallePM
              });
            this.listaPendientes.push(pagoMatricula);
          }
        }
      })
    }
  }

  verDetallePendiente(codPago?: number): void{
    console.log(codPago);
    let concepto, monto, montoMora, subTotal, descripcion, total;
    this.listaPendientes.forEach((e) =>{
      if(e.codPago == codPago){
        concepto = e.detallePagos?.[0].concepto;
        monto = e.detallePagos?.[0].monto;
        montoMora = e.detallePagos?.[0].montoMora;
        subTotal = e.detallePagos?.[0].subTotal;
        descripcion = e.descripcion;
        total = e.total;
      }
    });
    if(total != 0){
      Swal.fire({
        title: '',
        icon: 'info',
        html: `<table class="table table-bordered">
        <thead>
          <tr>
            <th colspan="2">Detalle de Pago | Referencia: ${codPago}</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td class="fw-bold">CONCEPTO</td>
            <td class="fw-light">${descripcion}</td>
          </tr>

          <tr>
          <td class="fw-bold">MONTO</td>
          <td class="fw-light">S/. ${monto}</td>
          </tr>
          <tr>
          <td class="fw-bold">MONTO MORA</td>
          <td class="fw-light">S./ ${montoMora}</td>
          </tr>

          <tr>
          <td class="fw-bold">SUBTOTAL</td>
          <td class="fw-light">S./ ${subTotal}</td>
          </tr>

          <tr>
          <td class="fw-bold">TOTAL</td>
          <td class="fw-light">S./ ${total}</td>
          </tr>
        </tbody>
      </table>
        `,
        confirmButtonText: 'CERRAR',
        confirmButtonColor: '#091695'
      });
    }
    else{
      Swal.fire('Ups! Ha sucedido un error','Imposible cargar detalle de pago. Vuelva a Intentarlo.','error');
    }
  }

  verDetallePago(codPago?: number): void{
    let concepto, monto, montoMora, subTotal, descripcion, total;
    this.listaPagados.forEach( (e) =>{
      if(e.codPago == codPago){
        concepto = e.detallePagos?.[0].concepto;
        monto = e.detallePagos?.[0].monto;
        montoMora = e.detallePagos?.[0].montoMora;
        subTotal = e.detallePagos?.[0].subTotal;
        descripcion = e.descripcion;
        total = e.total;
      }
    });
    if(total != 0){
      Swal.fire({
        title: '',
        icon: 'info',
        html: `<table class="table table-bordered">
        <thead>
          <tr>
            <th colspan="2">Detalle de Pago | Referencia: ${codPago}</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td class="fw-bold">CONCEPTO</td>
            <td class="fw-light">${descripcion}</td>
          </tr>

          <tr>
          <td class="fw-bold">MONTO</td>
          <td class="fw-light">S/. ${monto}</td>
          </tr>
          <tr>
          <td class="fw-bold">MONTO MORA</td>
          <td class="fw-light">S./ ${montoMora}</td>
          </tr>

          <tr>
          <td class="fw-bold">SUBTOTAL</td>
          <td class="fw-light">S./ ${subTotal}</td>
          </tr>

          <tr>
          <td class="fw-bold">TOTAL</td>
          <td class="fw-light">S./ ${total}</td>
          </tr>
        </tbody>
      </table>
        `,
        confirmButtonText: 'CERRAR',
        confirmButtonColor: '#091695'
      });
    }
    else{
      Swal.fire('Ups! Ha sucedido un error','Imposible cargar detalle de pago. Vuelva a Intentarlo.','error');
    }
  }
}
