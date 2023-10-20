import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Curso } from 'src/app/models/curso.model';
import { Inscripcion } from 'src/app/models/inscripcion.model';
import { Alumno } from 'src/app/models/alumno.model';
import { AlumnoService } from 'src/app/services/alumno.service';
import { InscripcionService } from 'src/app/services/inscripcion.service';
import { StorageService } from 'src/app/services/storage.service';

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
  
  constructor(private storageService: StorageService,private router: Router,private inscripcionService: InscripcionService, private alumnoService: AlumnoService) { }
  ngOnInit(): void {
      this.comprobarSesion();
      this.getInscripcionesPendientes();
  }

  getInscripcionesPendientes(): void{
    this.inscripcionService.getInscripcionesPendientes().subscribe({
      next: (data) => {
        this.inscripciones = data;
        console.log(this.inscripciones);
      },
      error: (err) =>{ console.log(err); }
    });
  }

  getInscripciones(): void{
    this.inscripcionService.getInscripciones().subscribe({
      next: (data) => {
        this.inscripciones = data;
        console.log(this.inscripciones);
      },
      error: (err) =>{ console.log(err); }
    });
  }

  getInscripcionesAceptadas(): void {
    this.inscripcionService.getInscripcionesAceptadas().subscribe({
      next: (data) => {
        this.inscripciones = data;
        console.log(this.inscripciones);
      },
      error: (err) =>{ console.log(err); }
    });
  }

  getInscripcionesAlumno(codAlumno: number): void{
    this.inscripcionService.getInscripcionesPorAlumno(codAlumno).subscribe({
      next: (data) => {
        this.inscripciones = data;
        console.log(this.inscripciones);
      },
      error: (err) =>{ console.log(err); }
    });
  }

  getInscripcionesCurso(codCurso: number): void{
    this.inscripcionService.getInscripcionesPorCurso(codCurso).subscribe({
      next: (data) => {
        this.inscripciones = data;
        console.log(this.inscripciones);
      },
      error: (err) =>{ console.log(err); }
    });
  }

  getAlumnoCod(codAlum: number): any{
    this.alumnoService.getAlumnoCod(codAlum).subscribe({
      next: (data) =>{
        return data;
      },
      error: (err) => { return null; }
    })
  }

  comprobarSesion(): void{
    if(this.storageService.sesionIniciada()){
      this.sesionIniciada = true;
      this.rol = this.storageService.obtenerUsuario().rol;
  }
  else{
    this.router.navigate(['/']);
  };
}
}