import { Component } from '@angular/core';
import { AuthService } from 'src/app/services/auth.service';
import { Profesor } from 'src/app/models/profesor.model';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-nuevo-profesor',
  templateUrl: './nuevo-profesor.component.html',
  styleUrls: ['./nuevo-profesor.component.css']
})
export class NuevoProfesorComponent {
  profesor: Profesor;
  fechaContratacionFormatted: string;

  constructor(private authService: AuthService) {
    this.profesor = new Profesor();
    this.fechaContratacionFormatted = this.formatDate(new Date());
  }

  formatDate(date: Date): string {
    const year = date.getFullYear();
    const month = (date.getMonth() + 1).toString().padStart(2, '0');
    const day = date.getDate().toString().padStart(2, '0');
    return `${year}-${month}-${day}`;
  }

  limpiarDni() {
    this.profesor.dni = '';
    const dniInput = document.getElementById('dni') as HTMLInputElement;
    dniInput.focus();
  }

  limpiarEmail() {
    this.profesor.email = "";
    const emailInput = document.getElementById('email') as HTMLInputElement;
    emailInput.focus();
  }

  limpiarTelefono() {
    this.profesor.telefono = '';
    const telefonoInput = document.getElementById('telefono') as HTMLInputElement;
    telefonoInput.focus();
  }

  limpiarEmailYDni() {
    this.profesor.dni = '';
    this.profesor.email = '';
    const dniInput = document.getElementById('dni') as HTMLInputElement;
    dniInput.focus();
  }

  limpiarDatos() {
    this.profesor = new Profesor();
    const nombreInput = document.getElementById('nombre') as HTMLInputElement;
    nombreInput.focus();
  }

  validarEmail(email: string): boolean {
    const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    return emailRegex.test(email);
  }

  registrarProfesor() {
    if (!this.profesor.nombre || !this.profesor.apellidoPaterno || !this.profesor.apellidoMaterno || !this.profesor.dni || !this.profesor.telefono || !this.profesor.email || !this.profesor.contrasena || !this.fechaContratacionFormatted) {
      Swal.fire('Error', 'Por favor, complete todos los campos correctamente.', 'error').then(() => {
        this.limpiarDatos();
      });
      return;
    }

    if (!this.profesor.dni || !/^\d+$/.test(this.profesor.dni) || this.profesor.dni.length < 8 || this.profesor.dni.length > 12) {
      Swal.fire('Error', 'El DNI debe ser un número entre 8 y 12 dígitos.', 'error').then(() => {
        this.limpiarDni();
      });
      return;
    }

    if (!this.profesor.telefono || !/^\d+$/.test(this.profesor.telefono) || this.profesor.telefono.length < 9 || this.profesor.telefono.length > 15) {
      Swal.fire('Error', 'El teléfono debe ser un número entre 9 y 15 dígitos.', 'error').then(() => {
        this.limpiarTelefono();
      });
      return;
    }

    if (!this.validarEmail(this.profesor.email)) {
      Swal.fire('Error', 'El formato del correo electrónico es inválido.', 'error').then(() => {
        this.limpiarEmail();
      });
      return;
    }

    this.profesor.fechaContratacion = new Date(this.fechaContratacionFormatted);
    this.authService.registroProfesor(this.profesor).subscribe(
      (response) => {
        console.log('Profesor registrado correctamente', response);
        Swal.fire('Éxito', 'El profesor ha sido registrado exitosamente.', 'success').then(() => {
          this.limpiarDatos();
        });
      },
      (error) => {
        Swal.fire('Error', 'Las credenciales ingresadas ya existen. Por favor intente de nuevo', 'error').then(() => {
          this.limpiarEmailYDni();
        });
      }
    );
  }
}