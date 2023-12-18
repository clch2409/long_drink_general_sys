import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Seccion } from 'src/app/models/seccion.model';
import { SeccionService } from 'src/app/services/seccion.service';
import { StorageService } from 'src/app/services/storage.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-listado-secciones',
  templateUrl: './listado-secciones.component.html',
  styleUrls: ['./listado-secciones.component.css'],
})
export class ListadoSeccionesComponent implements OnInit {
  secciones: Seccion[] = [];

  constructor(
    private storageService: StorageService,
    private router: Router,
    private seccionService: SeccionService
  ) {}

  ngOnInit(): void {
    this.storageService.comprobarSesion();
    this.storageService.denegarAcceso('ALUMNOyDOCENTE');
    this.getSecciones();
  }

  getSecciones(): void {
    this.seccionService.getSecciones().subscribe({
      next: (data) => {
        this.secciones = data;
      },
      error: (err) =>
        Swal.fire(
          'Error!',
          'Ups! No se pudieron cargar las secciones. Intente nuevamente.',
          'error'
        ),
    });
  }

  setEstadoSeccion(seccion: Seccion): string {
    let fechaActual = new Date();
    let fechaInicio = new Date(seccion.fechaInicio!!);
    let fechaFinal = new Date(seccion.fechaFinal!!);
    console.log(fechaFinal > fechaActual);
    if (fechaActual < fechaInicio && fechaActual < fechaFinal) {
      return 'PROGRAMADA';
    }
    if (fechaActual > fechaFinal && fechaActual > fechaInicio) {
      return 'TERMINADA';
    }
    if (fechaActual < fechaFinal && fechaActual > fechaInicio) {
      return 'EN CURSO';
    }
    return '';
  }
}
