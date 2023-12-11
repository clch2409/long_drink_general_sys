import { Component, OnInit } from '@angular/core';
import { Curso } from 'src/app/models/curso.model';
import { Inscripcion } from 'src/app/models/inscripcion.model';
import { Seccion } from 'src/app/models/seccion.model';
import { CursoService } from 'src/app/services/curso.service';
import { ProfesorService } from 'src/app/services/profesor.service';
import { SeccionService } from 'src/app/services/seccion.service';
import { StorageService } from 'src/app/services/storage.service';

@Component({
  selector: 'app-mis-cursos-profesor',
  templateUrl: './mis-cursos-profesor.component.html',
  styleUrls: ['./mis-cursos-profesor.component.css']
})
export class MisCursosProfesorComponent implements OnInit{
  constructor(private storageService: StorageService, private profesorService : ProfesorService, private cursoService: CursoService, private seccionService: SeccionService){ }
  ngOnInit(): void {
    this.storageService.comprobarSesion()
    this.storageService.denegarAcceso("ADMINISTRADORyALUMNO")
    this.codProfesor = this.storageService.obtenerUsuario().codProfesor;
    this.getSecciones()
  }

  inscripciones : Inscripcion[] = [];
  seccionesDocente: Seccion[] = [];
  codProfesor : number = 0;

  getSecciones(): void{
    this.seccionService.getSecciones().subscribe({
      next: (data) =>{
        data.forEach(e =>{
          if(e.profesor?.codProfesor == this.codProfesor && e.estado == true){
            this.seccionesDocente.push(e);
          }
        })
      }
    });
  }
}
