import { Component, OnInit } from '@angular/core';
import { StorageService } from 'src/app/services/storage.service';
import Swal from 'sweetalert2';
const API = "http://localhost:8080/api/v1";

@Component({
  selector: 'app-exportar-alumnos',
  templateUrl: './exportar-alumnos.component.html',
  styleUrls: ['./exportar-alumnos.component.css']
})
  
export class ExportarAlumnosComponent implements OnInit {

  tipo = 'activos';
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
  descargarArchivo(): void{
    if(this.tipo === 'activos' && this.formato === 'excel'){
      window.location.href=API+"/reporte/alumno/excel?tipo=1";
    }
    else if(this.tipo === 'general' && this.formato === 'excel'){
      window.location.href=API+"/reporte/alumno/excel?tipo=0";
    }
    else if(this.tipo === 'activos' && this.formato === 'pdf'){
      window.location.href=API+"/reporte/alumno/pdf?activo=1"
    }
    else if(this.tipo === 'general' && this.formato === 'pdf'){
      window.location.href=API+"/reporte/alumno/pdf?activo=0"
    }
    else if(this.tipo === 'activos' && this.formato === 'csv'){
      window.location.href=API+"/reporte/alumno/csv?tipo=1"
    }
    else if(this.tipo === 'general' && this.formato === 'csv'){
      window.location.href=API+"/reporte/alumno/csv"
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
      text: "No se encontraron alumnos a exportar con los filtros especificados.",
      icon: "error"
    })
  }

}
