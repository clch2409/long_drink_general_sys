import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Profesor } from 'src/app/models/profesor.model';
import { ProfesorService } from 'src/app/services/profesor.service';
import { StorageService } from 'src/app/services/storage.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-listado-profesores',
  templateUrl: './listado-profesores.component.html',
  styleUrls: ['./listado-profesores.component.css']
})
export class ListadoProfesoresComponent implements OnInit {
  sesionIniciada = false;
  rol = '';
  profesores: Profesor[] = [];
  filtroDni: string = '';
  filtroEstado = 'activos';
  constructor(private storageService: StorageService, private router: Router, private profesorService: ProfesorService) { }
  ngOnInit(): void {
      this.comprobarSesion();
      this.getProfesoresActivos();
  }

  comprobarSesion(): void {
    if (this.storageService.sesionIniciada()) {
      this.sesionIniciada = true;
      this.rol = this.storageService.obtenerUsuario().rol;
    } else {
      this.router.navigate(['/']);
    }
  }

  getProfesoresActivos(): void{
    this.profesorService.getProfesoresActivos().subscribe({
      next: (data) =>{
        this.profesores = data;
        console.log(data);
      },
      error: (err) => console.log(err)
    });
  }

  buscarPorDNI(): void {
    if (this.filtroDni.length === 8) {
      this.profesorService.getProfesorDNI(this.filtroDni).subscribe({
        next: (data) => {
          if (data) {
            this.profesores = [data];
            this.limpiarCampo();
          } else {
            Swal.fire({
              icon: 'info',
              title: 'Información',
              text: 'No se encontraron resultados para el DNI especificado.',
            });
            this.limpiarCampo();
          }
        },
        error: (err) => {
          if (err.status === 404) {
            Swal.fire({
              icon: 'error',
              title: 'Error',
              text: 'No se encontró resultados del DNI ingresado.',
            });
            this.limpiarCampo();
          } else {
            console.log(err);
          }
        }
      });
    } else {
      Swal.fire({
        icon: 'info',
        title: 'Información',
        text: 'El DNI debe contener 8 dígitos.',
      });
      this.limpiarCampo();
    }
  }
  
  filtrarProfesores(): void {
    if (this.filtroEstado === 'activos') {
      this.getProfesoresActivos();
    } else if (this.filtroEstado === 'general') {
      this.profesorService.getProfesores().subscribe({
        next: (data) => {
          this.profesores = data;
        },
        error: (err) => console.log(err)
      });
    }
  }

  limpiarCampo(){
    this.filtroDni = "";
  }
}