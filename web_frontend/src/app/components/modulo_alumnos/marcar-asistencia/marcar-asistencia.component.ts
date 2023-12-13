import { DatePipe, formatDate } from '@angular/common';
import { AfterViewInit, Component, OnInit } from '@angular/core';
import { Asistencia } from 'src/app/models/asistencia.model';
import { Inscripcion } from 'src/app/models/inscripcion.model';
import { AsistenciaService } from 'src/app/services/asistencia.service';
import { InscripcionService } from 'src/app/services/inscripcion.service';
import { StorageService } from 'src/app/services/storage.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-marcar-asistencia',
  templateUrl: './marcar-asistencia.component.html',
  styleUrls: ['./marcar-asistencia.component.css']
})
export class MarcarAsistenciaComponent implements OnInit {
  codAlumno?: number;

  inscripciones: Inscripcion[] = [];

  asistenciaMarcada?: Asistencia;

  txtBoton = 'MARCAR ASISTENCIA';

  estadoBoton = false;

  colorBoton = 'btn-success';

  objetoAsistencia: any;

  constructor(private storageService: StorageService, private inscripcionService: InscripcionService, private asistenciaService: AsistenciaService) { }

  ngOnInit(): void {
    this.storageService.comprobarSesion();
    this.storageService.denegarAcceso("ADMINISTRADORyDOCENTE");
    this.codAlumno = this.storageService.obtenerUsuario().codAlumno;
    this.llenarInscripciones(this.codAlumno!!);
  }

  marcarAsistencia(codIns: number){
    //let fecha = new Date().toLocaleDateString('es-PE');
    let hora = new Date().toLocaleTimeString('es-PE');
    let fecha = formatDate(new Date(),'yyyy-MM-dd','en-US')
    this.asistenciaService.marcarAsistencia(codIns,fecha,hora).subscribe({
      next: (data) =>{
        this.asistenciaMarcada = data;
        Swal.fire('Información','Asistencia marcada con exito!','info').then((result) =>{
          if(result.dismiss || result.isConfirmed){
            window.location.reload();
          }
        });
      },
      error: (err) =>{
        console.log(err);
      }
    });

  }

  llenarInscripciones(codAlumno: number) {
    this.inscripcionService.getInscripcionesPorAlumno(codAlumno).subscribe({
      next: (data) => {
        this.inscripciones = data;
        this.filtrarInscripciones();
      },
      error: (err) => {

      }
    });
  }

  filtrarInscripciones() {
    if (this.inscripciones.length > 0) {
      let asistenciaVisible = false;
      this.inscripciones.forEach((e) => {
        if (e.fechaTerminado == null && e.seccion?.estado == true) {
          let diaActual = new Date().toLocaleDateString('es-PE',{
            weekday: 'long',
          });
          let frecuencia = e.seccion?.curso?.frecuencia;
          switch(frecuencia){ //Filtrado de listado de inscripciones, se "eliminan" las que no pueden marcar asistencia el día.
            case 'Diario':{
              if(diaActual === 'sábado' || diaActual === 'domingo'){ //Diario no asiste sabados ni domingos.
                const index = this.inscripciones.indexOf(e);
                if(index > -1){
                  this.inscripciones.splice(index,1);
                }
              }
              break;
            }
            case 'Interdiario':{
              if(diaActual === 'martes' || diaActual === 'jueves' || diaActual === 'sábado' || diaActual === 'domingo' ){ //Interdiario asiste lunes, miercoles y viernes.
                const index = this.inscripciones.indexOf(e);
                if(index > -1){
                  this.inscripciones.splice(index,1);
                }
              }
              break;
            }
            case 'Sabados - Domingos':{
              if(diaActual != 'sábado' && diaActual != 'domingo'){ //interdiario no asiste resto de días de la semana.
                const index = this.inscripciones.indexOf(e);
                if(index > -1){
                  this.inscripciones.splice(index,1);
                }
              }
              break;
            }
          }
        }
        console.log(this.inscripciones);
      });
      let fecha = formatDate(new Date(),'yyyy-MM-dd','en-US')
      this.asistenciaService.comprobarAsistencia(this.inscripciones[0].codInscripcion!!,fecha).subscribe({
        next: (data) =>{
          this.objetoAsistencia = data;
          this.txtBoton = "ASISTENCIA MARCADA";
          this.estadoBoton = true;
          this.colorBoton = 'btn-danger';
        },
        error: (err) =>{
          this.txtBoton = "MARCAR ASISTENCIA";
          this.estadoBoton = false;
          this.colorBoton = 'btn-success'
        }
      })
    }
    }
  }




