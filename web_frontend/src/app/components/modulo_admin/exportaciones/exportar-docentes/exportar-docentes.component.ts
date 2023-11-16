import { Component, OnInit } from '@angular/core';
import { StorageService } from 'src/app/services/storage.service';
import Swal from 'sweetalert2';
const API = "http://localhost:8080/api/v1";
@Component({
  selector: 'app-exportar-docentes',
  templateUrl: './exportar-docentes.component.html',
  styleUrls: ['./exportar-docentes.component.css']
})
export class ExportarDocentesComponent implements OnInit {
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

  descargarArchivoD(): void{
    if(this.tipo === 'general' && this.formato === 'excel'){
      window.location.href=API+"/reporte/profesor/excel";
    }
    else if(this.tipo === 'general' && this.formato === 'pdf'){
      window.location.href=API+"/reporte/profesor/pdf?activo=0";
    }
    else if(this.tipo === 'general' && this.formato === 'csv'){
      window.location.href=API+"/reporte/profesor/csv";
    }
    else if(this.tipo === 'activos' && this.formato === 'excel'){
      window.location.href=API+"/reporte/profesor/excel?tipo=1";
    }
    else if(this.tipo === 'activos' && this.formato === 'pdf'){
      window.location.href=API+"/reporte/profesor/pdf?activo=1";
    }
    else if(this.tipo === 'activos' && this.formato === 'csv'){
      window.location.href=API+"/reporte/profesor/csv?tipo=1";
    }
    else if(this.tipo === 'disponibles' && this.formato === 'excel'){
      window.location.href=API+"/reporte/profesor/excel?tipo=2"
    } 
    else if(this.tipo === 'disponibles' && this.formato === 'pdf'){
      this.mensajeWIP();
    }
    else if(this.tipo === 'disponibles' && this.formato === 'csv'){
      window.location.href=API+"/reporte/profesor/csv?tipo=2";
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
