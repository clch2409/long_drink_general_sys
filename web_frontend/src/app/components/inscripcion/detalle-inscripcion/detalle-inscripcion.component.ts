import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { DetalleInscripcion } from 'src/app/models/detalle.inscripcion.model';
import { InscripcionService } from 'src/app/services/inscripcion.service';
import { StorageService } from 'src/app/services/storage.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-detalle-inscripcion',
  templateUrl: './detalle-inscripcion.component.html',
  styleUrls: ['./detalle-inscripcion.component.css']
})
export class DetalleInscripcionComponent implements OnInit {
  sesionIniciada = false;
  rol = '';
  detalle: DetalleInscripcion = new DetalleInscripcion();
  codAlum?: number;
  codCurso?: number;
  estado = ''
  constructor(private storageService: StorageService, private router: Router, private inscripcionService: InscripcionService, private route: ActivatedRoute) { }
  ngOnInit(): void {
    this.comprobarSesion();
    this.codAlum = this.route.snapshot.params['codalum']
    this.codCurso = this.route.snapshot.params['codcurso']
    this.obtenerDetalles();
    this.definirEstado();
  }

  comprobarSesion(): void {
    if (this.storageService.sesionIniciada()) {
      this.sesionIniciada = true;
      this.rol = this.storageService.obtenerUsuario().rol;
    } else {
      this.router.navigate(['/']);
    }
  }

  obtenerDetalles(): void{
    this.inscripcionService.detalleInscripcion(this.codCurso,this.codAlum).subscribe({
      next: (data) =>{
        this.detalle = data;
        console.log(this.detalle)
      },
      error: (err) => {
        console.log(err);
        this.detalle = new DetalleInscripcion();
        Swal.fire({
          title:'INFORMACIÓN',
          text:'Ups! Inscripción no encontrada.',
          icon:'error',
        }).then((result) => {
          if(result.isConfirmed || result.isDismissed){
            this.router.navigate(['/dashboard/inscripciones']);
          }
        })
      }
    });
  }

  definirEstado(): void{
    let algo = this.detalle.estado;
    if (algo == true){
      console.log(true)
    }
  }

}
