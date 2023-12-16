import { Component, OnInit } from '@angular/core';
import { Alumno } from 'src/app/models/alumno.model';
import { Pago } from 'src/app/models/pago.model';
import { AlumnoService } from 'src/app/services/alumno.service';
import { PagoService } from 'src/app/services/pago.service';
import { StorageService } from 'src/app/services/storage.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-listado-pagos-alumnos',
  templateUrl: './listado-pagos-alumnos.component.html',
  styleUrls: ['./listado-pagos-alumnos.component.css']
})
export class ListadoPagosAlumnosComponent implements OnInit{
  datosAlumno?: Alumno;
  listaPagos?: Pago[] = [];

  constructor(private storageService: StorageService,
              private alumnoService: AlumnoService,
              private pagoService: PagoService){}

  ngOnInit(): void {
    this.storageService.comprobarSesion();
    this.storageService.denegarAcceso('ALUMNOyDOCENTE');
  }

  cargarDatosAlumno(dniAlum: string){
    if(dniAlum.length == 8){
      this.alumnoService.getAlumnoDni(dniAlum).subscribe({
        next: (data) => {
          this.datosAlumno = data;
          this.cargarPagosAlumno();
        },
        error: (err) => {
          Swal.fire('Ups! Error','Datos del Alumno no encontrados.','error').then((event) =>{
            if(event.dismiss || event.isConfirmed){
              window.location.reload();
            }
          });
        }
      })
    }
    else{ Swal.fire('Ups!','Debe llenar el campo DNI con el formato correcto. Su longitud debe ser de 8 caracteres.','warning'); }
  }

  cargarPagosAlumno(){
    this.pagoService.getPagosAlumno(this.datosAlumno?.codAlumno).subscribe({
      next: (data) =>{
        this.listaPagos = data;
      },
      error: (err) => {
        Swal.fire('Ups! Ha sucedido un error.',err.error.mensaje,'error')
      }
    })
  }


}
