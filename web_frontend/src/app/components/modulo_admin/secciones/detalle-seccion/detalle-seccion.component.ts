import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Inscripcion } from 'src/app/models/inscripcion.model';
import { Seccion } from 'src/app/models/seccion.model';
import { AlumnoService } from 'src/app/services/alumno.service';
import { InscripcionService } from 'src/app/services/inscripcion.service';
import { SeccionService } from 'src/app/services/seccion.service';
import { StorageService } from 'src/app/services/storage.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-detalle-seccion',
  templateUrl: './detalle-seccion.component.html',
  styleUrls: ['./detalle-seccion.component.css']
})
export class DetalleSeccionComponent implements OnInit{
  codSeccion?: number;
  listaInscripciones?: Inscripcion[] = [];
  estadoSeccion = '';
  colorEstado = 'text-dark';
  seccion?: Seccion;
    constructor(
    private storageService: StorageService,
    private seccionService: SeccionService,
    private inscripcionService: InscripcionService,
    private alumnoService: AlumnoService,
    private route: ActivatedRoute,
    private router: Router
  ){}

  ngOnInit(): void {
    this.storageService.comprobarSesion();
    this.storageService.denegarAcceso("ALUMNOyDOCENTE");
    this.codSeccion = this.route.snapshot.params['codsec']
    console.log(this.codSeccion);
    this.obtenerDetalles();
  }

  obtenerDetalles(){
    this.seccionService.getSeccion(this.codSeccion!!).subscribe({
      next: (data) =>{
        this.seccion = data;
        this.obtenerInscripciones();
        this.setEstadoSeccion();
      },
      error: (err) => Swal.fire('Error!','Imposible obtener datos de sección.','error').then((event) =>{
        if(event.dismiss || event.isConfirmed){
          this.router.navigate(['dashboard','secciones']);
        }
      })

    })
  }

  obtenerInscripciones(){
    this.inscripcionService.getInscripcionesPorSeccion(this.codSeccion!!).subscribe({
      next: (data) =>{
        this.listaInscripciones = data;
        this.enriquecerSecciones();
      },
      error: (err) => console.log(err)
    })
  }

  enriquecerSecciones(){
    if(this.listaInscripciones?.length!! >= 1){
      this.listaInscripciones?.forEach((e) =>{
        this.alumnoService.getAlumnoCod(e.alumno).subscribe({
          next: (data) => e.alumno = data,
          error: (err) => console.log(err)
        });
      });
    }
  }

  setEstadoSeccion(){
    let fechaActual = new Date();
    let fechaInicio = new Date(this.seccion?.fechaInicio!!)
    let fechaFinal = new Date(this.seccion?.fechaFinal!!);
    console.log(fechaFinal > fechaActual);
    if(fechaActual < fechaInicio && fechaActual < fechaFinal){
      this.estadoSeccion = "PROGRAMADA";
      this.colorEstado = 'text-warning';
    }
    if(fechaActual > fechaFinal && fechaActual > fechaInicio){
      this.estadoSeccion = "TERMINADA";
      this.colorEstado = 'text-danger'
    }
    if(fechaActual < fechaFinal && fechaActual > fechaInicio){
      this.estadoSeccion = "EN CURSO";
      this.colorEstado = 'text-success';
    }
  }
}
