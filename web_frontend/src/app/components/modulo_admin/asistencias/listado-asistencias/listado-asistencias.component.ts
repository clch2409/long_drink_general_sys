import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Inscripcion } from 'src/app/models/inscripcion.model';
import { Seccion } from 'src/app/models/seccion.model';
import { AlumnoService } from 'src/app/services/alumno.service';
import { InscripcionService } from 'src/app/services/inscripcion.service';
import { SeccionService } from 'src/app/services/seccion.service';
import { StorageService } from 'src/app/services/storage.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-listado-asistencias',
  templateUrl: './listado-asistencias.component.html',
  styleUrls: ['./listado-asistencias.component.css']
})
export class ListadoAsistenciasComponent implements OnInit {

  estadoDni = false;
  estadoSeccion = true;
  listaSecciones: Seccion[] = [];
  inscripcionesAlumno: Inscripcion[] = [];
  seccionesAlumno: Seccion[] = [];

  inscripcionesGenerales: Inscripcion[] = [];

  constructor(private storageService: StorageService,
    private seccionService: SeccionService,
    private router: Router, private inscripcionService: InscripcionService,
    private alumnoService: AlumnoService) {}

  ngOnInit(): void {
    this.storageService.comprobarSesion();
    this.storageService.denegarAcceso("ALUMNOyDOCENTE");
    this.llenarSecciones();
  }

  setEstados(tipoBusqueda: string){
    if(tipoBusqueda === '1'){
      this.estadoSeccion = true;
      this.estadoDni = false;
    }
    else{
      this.estadoSeccion = false;
      this.estadoDni = true;
    }
  }

  llenarInscripcionesSeccion(codSeccion: number){
    this.inscripcionService.getInscripcionesPorSeccion(codSeccion).subscribe({
      next: (data) =>{
        this.inscripcionesGenerales = data;
        this.enriquecerInscripciones();
        this.inscripcionesGenerales.forEach((e) =>{
          let conteoFaltas = 0;
          e.asistencias?.forEach((a) =>{
            if(a.estado == 0){
              conteoFaltas +=1;
            }
          })
          e.cantidadFaltas = conteoFaltas;
        });
      },
      error: (err) =>{
        Swal.fire('Ups!',err.error.mensaje,'error');
      }
    })
  }

  enriquecerInscripciones(){
    this.inscripcionesGenerales.forEach((e) =>{
      this.alumnoService.getAlumnoCod(e.alumno!!).subscribe({
        next: (data) =>{
          e.alumno = data;
        },
        error: (err) =>{
          console.log(err);
        }
      });
    });
  }

  llenarSecciones(){ //Combobox Secciones.
    this.seccionService.getSecciones().subscribe({
      next: (data) =>{
        this.listaSecciones = data;
        console.log(this.listaSecciones);
      },
      error: (err) =>{
        Swal.fire("Error!","Imposible recuperar listado de secciones. La visualización de asistencia estara deshabilitada por el momento. Intente mas tarde.","error").then( (result) =>{
          if(result.isDismissed || result.isConfirmed){
            this.router.navigate(['/dashboard','alumnos'])
          }
        });
      }
    })
  }

  llenarInscripcionesAlumno(dniAlum: string){
    this.inscripcionService.getInscripcionesDniAlumno(dniAlum).subscribe({
      next: (data) => {
        this.inscripcionesAlumno = data;
        console.log(this.inscripcionesAlumno);
        this.inscripcionesAlumno.forEach((e) =>{
          this.seccionesAlumno.push(e.seccion!!);
        })
        this.inscripcionesAlumno.forEach((i) =>{
          let conteoFaltas = 0;
          i.asistencias?.forEach((a) =>{
            if(a.estado == 0){
              conteoFaltas++;
            }
          })
          i.cantidadFaltas = conteoFaltas;
        })
      },
      error: (err) => {
        Swal.fire('Ups!',err.error.mensaje,'error');
      }
    })
  }

  visualizarAsistencias(){
    if(this.estadoSeccion == true && this.estadoDni == false){ //Busqueda por alumno.
      this.inscripcionesGenerales.length = 0;
      let dniAlum = (document.getElementById("dniAlumno") as HTMLInputElement).value;
      if(dniAlum.length != 8){
        Swal.fire('Ups!','Debe llenar los datos con formato correcto. El DNI solo puede tener 8 caracteres.','info');
      }
      else{
        this.llenarInscripcionesAlumno(dniAlum);
      }
    }
    else{ //Busqueda por seccion.
      this.inscripcionesAlumno.length = 0;
      let codSeccion = (document.getElementById("seccion") as HTMLInputElement).value;
      this.llenarInscripcionesSeccion(Number(codSeccion));
      console.log(this.inscripcionesGenerales)
    }
  }

  resetear(){
    window.location.reload();
  }

}
