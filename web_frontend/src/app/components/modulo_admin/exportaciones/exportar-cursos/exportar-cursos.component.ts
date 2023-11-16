import { Component, OnInit } from '@angular/core';
import { StorageService } from 'src/app/services/storage.service';
import Swal from 'sweetalert2';
const API = "http://localhost:8080/api/v1";

@Component({
  selector: 'app-exportar-cursos',
  templateUrl: './exportar-cursos.component.html',
  styleUrls: ['./exportar-cursos.component.css']
})
export class ExportarCursosComponent implements OnInit {
  tipo = 'general';
  formato = 'excel';

  constructor(private storageService: StorageService) {}
  ngOnInit(): void {
    this.storageService.comprobarSesion();
    this.storageService.denegarAcceso("ALUMNOyDOCENTE");
  }

  setTipo(tipo: string):void{
    this.tipo = tipo;
  }
  setFormato(formato: string): void{
    this.formato = formato;
  }
  descargarArchivoC(): void{
    if(this.tipo === 'general' && this.formato === 'excel'){
      window.location.href=API+"/reporte/curso/excel";
    }
    else if(this.tipo === 'general' && this.formato === 'pdf'){
      window.location.href=API+"/reporte/curso/pdf";
    }
    else if(this.tipo === 'general' && this.formato === 'csv'){
      window.location.href=API+"/reporte/curso/csv";
    }
    else{
      this.mensajeError();
    }
  }

  public mensajeWIP(): void{
    Swal.fire({
      title: "WIP",
      text: "Trabajo en progreso",
      icon: "info"
    });
  }

  public mensajeError(): void{
    Swal.fire({
      title: "Ups! Ha sucedido un error.",
      text: "No se encontraron docentes a exportar con los filtros especificados.",
      icon: "error"
    })
  }
}
