import { Component } from '@angular/core';
import { AuthService } from 'src/app/services/auth.service';
import { Profesor } from 'src/app/models/profesor.model';

@Component({
  selector: 'app-nuevo-profesor',
  templateUrl: './nuevo-profesor.component.html',
  styleUrls: ['./nuevo-profesor.component.css']
})
export class NuevoProfesorComponent {
  profesor: Profesor = new Profesor();

  constructor(private authService: AuthService) {}

  registrarProfesor() {
    this.authService.registroProfesor(this.profesor).subscribe(
      (response) => {
        console.log('Profesor registrado correctamente', response);
        // Puedes realizar redirecciones o mostrar un mensaje de éxito aquí
      },
      (error) => {
        console.error('Error al registrar el profesor', error);
        // Maneja errores aquí, muestra mensajes de error, etc.
      }
    );
  }
}