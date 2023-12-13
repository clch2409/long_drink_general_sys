import { AfterViewInit, Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { MatSidenav } from '@angular/material/sidenav';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import { StorageService } from 'src/app/services/storage.service';
import { Toast } from 'bootstrap';
import Swal from 'sweetalert2';
import { InscripcionService } from 'src/app/services/inscripcion.service';
import { Inscripcion } from 'src/app/models/inscripcion.model';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit, AfterViewInit {
  constructor(private storageService: StorageService,private router: Router, private inscripcionService: InscripcionService) { }
  rol = ''
  inscripciones: Inscripcion[] = [];
  ngOnInit(): void {
    this.storageService.comprobarSesion();
    this.rol = this.storageService.obtenerRol();
  }

  ngAfterViewInit(){
    if(this.rol === "ALUMNO"){
      const toast = document.getElementById('toastAsistencia');
      const toastAsistencia = Toast.getOrCreateInstance(toast!!);
      this.llenarInscripciones(this.storageService.obtenerUsuario().codAlumno,toastAsistencia);
    }
  }

  llenarInscripciones(codAlumno: number, toastAsistencia: any){
    this.inscripcionService.getInscripcionesPorAlumno(codAlumno).subscribe({
      next: (data) =>{
        this.inscripciones = data;
        if(this.inscripciones.length > 0){
          let asistenciaVisible = false;
          this.inscripciones.forEach((e) =>{
            if(e.fechaTerminado == null || e.seccion?.estado == true){
              let diaActual = new Date().toLocaleDateString('es-ES',{
                weekday: 'long',
              });
              let frecuencia = e.seccion?.curso?.frecuencia;
              //console.log(frecuencia)
              //console.log(diaActual);
              switch(frecuencia){
                case 'Diario':{
                  if(diaActual != 'sábado' && diaActual != 'domingo'){
                    asistenciaVisible = true;
                  }
                  break;
                }
                case 'Interdiario':{
                  if(diaActual === 'lunes' || diaActual === 'miércoles' || diaActual === 'viernes' ){
                    asistenciaVisible = true;
                  }
                  break;
                }
                case 'Sabados - Domingos':{
                  if(diaActual === 'sábado' || diaActual === 'domingo'){
                    asistenciaVisible = true;
                  }
                  break;
                }
                default:{
                  asistenciaVisible = false;
                }
              }
            }
          });
          if(asistenciaVisible){
            toastAsistencia.show();
          }
        }
      },
      error: (err) => {
        console.log(err);
      }
    })
  }

  cerrarSesion(): void {
    this.storageService.cerrarSesion();
  }

}

