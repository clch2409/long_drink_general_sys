import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { StorageService } from 'src/app/services/storage.service';

@Component({
  selector: 'app-listado-profesores',
  templateUrl: './listado-profesores.component.html',
  styleUrls: ['./listado-profesores.component.css']
})
export class ListadoProfesoresComponent implements OnInit {
  sesionIniciada = false;
  rol = '';
  constructor(private storageService: StorageService, private router: Router) { }
  ngOnInit(): void {
      this.comprobarSesion();
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
