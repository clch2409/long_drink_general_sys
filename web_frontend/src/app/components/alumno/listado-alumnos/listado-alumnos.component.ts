import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Alumno } from 'src/app/models/alumno.model';
import { AlumnoService } from 'src/app/services/alumno.service';
import { StorageService } from 'src/app/services/storage.service';

@Component({
  selector: 'app-listado-alumnos',
  templateUrl: './listado-alumnos.component.html',
  styleUrls: ['./listado-alumnos.component.css']
})
export class ListadoAlumnosComponent implements OnInit {
  sesionIniciada = false;
  rol = '';
  alumnos: Alumno[] = [];
  criterioSeleccionado = '';

  constructor(private storageService: StorageService, private router: Router, private alumnoService: AlumnoService) { }
  ngOnInit(): void {
    this.comprobarSesion();
    this.getAlumnosActivos();

  }

  onSelected(indice: string): void{
    console.log(indice);
    this.criterioSeleccionado = indice;
    if(this.criterioSeleccionado === 'listadoGeneral'){
      this.getAlumnosGeneral();
    }
    else{
      this.getAlumnosActivos();
    }
  }

  getAlumnosActivos(): void{
    this.alumnos.length = 0;
    this.alumnoService.getAlumnosActivos().subscribe({
      next: (data) => {
        this.alumnos = data;
        console.log(this.alumnos);
      },
      error: (err) =>{
        console.log(err);
      }
    });
  }

  getAlumnosGeneral(): void{
    this.alumnos.length = 0;
    this.alumnoService.getAlumnos().subscribe({
      next: (data) => {
        this.alumnos = data;
        console.log(this.alumnos);
      },
      error: (err) =>{
        console.log(err);
      }
    });
  }

  comprobarSesion(): void {
    if (this.storageService.sesionIniciada()) {
      this.sesionIniciada = true;
      this.rol = this.storageService.obtenerUsuario().rol;
    } else {
      this.router.navigate(['/']);
    }
  }
}
