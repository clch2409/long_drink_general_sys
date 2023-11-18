import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Alumno } from 'src/app/models/alumno.model';
import { AlumnoService } from 'src/app/services/alumno.service';
import { StorageService } from 'src/app/services/storage.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-editar-alumno',
  templateUrl: './editar-alumno.component.html',
  styleUrls: ['./editar-alumno.component.css']
})
export class EditarAlumnoComponent implements OnInit{
  codAlumno?: number;
  alumno?: Alumno;
  eNombre = '';
  eApP = '';
  eApM = '';
  eTlf = '';
  eEstado = true;
  

  constructor(private storageService: StorageService,private route: ActivatedRoute,private alumnoService: AlumnoService,private router: Router){}
ngOnInit(): void {
  this.storageService.comprobarSesion();
  this.storageService.denegarAcceso('ALUMNOyDOCENTE');
  this.codAlumno = this.route.snapshot.params['codalum']
  this.getAlumno();
}

getAlumno(): void{
  this.alumnoService.getAlumnoCod(this.codAlumno).subscribe({
    next: (data) =>{
      this.alumno = data;
      this.setDatos();
    },
    error: (err) =>{
      Swal.fire("Error!",`Profesor de código: ${this.codAlumno} no encontrado.`,"error").then((result) => {
        if(result.isConfirmed || result.isDismissed){
          this.router.navigate(['dashboard/alumnos']);
        }
      })
    }
  })
}

setDatos(): void{
  let txtNombre = document.getElementById("nombre") as HTMLInputElement;
  let txtApP = document.getElementById("apPaterno") as HTMLInputElement;
  let txtApM = document.getElementById("apMaterno") as HTMLInputElement;
  let txtTlf = document.getElementById("telefono") as HTMLInputElement;
  let cbEstado = document.getElementById("activo") as HTMLInputElement;
  txtNombre.value = this.alumno?.nombre!;
  txtApP.value = this.alumno?.apellidoPaterno!;
  txtApM.value = this.alumno?.apellidoMaterno!;
  txtTlf.value = this.alumno?.telefono!;
  if(this.alumno?.activo){
    cbEstado.value = "true";
  }
  else{
    cbEstado.value = "false";
  }
}

onSubmit(n: string, app: string, apm: string, t: string, e: string): void{
  this.eNombre = n;
  this.eApP = app;
  this.eApM = apm;
  this.eTlf = t;
  this.eEstado = e === "true" ? true : false;
  if(!this.validarNombre()){
    Swal.fire("Ups! Datos incorrectos","Debe llenar el campo nombre.","error");
  }
  else if(!this.validarApP()){
    Swal.fire("Ups! Datos incorrectos","Debe llenar el campo apellido paterno.","error");
  }
  else if(!this.validarApM()){
    Swal.fire("Ups! Datos incorrectos","Debe llenar el campo apellido materno.","error");
  }
  else if(!this.validarTelefono()){
    Swal.fire("Ups! Datos incorrectos","Debe llenar el campo teléfono.","error");
  }
  else{
    const carga = {
      "codAlumno": this.codAlumno,
      "nombre": this.eNombre,
      "apellidoPaterno": this.eApP,
      "apellidoMaterno": this.eApM,
      "telefono": this.eTlf,
      "activo": this.eEstado
    }
    this.alumnoService.editarAlumno(carga).subscribe({
      next: (data) =>{
        Swal.fire("Exito!","Alumno actualizado correctamente.","info").then((result) => {
          if(result.isConfirmed || result.isDismissed){
            this.router.navigate(['dashboard/alumnos']);
          }
        })
      },
      error: (err) =>{
        Swal.fire("Error!","Error en la actualización de profesor. Vuelva a intentarlo.","error").then((result) =>{
          console.log(err);
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
  return this.eNombre.length >= 1 && this.eNombre.length <=50;
}

validarApP(): boolean{
  return this.eApP.length >= 1 && this.eApP.length <= 25;
}

validarApM():boolean{
  return this.eApM.length >= 1 && this.eApM.length <= 25;
}

validarTelefono(): boolean{
  return this.eTlf.length ==9;
}

}
