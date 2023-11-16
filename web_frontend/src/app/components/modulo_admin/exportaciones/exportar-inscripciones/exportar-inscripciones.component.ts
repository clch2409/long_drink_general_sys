import { Component } from '@angular/core';
import { Curso } from 'src/app/models/curso.model';
import { AlumnoService } from 'src/app/services/alumno.service';
import { CursoService } from 'src/app/services/curso.service';
import { StorageService } from 'src/app/services/storage.service';
import Swal from 'sweetalert2';
const API = "http://localhost:8080/api/v1";
@Component({
  selector: 'app-exportar-inscripciones',
  templateUrl: './exportar-inscripciones.component.html',
  styleUrls: ['./exportar-inscripciones.component.css']
})
export class ExportarInscripcionesComponent {
  tipo = 'general';
  formato = 'excel';
  estadoDni = true;
  estadoCurso = true;
  listaCursos: Curso[] = [];
  cursoSeleccionado = 1;
  dniIngresado: any = '';
  constructor(private storageService: StorageService, private cursoService: CursoService, private alumnoService: AlumnoService) {}
  ngOnInit(): void {
    this.storageService.comprobarSesion();
    this.storageService.denegarAcceso("ALUMNOyDOCENTE");
    this.llenarCursos();
  }

  setTipo(tipo: string):void{
    this.tipo = tipo;
    if(tipo == 'general'){
      this.estadoDni = true;
      this.estadoCurso = true;
    }
    else if(tipo == 'curso'){
      this.estadoDni = true;
      this.estadoCurso = false;
    }
    else if(tipo == 'alumno'){
      this.estadoDni = false;
      this.estadoCurso = true;
    }
    else{
      this.estadoDni = true;
      this.estadoCurso = true;
    }
  }
  setFormato(formato: string): void{
    this.formato = formato;
  }

  setCurso(curso: number | any): void{
    this.cursoSeleccionado = curso;
    console.log(this.cursoSeleccionado);
  }

  descargarArchivoI(): void{
    if(this.tipo === 'general' && this.formato === 'excel'){
      window.location.href=API+"/reporte/inscripcion/excel";
    }
    else if(this.tipo === 'general' && this.formato === 'pdf'){
      window.location.href=API+`/reporte/inscripcion/pdf?tipo=0`;
    }
    else if(this.tipo === 'general' && this.formato === 'csv'){
      window.location.href=API+"/reporte/inscripcion/csv";
    }
    else if(this.tipo === 'curso' && this.formato === 'excel'){
      this.checkCurso(this.cursoSeleccionado,"excel");
    }
    else if(this.tipo === 'curso' && this.formato === 'pdf'){
      this.checkCurso(this.cursoSeleccionado,"pdf");
    }
    else if(this.tipo === 'curso' && this.formato === 'csv'){
      this.checkCurso(this.cursoSeleccionado,"csv");
    }
    else if(this.tipo === 'alumno' && this.formato === 'excel'){
      let dni = document.getElementById("dniEstudiante") as HTMLInputElement;
      this.dniIngresado = dni.value;
      if(!this.checkDNI()){
        Swal.fire('Error!','El DNI debe contener 8 dígitos.','info');
      }
      else{
        this.checkAlumno(this.dniIngresado,"excel");
      }
    }
    else if(this.tipo === 'alumno' && this.formato === 'pdf'){
      let dni = document.getElementById("dniEstudiante") as HTMLInputElement;
      this.dniIngresado = dni.value;
      if(!this.checkDNI()){
        Swal.fire('Error!','El DNI debe contener 8 dígitos.','info');
      }
      else{
        this.checkAlumno(this.dniIngresado,"pdf");
      }
    }
    else if(this.tipo === 'alumno' && this.formato === 'csv'){
      let dni = document.getElementById("dniEstudiante") as HTMLInputElement;
      this.dniIngresado = dni.value;
      if(!this.checkDNI()){
        Swal.fire('Error!','El DNI debe contener 8 dígitos.','info');
      }
      else{
        this.checkAlumno(this.dniIngresado,"csv");
      }
    }
    else{
      this.mensajeError();
    }
  }

  public mensajeError(): void{
    Swal.fire({
      title: "Ups! Ha sucedido un error.",
      text: "No se encontraron inscripciones a exportar con los filtros especificados.",
      icon: "error"
    })
  }

  public checkDNI(): boolean{
    const numerico = /^[0-9]+$/.test(this.dniIngresado);
    if(this.dniIngresado.length != 8 || !numerico){
      return false;
    }
    return true;
  }

  public checkAlumno(criterio: string,formato: string): void{
    this.alumnoService.getAlumnoDni(this.dniIngresado).subscribe({
      next: (data) =>{
        if(formato === "pdf"){
          window.location.href=API+`/reporte/inscripcion/pdf?tipo=1&cod=${criterio}`;
        }
        else{
          window.location.href=API+`/reporte/inscripcion/${formato}?tipo=1&dni=${criterio}`;
        } 
      },
      error: (err) =>{
        Swal.fire('Error!','El DNI ingresado no coincide con ningún alumno.','error');
      }
    })
  }

  public checkCurso(criterio: number, formato: string): void{
    this.cursoService.getCurso(criterio).subscribe({
      next: (data) =>{
        if(formato === "pdf"){
          window.location.href=API+`/reporte/inscripcion/pdf?tipo=2&cod=${criterio}`;
        }
        else{
          window.location.href=API+`/reporte/inscripcion/${formato}?tipo=2&codCurso=${criterio}`;
        } 
      },
      error: (err)=>{
        Swal.fire('Error!','El curso seleccionado no existe!.','error');
      }
    })
  }

  llenarCursos(): void{
    this.cursoService.getCursos().subscribe({
      next: (data) =>{
        this.listaCursos = data;
      },
      error: (err) =>{
        Swal.fire({
          title: "Ups! Ha sucedido un error.",
          text: "No se encontraron cursos para realizar reportes.",
          icon: "error"
        })
      }
    })
  }

}
