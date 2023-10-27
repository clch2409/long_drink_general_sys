import { Component } from '@angular/core';
import { StorageService } from 'src/app/services/storage.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent {

  constructor(private storageService: StorageService) { }

  miCuenta(): void{
    Swal.fire('Trabajo en Progreso...')
  }

  cerrarSesion(): void {
    this.storageService.cerrarSesion();
  }
}