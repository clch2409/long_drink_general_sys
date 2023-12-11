import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Curso } from 'src/app/models/curso.model';
import { DetalleInscripcion } from 'src/app/models/detalle.inscripcion.model';
import { Inscripcion } from 'src/app/models/inscripcion.model';
import { CursoService } from 'src/app/services/curso.service';
import { InscripcionService } from 'src/app/services/inscripcion.service';
import { StorageService } from 'src/app/services/storage.service';
import Swal from 'sweetalert2';
const API = "http://localhost:8080/api/v1";

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
      next: (data: Inscripcion) =>{
        this.inscripcion = data;
        console.log(this.inscripcion);
      }
    })
  }


  getCursoCod(codCurso: number | any): any {
    return this.cursoService.getCurso(codCurso);
  }

  descargarGuia(codGuia: number | any): void{
    window.location.href=API+`/tema/descargar_guia?codTema=${codGuia}`;
    Swal.fire({
      title: "Descargando guía...",
      text: "Estas descargando la guía de estudio: " + codGuia,
      icon: "info"
    });
  }

  descrgarCertificado(codAlum: number | any, codCurso: number | any): void {
    window.location.href = API + `/reporte/certificado/pdf?codAlum=${codAlum}&codCurso=${codCurso}`
    Swal.fire({
      title: "Descargando Certificado...",
      text: "Estas descargando su certificado",
      icon: "success"
    });
  }

}
