import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Curso } from 'src/app/models/curso.model';
import { Inscripcion } from 'src/app/models/inscripcion.model';
import { Alumno } from 'src/app/models/alumno.model';
import { AlumnoService } from 'src/app/services/alumno.service';
import { InscripcionService } from 'src/app/services/inscripcion.service';
import { StorageService } from 'src/app/services/storage.service';
import { CursoService } from 'src/app/services/curso.service';

@Component({
  selector: 'app-listado-inscripciones',
  templateUrl: './listado-inscripciones.component.html',
  styleUrls: ['./listado-inscripciones.component.css']
})
export class ListadoInscripcionesComponent {
  sesionIniciada = false;
  rol = '';
  inscripciones: Inscripcion[] = [];
  cursos: Curso[] = [];
  
  constructor(private storageService: StorageService, private router: Router, private inscripcionService: InscripcionService, private cursoService: CursoService, private alumnoService: AlumnoService) { }

  ngOnInit(): void {
    this.comprobarSesion();
    this.getInscripcionesPendientes();
  }

  getInscripcionesPendientes(): void {
    this.inscripcionService.getInscripcionesPendientes().subscribe({
      next: (data) => {
        this.inscripciones = data;
        this.enriquecerInscripciones();
      },
      error: (err) => { console.log(err); }
    });
  }

  enriquecerInscripciones(): void {
    for (const inscripcion of this.inscripciones) {
      if (inscripcion.inscripcionPk) {
        this.getAlumnoCod(inscripcion.inscripcionPk.codAlumno).subscribe((alumno: Alumno) => {
          inscripcion.alumno = alumno;
        });
        this.getCursoCod(inscripcion.inscripcionPk.codCurso).subscribe((curso: Curso) => {
          inscripcion.curso = curso;
        });
      }
    }
  }

  getCursoCod(codCurso: number): any {
    return this.cursoService.getCurso(codCurso);
  }
  

  getInscripciones(): void {
    this.inscripcionService.getInscripciones().subscribe({
      next: (data) => {
        this.inscripciones = data;
        console.log(this.inscripciones);
      },
      error: (err) => { console.log(err); }
    });
  }

  getInscripcionesAceptadas(): void {
    this.inscripcionService.getInscripcionesAceptadas().subscribe({
      next: (data) => {
        this.inscripciones = data;
        console.log(this.inscripciones);
      },
      error: (err) => { console.log(err); }
    });
  }

  getInscripcionesAlumno(codAlumno: number): void {
    this.inscripcionService.getInscripcionesPorAlumno(codAlumno).subscribe({
      next: (data) => {
        this.inscripciones = data;
        console.log(this.inscripciones);
      },
      error: (err) => { console.log(err); }
    });
  }

  getInscripcionesCurso(codCurso: number): void {
    this.inscripcionService.getInscripcionesPorCurso(codCurso).subscribe({
      next: (data) => {
        this.inscripciones = data;
        console.log(this.inscripciones);
      },
      error: (err) => { console.log(err); }
    });
  }

  getAlumnoCod(codAlum: number): any {
    return this.alumnoService.getAlumnoCod(codAlum);
  }

  comprobarSesion(): void {
    if (this.storageService.sesionIniciada()) {
      this.sesionIniciada = true;
      this.rol = this.storageService.obtenerUsuario().rol;
    } else {
      this.router.navigate(['/']);
    }
  }
}