import { Component, OnInit } from '@angular/core';
import { Curso } from 'src/app/models/curso.model';
import { Inscripcion } from 'src/app/models/inscripcion.model';
import { CursoService } from 'src/app/services/curso.service';
import { ProfesorService } from 'src/app/services/profesor.service';
import { StorageService } from 'src/app/services/storage.service';

@Component({
  selector: 'app-mis-cursos-profesor',
  templateUrl: './mis-cursos-profesor.component.html',
  styleUrls: ['./mis-cursos-profesor.component.css']
})
export class MisCursosProfesorComponent implements OnInit{
  constructor(private storageService: StorageService, private profesorService : ProfesorService, private cursoService: CursoService){ }
  ngOnInit(): void {
    this.storageService.comprobarSesion()
    this.storageService.denegarAcceso("ADMINISTRADORyALUMNO")
    this.codProfesor = this.storageService.obtenerUsuario().codProfesor;
    this.getCursos()
  }

  inscripciones : Inscripcion[] = [];
  cursosDocente: Curso[] = [];
  codProfesor : number = 0;
  

  getCursos(): void{
    this.cursoService.getCursos().subscribe({
      next: (data)=>{
        data.forEach(curso => {
          if (curso.profesor?.codProfesor == this.codProfesor){
            this.cursosDocente.push(curso)
          }
        })
      },
      error: (err) =>{
        console.log(err);
      }
    })
  }
}
