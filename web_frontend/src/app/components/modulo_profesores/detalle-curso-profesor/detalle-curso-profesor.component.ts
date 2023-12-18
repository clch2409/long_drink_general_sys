import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Alumno } from 'src/app/models/alumno.model';
import { Curso } from 'src/app/models/curso.model';
import { Inscripcion } from 'src/app/models/inscripcion.model';
import { Seccion } from 'src/app/models/seccion.model';
import { Tema } from 'src/app/models/tema.model';
import { Turno } from 'src/app/models/turno.model';
import { AlumnoService } from 'src/app/services/alumno.service';
import { CursoService } from 'src/app/services/curso.service';
import { InscripcionService } from 'src/app/services/inscripcion.service';
import { SeccionService } from 'src/app/services/seccion.service';
import { StorageService } from 'src/app/services/storage.service';
import Swal from 'sweetalert2';
const API = "http://localhost:8080/api/v1";

@Component({
  selector: 'app-detalle-curso-profesor',
  templateUrl: './detalle-curso-profesor.component.html',
  styleUrls: ['./detalle-curso-profesor.component.css']
})
export class DetalleCursoProfesorComponent implements OnInit {
  codProfesor: number = 0;
  codCurso: number = 0;
  curso: Curso = new Curso;
  seccion?: Seccion;
  inscripciones: Inscripcion[] = [];
  listadoAlumno: Alumno[] = [];
  listadoTurnos: Turno[] = [];
  listadoTemas?: Tema[] = [];


  constructor(private alumnoService: AlumnoService, private inscripcionService: InscripcionService,
              private cursoService: CursoService, private route: ActivatedRoute,
              private seccionService: SeccionService) { }
  ngOnInit(): void {
    this.codProfesor = this.route.snapshot.params["codprof"];
    this.codCurso = this.route.snapshot.params["codcur"];
    this.getInscripcionPorCurso(this.codCurso)

    this.getSeccion(this.codCurso);
  }

  getInscripcionPorCurso(codCurso: number): void {
    this.inscripcionService.getInscripcionesPorSeccion(codCurso).subscribe({
      next: (data: Inscripcion[]) => {
        this.inscripciones = data
        this.enriquecerInscripciones();
        console.log(this.inscripciones);
      },
      error: (err) => {
        this.inscripciones = []
        console.log(err);
      }
    })
  }

  enriquecerInscripciones(){
    if(this.inscripciones.length >=1){
      this.inscripciones.forEach((e) =>{
        this.alumnoService.getAlumnoCod(e.alumno).subscribe({
          next: (data) => e.alumno = data,
          error: (err) => console.log(err)
        })
      })
    }
  }

  getSeccion(codSeccion: number): void{
    this.seccionService.getSeccion(codSeccion).subscribe({
      next: (data) => {
        this.seccion = data;
      },
      error: (err) =>{
        console.log(err);
      }
    })
  }

  getCursoCod(codCurso: number | any): any {
    return this.cursoService.getCurso(codCurso);
  }

  getAlumnoCod(codAlumno: number | any): any {
    return this.alumnoService.getAlumnoCod(codAlumno);
  }

  descargarGuia(codGuia: number | any): void {
    window.location.href = API + `/tema/descargar_guia?codTema=${codGuia}`;
    Swal.fire({
      title: "Descargando guía...",
      text: "Estas descargando la guía de estudio: " + codGuia,
      icon: "info"
    });
  }
}
