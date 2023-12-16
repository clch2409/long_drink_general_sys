import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Seccion } from 'src/app/models/seccion.model';
import { SeccionService } from 'src/app/services/seccion.service';
import { StorageService } from 'src/app/services/storage.service';
import Swal from 'sweetalert2';


@Component({
  selector: 'app-listado-secciones',
  templateUrl: './listado-secciones.component.html',
  styleUrls: ['./listado-secciones.component.css']
})


export class ListadoSeccionesComponent {

  secciones: Seccion[] = [];

  constructor( private storageService: StorageService, private router: Router,private seccionService: SeccionService) { }

  
  ngOnInit(): void {
    this.getSecciones();
}
  

  getSecciones(): void{
    this.seccionService.getSecciones().subscribe({
      next: (data) =>{
        this.secciones = data;
        console.log(data);
      },
      error: (err) => console.log(err)
    });
  }


}

 




