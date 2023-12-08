import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Alumno } from 'src/app/models/alumno.model';
import { Curso } from 'src/app/models/curso.model';
import { Inscripcion } from 'src/app/models/inscripcion.model';
import { Tema } from 'src/app/models/tema.model';
import { Turno } from 'src/app/models/turno.model';
import { AlumnoService } from 'src/app/services/alumno.service';
import { CursoService } from 'src/app/services/curso.service';
import { InscripcionService } from 'src/app/services/inscripcion.service';
import { StorageService } from 'src/app/services/storage.service';
import Swal from 'sweetalert2';
const API = "http://localhost:8080/api/v1";

@Component({
  selector: 'app-detalle-curso-profesor',
  templateUrl: './detalle-curso-profesor.component.html',
  styleUrls: ['./detalle-curso-profesor.component.css']
})
export class DetalleCursoProfesorComponent implements OnInit{
  codProfesor : number = 0;
  codCurso : number = 0;
  curso : Curso = new Curso;
  inscripciones : Inscripcion[] = [];
  listadoAlumno : Alumno[] = [];
  listadoTurnos : Turno[] = [];
  listadoTemas? : Tema[] = [];


  constructor (private alumnoService : AlumnoService, private inscripcionService : InscripcionService, private cursoService : CursoService, private route : ActivatedRoute){}
  ngOnInit(): void {
    this.codProfesor = this.route.snapshot.params["codprof"];
    this.codCurso = this.route.snapshot.params["codcur"];
    this.getInscripcionPorCurso(this.codCurso)
    this.getCursoCod(this.codCurso).subscribe({
      next: (curso : Curso) =>{
        this.curso = curso
      }
    })
  }

  getInscripcionPorCurso(codCurso : number) : void {
    try{
      this.inscripcionService.getInscripcionesPorCurso(codCurso).subscribe({
        next: (data : Inscripcion[]) => {
          this.inscripciones = data
          console.log(this.inscripciones);
        }
      })
    }catch(err){
      console.log(err)
      this.inscripciones = []
    }
  }

  getCursoCod(codCurso: number | any): any {
    return this.cursoService.getCurso(codCurso);
  }

  getAlumnoCod(codAlumno: number | any): any {
    return this.alumnoService.getAlumnoCod(codAlumno);
  }

  descargarGuia(codGuia: number | any): void{
    window.location.href=API+`/tema/descargar_guia?codTema=${codGuia}`;
    Swal.fire({
      title: "Descargando guía...",
      text: "Estas descargando la guía de estudio: " + codGuia,
      icon: "info"
    });
  }
}
