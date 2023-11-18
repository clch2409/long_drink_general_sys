import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Curso } from 'src/app/models/curso.model';
import { Profesor } from 'src/app/models/profesor.model';
import { CursoService } from 'src/app/services/curso.service';
import { ProfesorService } from 'src/app/services/profesor.service';
import { StorageService } from 'src/app/services/storage.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-editar-curso',
  templateUrl: './editar-curso.component.html',
  styleUrls: ['./editar-curso.component.css']
})
export class EditarCursoComponent implements OnInit {
  codCurso?: number;
  curso?: Curso;
  listaFrecuencias = ["Diario","Sabados - Domingos","Interdiario"];
  listaProfesores: Profesor[] = [];
  codProfesor = 0;
  cambiar = false;

  //Datos a enviar...
  eNombre = '';
  eDescripcion = '';
  eDuracion = 0;
  eFrecuencia = '';
  eMensualidad = 0;
  eVisibilidad = true;
  eProfesor = 0;

  constructor(private storageService: StorageService,private route: ActivatedRoute, private cursoService: CursoService, private profesorService: ProfesorService, private router: Router) { }
  ngOnInit(): void {
    this.storageService.comprobarSesion();
    this.storageService.denegarAcceso('ALUMNOyDOCENTE');
    this.codCurso = this.route.snapshot.params['codcur']
    this.getCurso();
    this.llenarProfesores();
    let profesor = document.getElementById("profesor") as HTMLInputElement;
    profesor.disabled = true; // '-'
    
  }

  setCambiar(): void{
    let chkCP = document.getElementById("cambiarProfesor") as HTMLInputElement;
    if(chkCP.checked){
      this.cambiar = true;
      let profesor = document.getElementById("profesor") as HTMLInputElement;
      profesor.disabled = false;
    }
    else{ 
      this.cambiar = false; 
      let profesor = document.getElementById("profesor") as HTMLInputElement;
      profesor.disabled = true;
    }
    console.log(this.cambiar);
  }

  llenarProfesores(): void{
    this.profesorService.getProfesoresDisponibles().subscribe({
      next: (data) =>{
        this.listaProfesores = data;
      },
      error: (err) =>{
        Swal.fire("Error!",`Ups! No se encontraron profesores disponibles para asignar. Por tanto, no se podrá cambiar el profesor asignado al curso.`,"error")
        let chkCP = document.getElementById("cambiarProfesor") as HTMLInputElement;
        chkCP.disabled = true;
        let profesor = document.getElementById("profesor") as HTMLInputElement;
        profesor.disabled = true;
      }
    })
  }

  getCurso(): void{
    this.cursoService.getCurso(this.codCurso).subscribe({
      next: (data) =>{
        this.curso = data;
        this.codProfesor = data.profesor?.codProfesor!;
        this.setDatos();
        console.log(this.codProfesor);
      },
      error: (err) =>{
        Swal.fire("Error!",`Curso de código: ${this.codCurso} no encontrado.`,"error").then((result) => {
          if(result.isConfirmed || result.isDismissed){
            this.router.navigate(['dashboard/cursos']);
          }
        })
        console.log(err);
      }
    })
  }

  setDatos(): void{
    let txtNombre = document.getElementById("nombre") as HTMLInputElement;
    let txtDescripcion = document.getElementById("descripcionCurso") as HTMLInputElement;
    let txtDuracion = document.getElementById("duracionCurso") as HTMLInputElement;
    let cbProfesor = document.getElementById("profesor") as HTMLInputElement; ///Profesor "nuevo"
    let txtPAntiguo = document.getElementById("profesorActual") as HTMLInputElement;
    let cbFrecuencia = document.getElementById("frecuencia") as HTMLInputElement;
    let txtMensualidad = document.getElementById("mensualidad") as HTMLInputElement;
    let cbVisibilidad = document.getElementById("activo") as HTMLInputElement;
    txtNombre.value = this.curso?.nombre!;
    txtDescripcion.value = this.curso?.descripcion!;
    txtPAntiguo.value = this.curso?.profesor?.nombre! +" "+ this.curso?.profesor?.apellidoPaterno!;
    txtDuracion.value = this.curso?.duracion?.toString()!;
    txtMensualidad.value = this.curso?.mensualidad?.toString()!;
    cbFrecuencia.value = this.curso?.frecuencia!;
    if(this.curso?.visibilidad == true){
      cbVisibilidad.value = "true";
    }
    else{
      cbVisibilidad.value = "false";
    }
  }

  onSubmit(n: string, d: string, duracion: string, pNuevo: number | any,f: string, m: string, activo: string): void{
    this.eNombre = n;
    this.eDescripcion = d;
    this.eDuracion = Number.parseInt(duracion);
    this.eFrecuencia = f;
    this.eMensualidad = Number.parseInt(m);
    this.eVisibilidad = activo === 'true' ? true : false;
    if(this.cambiar){
      this.eProfesor = pNuevo //Envia el cod del NUEVO profesor seleccionado para el curso.
    }
    else{
      this.eProfesor = this.codProfesor; //Default: Envia el cod del profesor "actual" del curso.
    }

    if(!this.validarNombre()){
      Swal.fire("Ups! Datos incorrectos","Debe llenar el campo nombre.","error");
    }
    else if(!this.validarDescripcion()){
      Swal.fire("Ups! Datos incorrectos","Debe llenar el descripción. Soporta un máximo de 150 caracteres.","error");
    }
    else if(!this.validarDuracion()){
      Swal.fire("Ups! Datos incorrectos","Debe llenar el campo duración. Debe tener entre 1 y 50 semanas.","error");
    }
    else if(!this.validarFrecuencia()){
      Swal.fire("Ups! Datos incorrectos","Debe seleccionar una frecuencia.","error");
    }
    else if(!this.validarMensualidad()){
      Swal.fire("Ups! Datos incorrectos","Debe llenar el campo mensualidad. Su valor debe ser entre S./ 1 y S./ 1000","error");
    }
    else if(!this.validarProfesor()){
      Swal.fire("Ups! Datos incorrectos","Debe seleccionar un profesor valido. Comuniquese con administración.","error");
    }
    else{
      const carga = {
        "codCurso": this.curso?.codCurso,
        "codProfesor": this.eProfesor,
        "nombre": this.eNombre,
        "descripcion": this.eDescripcion,
        "duracion": this.eDuracion,
        "frecuencia": this.eFrecuencia,
        "mensualidad": this.eMensualidad,
        "visibilidad": this.eVisibilidad
      }
      this.cursoService.editarCurso(carga).subscribe({
        next: (data) =>{
          Swal.fire("Exito!","Curso actualizado correctamente.","info").then((result) => {
            if(result.isConfirmed || result.isDismissed){
              this.router.navigate(['dashboard/cursos']);
            }
          })
        },
        error: (err) =>{
          Swal.fire("Error!","Error en la actualización de curso. Vuelva a intentarlo.","error").then((result) =>{
            if(result.isConfirmed || result.isDismissed){
              window.location.reload();
            }
          })
          console.log(err);
        }
      })
    }
  }

  validarNombre(): boolean{
    return this.eNombre.length >= 1 && this.eNombre.length <=100;
  }

  validarDescripcion(): boolean{
    return this.eDescripcion.length >= 1 && this.eDescripcion.length <= 150;
  }

  validarDuracion(): boolean{
    return this.eDuracion >= 1 && this.eDuracion <= 50;
  }

  validarFrecuencia(): boolean{
    return this.eFrecuencia.length > 1;
  }

  validarMensualidad(): boolean{
    return this.eMensualidad >= 1 && this.eMensualidad <= 1000;
  }

  validarProfesor(): boolean{
    return this.eProfesor != 0;
  }

}
