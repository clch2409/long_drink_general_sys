import { formatDate } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Profesor } from 'src/app/models/profesor.model';
import { ProfesorService } from 'src/app/services/profesor.service';
import { StorageService } from 'src/app/services/storage.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-editar-profesor',
  templateUrl: './editar-profesor.component.html',
  styleUrls: ['./editar-profesor.component.css']
})
export class EditarProfesorComponent implements OnInit {
  codProfesor?: number;
  profesor?: Profesor;
  eNombre = '';
  eApP = '';
  eApM = '';
  eTlf = '';
  eEstado = true;
  eFecha = '';


  constructor(private storageService: StorageService, private route: ActivatedRoute, private profesorService: ProfesorService, private router: Router) { }
  ngOnInit(): void {
    this.storageService.comprobarSesion();
    this.storageService.denegarAcceso('ALUMNOyDOCENTE');
    this.codProfesor = this.route.snapshot.params['codpro']
    this.getProfesor();
  }

  getProfesor(): void {
    this.profesorService.getProfesorCod(this.codProfesor).subscribe({
      next: (data) => {
        this.profesor = data;
        console.log(this.profesor);
        this.setDatos();
      },
      error: (err) => {
        Swal.fire("Error!", `Profesor de código: ${this.codProfesor} no encontrado.`, "error").then((result) => {
          if (result.isConfirmed || result.isDismissed) {
            this.router.navigate(['dashboard/docentes']);
          }
        })
      }
    })
  }

  setDatos(): void {
    let txtNombre = document.getElementById("nombre") as HTMLInputElement;
    let txtApP = document.getElementById("apPaterno") as HTMLInputElement;
    let txtApM = document.getElementById("apMaterno") as HTMLInputElement;
    let txtTlf = document.getElementById("telefono") as HTMLInputElement;
    let cbEstado = document.getElementById("activo") as HTMLInputElement;
    let txtFecha = document.getElementById("fechaC") as HTMLInputElement;
    txtNombre.value = this.profesor?.nombre!;
    txtApP.value = this.profesor?.apellidoPaterno!;
    txtApM.value = this.profesor?.apellidoMaterno!;
    txtTlf.value = this.profesor?.telefono!;
    txtFecha.value = this.profesor?.fechaContratacion?.toLocaleString("GMT-5")!;
    if (this.profesor?.activo) {
      cbEstado.value = "true";
    }
    else {
      cbEstado.value = "false";
    }
  }

  onSubmit(n: string, app: string, apm: string, t: string, e: string, f: string): void {
    this.eNombre = n;
    this.eApP = app;
    this.eApM = apm;
    this.eTlf = t;
    this.eEstado = e === "true" ? true : false;
    this.eFecha = formatDate(new Date(f), 'yyyy-MM-dd', 'en-US', 'UTC');
    if (!this.validarNombre()) {
      Swal.fire("Ups! Datos incorrectos", "Debe llenar el campo nombre.", "error");
    }
    else if (!this.validarApP()) {
      Swal.fire("Ups! Datos incorrectos", "Debe llenar el campo apellido paterno.", "error");
    }
    else if (!this.validarApM()) {
      Swal.fire("Ups! Datos incorrectos", "Debe llenar el campo apellido materno.", "error");
    }
    else if (!this.validarTelefono()) {
      Swal.fire("Ups! Datos incorrectos", "Debe llenar el campo teléfono.", "error");
    }
    else if (!this.validarFecha()) {
      Swal.fire("Ups! Datos incorrectos", "Debe llenar el campo fecha correctamente.", "error");
    }
    else {
      const carga = {
        "codProfesor": this.codProfesor,
        "nombre": this.eNombre,
        "apellidoPaterno": this.eApP,
        "apellidoMaterno": this.eApM,
        "telefono": this.eTlf,
        "fechaContratacion": this.eFecha,
        "activo": this.eEstado
      }

      this.profesorService.editarProfesor(carga).subscribe({
        next: (data) => {
          Swal.fire("Exito!", "Profesor actualizado correctamente.", "info").then((result) => {
            if (result.isConfirmed || result.isDismissed) {
              this.router.navigate(['dashboard/docentes']);
            }
          })
        },
        error: (err) => {
          Swal.fire("Error!", "Error en la actualización de profesor. Vuelva a intentarlo.", "error").then((result) => {
            console.log(err);
            if (result.isConfirmed || result.isDismissed) {
              window.location.reload();
            }
          })
        }
      })
    }
  }

  validarNombre(): boolean {
    return this.eNombre.length >= 1 && this.eNombre.length <= 50;
  }

  validarApP(): boolean {
    return this.eApP.length >= 1 && this.eApP.length <= 25;
  }

  validarApM(): boolean {
    return this.eApM.length >= 1 && this.eApM.length <= 25;
  }

  validarTelefono(): boolean {
    return this.eTlf.length == 9;
  }

  validarFecha(): boolean {
    let fechaNueva = new Date(this.eFecha);
    let retorno = new Date().getTime() - fechaNueva.getTime();
    if (retorno > 0) {
      return true; //Fecha en el pasado, retorna true. Ok.
    }
    return false;
  }

}
