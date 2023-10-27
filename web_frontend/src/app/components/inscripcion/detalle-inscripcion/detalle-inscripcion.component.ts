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
        this.definirEstado();
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
    let estado = this.detalle.estado as boolean;
    let fechaT = this.detalle.fechaTerminado;
    let fechaI = this.detalle.fechaInscripcion;
    if(estado as boolean == false && fechaT == null){
      this.estado = 'PENDIENTE';
    }
    if(estado as boolean == true && fechaT == null || fechaT != null){
      this.estado = 'ACEPTADA';
    }
    if(estado as boolean == false && fechaT == fechaI){
      this.estado = 'RECHAZADA';
    }
    console.log(this.estado);
  }

  aprobarSolicitud(codAlum?: number, codCurso?: number, curso?: string, nombreAlum?: string, apePa?: string, apeMa?: string, fecha?: string | Date): void{
    var textoSolicitud = `Usted esta a punto de aceptar la siguiente solicitud:
    ALUMNO: ${apePa}  ${apeMa} ${nombreAlum}
    CURSO: ${curso}
    FECHA SOLICITUD: ${fecha}
    `
    Swal.fire({
      title:'Confirmación de Solicitud.',
      text:textoSolicitud,
      icon:'question',
      showCancelButton: true,
      cancelButtonText: 'CANCELAR',
      confirmButtonColor: '#129C1A',
      cancelButtonColor: '#d33',
      confirmButtonText: 'APROBAR',
    }).then((result) => {
      if(result.isConfirmed){
        this.confirmarSolicitud(codAlum,codCurso);
        console.log('Solicitud confirmada....');
      }
    })
  }

  confirmarSolicitud(codAlum?: number, codCurso?: number): void{
    this.inscripcionService.confirmarInscripcion(codCurso,codAlum).subscribe({
      next: (data) =>{
        Swal.fire('Éxito', 'Solicitud de inscripción aceptada correctamente.', 'success').then(() =>{
          this.router.navigate(['/dashboard/inscripciones']);
        });
        
        //this.recargarPagina();
      },
      error: (err) => {
        console.log(err);
        Swal.fire('Error!', 'Ups! Imposible aceptar solicitud de inscripción. Comuniquese con el área IT.', 'error');
      }
    });
  }

  rechazarSolicitud(codAlum?: number, codCurso?: number, curso?: string, nombreAlum?: string, apePa?: string, apeMa?: string, fecha?: string | Date): void{
    var textoSolicitud = `Usted esta a punto de rechazar la siguiente solicitud:
    ALUMNO: ${apePa}  ${apeMa} ${nombreAlum}
    CURSO: ${curso}
    FECHA SOLICITUD: ${fecha}
    `
    Swal.fire({
      title:'Confirmación de Solicitud.',
      text:textoSolicitud,
      icon:'question',
      showCancelButton: true,
      cancelButtonText: 'CANCELAR',
      confirmButtonColor: '#d33',
      cancelButtonColor: '#000',
      confirmButtonText: 'RECHAZAR',
    }).then((result) => {
      if(result.isConfirmed){
        this.denegarSolicitud(codAlum,codCurso);
      }
    })
  }

  denegarSolicitud(codAlum?: number, codCurso?: number): void{
    this.inscripcionService.rechazarInscripcion(codCurso,codAlum).subscribe({
      next: (data) =>{
        Swal.fire('Éxito', 'Solicitud de inscripción rechazada correctamente.', 'success').then(() =>{
          this.router.navigate(['/dashboard/inscripciones']);
        });
      },
      error: (err) => {
        console.log(err);
        Swal.fire('Error!', 'Ups! Imposible rechazar solicitud de inscripción. Comuniquese con el área IT.', 'error');
      }
    });
  }

}
