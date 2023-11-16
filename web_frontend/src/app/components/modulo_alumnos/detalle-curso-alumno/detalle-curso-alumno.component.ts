import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Curso } from 'src/app/models/curso.model';
import { DetalleInscripcion } from 'src/app/models/detalle.inscripcion.model';
import { Inscripcion } from 'src/app/models/inscripcion.model';
import { CursoService } from 'src/app/services/curso.service';
import { InscripcionService } from 'src/app/services/inscripcion.service';
import { StorageService } from 'src/app/services/storage.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-detalle-curso-alumno',
  templateUrl: './detalle-curso-alumno.component.html',
  styleUrls: ['./detalle-curso-alumno.component.css']
})
export class DetalleCursoAlumnoComponent implements OnInit{
  constructor(private storageService: StorageService, private inscripcionService: InscripcionService, private cursoService: CursoService, private route: ActivatedRoute){ }
  codAlum?: number;
  codCurso?: number;
  codInscripcion?: number;
  inscripcion: Inscripcion = new Inscripcion();
  curso: Curso = new Curso();
  ngOnInit(): void {
    this.storageService.comprobarSesion();
    this.storageService.denegarAcceso("ADMINISTRADORyDOCENTE");
    this.codAlum = this.route.snapshot.params['codalum']
    this.codCurso = this.route.snapshot.params['codcurso']
    this.codInscripcion = this.route.snapshot.params['codins']
    this.getInscripcion();
  }

  getInscripcion():void{
    this.inscripcionService.getInscripcionPorCod(this.codInscripcion).subscribe({
      next: (data) =>{
        this.inscripcion = data;
        this.enriquecerInscripcion(this.inscripcion);
        console.log(this.inscripcion);
      }
    })
  }

  enriquecerInscripcion(inscripcion: Inscripcion): void {
    this.getCursoCod(inscripcion.curso).subscribe((curso: Curso) => {
        this.inscripcion.curso = curso;
    }); 
  }

  getCursoCod(codCurso: number | any): any {
    return this.cursoService.getCurso(codCurso);
  }

  descargarGuia(codGuia: number | any): void{
    Swal.fire({
      title: "Descargando guía...",
      text: "Estas descargando la guía de estudio: " + codGuia,
      icon: "info"
    });
  }

}