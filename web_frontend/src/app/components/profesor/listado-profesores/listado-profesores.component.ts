import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Profesor } from 'src/app/models/profesor.model';
import { ProfesorService } from 'src/app/services/profesor.service';
import { StorageService } from 'src/app/services/storage.service';

@Component({
  selector: 'app-listado-profesores',
  templateUrl: './listado-profesores.component.html',
  styleUrls: ['./listado-profesores.component.css']
})
export class ListadoProfesoresComponent implements OnInit {
  sesionIniciada = false;
  rol = '';
  profesores: Profesor[] = [];
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
}
