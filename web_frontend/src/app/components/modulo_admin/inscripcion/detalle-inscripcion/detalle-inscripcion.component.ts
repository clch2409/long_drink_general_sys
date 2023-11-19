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
  detalle: DetalleInscripcion = new DetalleInscripcion();
  codAlum?: number;
  codCurso?: number;
  codInscripcion?: number;
  estado = ''
  fechaFutura = false;
  constructor(private storageService: StorageService, private router: Router, private inscripcionService: InscripcionService, private route: ActivatedRoute) { }
  ngOnInit(): void {
    this.storageService.comprobarSesion();
    this.storageService.denegarAcceso('ALUMNOyDOCENTE');
    this.codAlum = this.route.snapshot.params['codalum']
    this.codInscripcion = this.route.snapshot.params['codins']
    this.obtenerDetalles();
    
  }

  obtenerDetalles(): void{
    this.inscripcionService.detalleInscripcion(this.codInscripcion).subscribe({
      next: (data) =>{
        this.detalle = data;
        console.log(this.detalle)
        this.definirEstado();
        this.fechaFutura = this.comprobarFecha();
        console.log(this.comprobarFecha())
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
    let fechaInicio = this.detalle.fechaInicio;
    let fechaF = this.detalle.fechaFinal;
    if(fechaT == null){
      this.estado = 'EN PROCESO';
    }
    if(fechaF === fechaT){
      this.estado = 'TERMINADO';
    }
    if(fechaT === fechaInicio){
      this.estado = 'RETIRADO';
    }
  }

  comprobarFecha(): boolean{
    let fechaFinal = new Date(this.detalle.fechaFinal!);
    let retorno = new Date().getTime() - fechaFinal.getTime();
    if(retorno < 0){
      return true; //Fecha en el futuro, retorna true. Ok.
    }
    return false;
  }

  retirarAlumno(): void{
    var textoRetiro = `Usted esta a punto de retirar al siguiente alumno: 
    ALUMNO: ${this.detalle.nombre} ${this.detalle.apellidoPaterno} ${this.detalle.apellidoMaterno}
    CURSO: ${this.detalle.descripcion}
    FECHA INICIO: ${this.detalle.fechaInicio}
    FECHA FIN: ${this.detalle.fechaFinal}
    FECHA INSCRIPCIÓN: ${this.detalle.fechaInscripcion}
    `;

    Swal.fire({
      title:'Confirmación de Solicitud.',
      text:textoRetiro,
      icon:'question',
      showCancelButton: true,
      cancelButtonText: 'CANCELAR',
      confirmButtonColor: '#d33',
      cancelButtonColor: '#000',
      confirmButtonText: 'RETIRAR ALUMNO',
    }).then((result) => {
      if(result.isConfirmed){
        this.enviarRetiro();
      }
    })
  }


  enviarRetiro(): void{
    this.inscripcionService.retirarAlumno(this.codInscripcion).subscribe({
      next: (data) =>{
        Swal.fire('Éxito', 'Alumno retirado del curso exitosamente.', 'success').then(() =>{
          this.router.navigate(['/dashboard/inscripciones']);
        });
      },
      error: (err) =>{
        Swal.fire('Error!', 'Ups! Imposible retirar al alumno. Comuniquese con el área IT.', 'error');
        console.log(err);
      }
    })
  }

}
