import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { Profesor } from 'src/app/models/profesor.model';
import { Turno } from 'src/app/models/turno.model';
import { CursoService } from 'src/app/services/curso.service';
import { ProfesorService } from 'src/app/services/profesor.service';
import { StorageService } from 'src/app/services/storage.service';
import { TurnoService } from 'src/app/services/turno.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-nuevo-curso',
  templateUrl: './nuevo-curso.component.html',
  styleUrls: ['./nuevo-curso.component.css']
})
export class NuevoCursoComponent implements OnInit {

  listaFrecuencias = ["Diario","Sabados - Domingos","Interdiario"];
  listaProfesores: Profesor[] = [];
  listaTurnos: Turno[] = [];

  eNombre = '';
  eDuracion = 0;
  eMensualidad = 0;
  eDescripcion = '';
  eCantidad = 0;
  eDocente = 0;
  eTurno = 0;
  eFrecuencia = '';
  eVisibilidad = true;
  eImagen = 'https://i.imgur.com/APbzr19.jpg';
  datosValidos = false;

  constructor(private storageService: StorageService, 
    private profesorService: ProfesorService, 
    private turnoService: TurnoService, 
    private cursoService: CursoService){}

  
  ngOnInit(): void {
    this.storageService.comprobarSesion();
    this.storageService.denegarAcceso('ALUMNOyDOCENTE');
    this.llenarProfesores();
    this.llenarTurnos();
  }
  onSubmit(n: string, d: number | any, m: number | any, des: string, c: number | any, doc: number | any, t: number | any, f: string, v: boolean): void{
    this.eNombre = n;
    this.eDuracion = parseInt(d);
    this.eMensualidad = parseFloat(m);
    this.eDescripcion = des;
    this.eCantidad = parseInt(c);
    this.eDocente = parseInt(doc);
    this.eTurno = parseInt(t);
    this.eFrecuencia = f;
    this.eVisibilidad = true;
    this.validarDatos();
  }

  validarDatos(): void{
    if(!this.validarNombre()){
      Swal.fire("Ups! Datos incorrectos.","Debe llenar el campo nombre con un valor entre 5 y 100 caracteres.","error")
    }
    else if(!this.validarDuracion()){
      Swal.fire("Ups! Datos incorrectos","Debe llenar la duración del curso. Recuerde que el curso puede durar entre 1 y 50 semanas.","error")
    }
    else if(!this.validarMensualidad()){
      Swal.fire("Ups! Datos incorrectos","Debe llenar la mensualidad del curso. Recuerde que tiene un tope de S./ 1000 y un minimo de S./ 1","error")
    }
    else if(!this.validarDescripcion()){
      Swal.fire("Ups! Datos incorrectos","Debe llenar la descripción del curso. Recuerde que debe tener entre 1 y 100 caracteres.","error")
    }
    else if(!this.validarCantidad()){
      Swal.fire("Ups! Datos incorrectos","Debe llenar la cantidad máxima de alumnos del curso. Recuerde que el minimo es 8 y el máximo 20.","error")
    }
    else if(!this.validarDocente()){
      Swal.fire("Ups! Datos incorrectos","Debe seleccionar un docente responsable del curso.","error")
    }
    else if(!this.validarTurno()){
      Swal.fire("Ups! Datos incorrectos","Debe seleccionar un turno para el curso.","error")
    }
    else if(!this.validarFrecuencia()){
      Swal.fire("Ups! Datos incorrectos","Debe seleccionar una frecuencia para el curso.","error")
    }
    else{
      const carga = {
        "nombre": this.eNombre,
        "descripcion": this.eDescripcion,
        "frecuencia": this.eFrecuencia,
        "imagen": this.eImagen,
        "duracion": this.eDuracion,
        "cantidadAlumnos": this.eCantidad,
        "mensualidad": this.eMensualidad,
        "visibilidad": true,
        "codProfesor": this.eDocente,
        "codTurno": this.eTurno
      }
      this.cursoService.nuevoCurso(carga).subscribe({
        next: (data) =>{
          Swal.fire("Exito!","Curso creado correctamente.","info").then((result) => {
            if(result.isConfirmed || result.isDismissed){
              window.location.reload();
            }
          })
        },
        error: (err) =>{
          Swal.fire("Error!","Error en creación de curso. Vuelva a intentarlo.","error");
        }
      })
    }
  }

  validarNombre(): boolean{
    return this.eNombre.length >= 5 && this.eNombre.length <= 100
  }
  validarDuracion(): boolean{
    return this.eDuracion >= 1 && this.eDuracion <= 50
  }
  validarMensualidad(): boolean{
    return this.eMensualidad >= 1 && this.eMensualidad <= 1000;
  }
  validarDescripcion(): boolean{
    return this.eDescripcion.length >= 5 && this.eDescripcion.length <= 100;
  }
  validarCantidad(): boolean{
    return this.eCantidad >= 8 && this.eCantidad <= 20;
  }
  validarDocente(): boolean{
    return this.eDocente != 0;
  }
  validarTurno(): boolean{
    return this.eTurno != 0;
  }
  validarFrecuencia(): boolean{
    return this.eFrecuencia.length > 1;
  }

  limpiarCampos(): void{
    let txtNombre = document.getElementById("nombreCurso") as HTMLInputElement;
    let txtDescripcion = document.getElementById("descripcionCurso") as HTMLInputElement;
    let txtDuracion = document.getElementById("duracionCurso") as HTMLInputElement;
    let txtCantidad = document.getElementById("cantidadAlumnos") as HTMLInputElement;
    let txtMensualidad = document.getElementById("mensualidad") as HTMLInputElement;
    txtNombre.value = "";
    txtDescripcion.value = "";
    txtDuracion.value = "";
    txtCantidad.value = "";
    txtMensualidad.value = "";
  }

  llenarProfesores(): void{
    this.profesorService.getProfesoresDisponibles().subscribe({
      next: (data) =>{
        this.listaProfesores = data;
      },
      error: (err) =>{
        console.log(err.mensaje);
      }
    });
  }

  llenarTurnos(): void{
    this.turnoService.getTurnos().subscribe({
      next: (data) =>{
        this.listaTurnos = data;
      },
      error: (err) =>{
        console.log(err.mensaje);
      }
    })
  }

}
