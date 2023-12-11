import { Component, OnInit } from '@angular/core';
import { Curso } from 'src/app/models/curso.model';
import { Inscripcion } from 'src/app/models/inscripcion.model';
import { CursoService } from 'src/app/services/curso.service';
import { InscripcionService } from 'src/app/services/inscripcion.service';
import { StorageService } from 'src/app/services/storage.service';

@Component({
  selector: 'app-mis-cursos',
  templateUrl: './mis-cursos.component.html',
  styleUrls: ['./mis-cursos.component.css']
})
export class MisCursosComponent implements OnInit {
  constructor(private storageService: StorageService, private inscripcionService: InscripcionService, private cursoService: CursoService){ }
  ngOnInit(): void {
      this.storageService.comprobarSesion();
      this.storageService.denegarAcceso("ADMINISTRADORyDOCENTE");
      this.codAlumno = this.storageService.obtenerUsuario().codAlumno;
      this.getInscripciones();
      this.getCursos();
  }
  inscripciones: Inscripcion[] = [];
  cursosActivos: Inscripcion[] = [];
  cursosTerminados: Inscripcion[] = [];
  cursos: Curso[] = [];
  codAlumno: number = 0;
  mensajeTerminados = '';
  mensajeActivos = '';

  getInscripciones(): void{
    this.inscripcionService.getInscripcionesPorAlumno(this.codAlumno).subscribe({
      next: (data) =>{
        this.inscripciones = data;
        this.llenarCursos(this.inscripciones);
        //this.enriquecerInscripciones(this.inscripciones);
        console.log(this.storageService.obtenerUsuario())
      },
      error: (err) =>{
        console.log(err)
      }
    });
  }

  getCursos(): void{
    this.cursoService.getCursos().subscribe({
      next: (data)=>{
        this.cursos = data;
        console.log(this.cursos);
      },
      error: (err) =>{
        console.log(err);
      }
    })
  }

  llenarCursos(listado: Inscripcion[]): void{
    listado.forEach((e) =>{
      if(e.fechaTerminado == null && e.estado == true){
        this.cursosActivos.push(e);
        console.log(e.curso);
      }
      else if(e.estado === false || e.fechaTerminado != null){
        this.cursosTerminados.push(e);
      }
    })
  }

  getCursoCod(codCurso: number | any): any {
    return this.cursoService.getCurso(codCurso);
  }

  enriquecerInscripciones(inscripciones: Inscripcion[]): void {
    for (const inscripcion of inscripciones) {
        var codCurso = inscripcion.curso;
        this.getCursoCod(codCurso).subscribe((curso: Curso) => {
          inscripcion.curso = curso;
        });

    }
  }

}
