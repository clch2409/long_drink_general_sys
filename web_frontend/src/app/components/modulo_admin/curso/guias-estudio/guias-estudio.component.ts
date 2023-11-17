import { HttpEventType, HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Curso } from 'src/app/models/curso.model';
import { TemaCurso } from 'src/app/models/tema.curso.model';
import { CursoService } from 'src/app/services/curso.service';
import { StorageService } from 'src/app/services/storage.service';
import { TemaService } from 'src/app/services/tema.service';
import Swal from 'sweetalert2';
const API = "http://localhost:8080/api/v1";

@Component({
  selector: 'app-guias-estudio',
  templateUrl: './guias-estudio.component.html',
  styleUrls: ['./guias-estudio.component.css']
})
export class GuiasEstudioComponent implements OnInit {
  listaCursos: Curso[] = [];
  listaTemas: TemaCurso[] = [];

  archivo?: File;
  archivos?: FileList;
  progreso = 0;
  mensaje = '';

  constructor(private storageService: StorageService, private cursoService: CursoService, private temaService: TemaService) {}
  ngOnInit(): void {
    this.storageService.comprobarSesion();
    this.storageService.denegarAcceso("ALUMNOyDOCENTE");
    this.llenarTemas();
  }

  llenarCursos(): void{
    this.cursoService.getCursos().subscribe({
      next: (data) =>{
        this.listaCursos = data;
      },
      error: (err) =>{
        this.mensajeError();
      }
    })
  }

  llenarTemas(): void{
    this.temaService.obtenerTemas().subscribe({
      next: (data) => {
        this.listaTemas = data;
      },
      error: (err) =>{
        this.mensajeError();
      }
    })
  }

  descargarGuia(codTema?: number): void{
    window.location.href=API+`/tema/descargar_guia?codTema=${codTema}`;
  }

  seleccionarArchivo(event: any){
    this.archivos = event.target.files;
  }

  subirGuia(nombreDoc: string): void{
    if(nombreDoc.length >= 1 && nombreDoc.length <=30){
    if(this.archivos){
      const file: File | null = this.archivos.item(0);
      if(file){
        this.archivo = file;
        this.temaService.subirGuia(this.archivo,nombreDoc).subscribe({
          next: (event: any) =>{
            if (event.type === HttpEventType.UploadProgress) {
              this.progreso = Math.round(100 * event.loaded / event.total);
              Swal.fire("Exito!","Archivo subido con exito.","info")
              
            } else if (event instanceof HttpResponse) {
              console.log("Supuesto error!!");
            }
          },
          error: (err: any) =>{
            this.archivos = undefined;
          }
        })
      }
    }
    else{
      Swal.fire("Advertencia","Debe seleccionar un archivo a subir","info");
    }
  }
  else{
    Swal.fire("Advertencia","Debe llenar el campo nombre de guía","info")
  }
}
  
  public mensajeError(): void{
    Swal.fire({
      title: "Ups! Ha sucedido un error.",
      text: "No se encontraron guías de estudio asociadas a ningún curso.",
      icon: "error"
    })
  }
}
