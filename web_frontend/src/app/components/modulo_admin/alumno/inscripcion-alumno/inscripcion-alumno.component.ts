import { Component, OnInit } from '@angular/core';
import { Curso } from 'src/app/models/curso.model';
import { CursoService } from 'src/app/services/curso.service';
import { AuthService } from 'src/app/services/auth.service';
import Swal from 'sweetalert2';
import { StorageService } from 'src/app/services/storage.service';

@Component({
  selector: 'app-inscripcion-alumno',
  templateUrl: './inscripcion-alumno.component.html',
  styleUrls: ['./inscripcion-alumno.component.css']
})
export class InscripcionAlumnoComponent implements OnInit {
  cursosDisponibles: Curso[] = [];
  selectedCurso: number | undefined;
  selectedCursoNombreTurno: string = '';
  fechaInicio: string = '';
  fechaInscripcion: string = '';
  fechaFinal: string = '';

  selectedCursoCod: number | undefined;
  selectedTurnoCod: number | undefined;

  nombre: string = '';
  apellidoPaterno: string = '';
  apellidoMaterno: string = '';
  dni: string = '';
  telefono: string = '';
  email: string = '';

  constructor(
    private cursoService: CursoService,
    private authService: AuthService,
    private storageService: StorageService
  ) { }

  ngOnInit(): void {
    this.storageService.comprobarSesion();
    this.storageService.denegarAcceso("ALUMNOyDOCENTE");
    this.cursoService.getCursosDisponibles().subscribe(
      (cursos) => {
        this.cursosDisponibles = cursos;
      },
      (error) => {
        console.error('Error al obtener la lista de cursos disponibles:', error);
      }
    );
    const today = new Date();
    this.fechaInscripcion = today.toISOString().split('T')[0];
  }

  onCursoSelectionChange(): void {
    if (this.selectedCurso) {
      this.cursoService.getCurso(this.selectedCurso).subscribe(
        (curso) => {
          if (curso.turnos && curso.turnos.length > 0) {
            this.selectedCursoNombreTurno = curso.turnos[0].nombre || '';
            this.selectedCursoCod = curso.codCurso;
            this.selectedTurnoCod = curso.turnos[0].codTurno;

            const today = new Date();
            const nextMonth = new Date(today.getFullYear(), today.getMonth() + 1, 1);
            this.fechaInicio = nextMonth.toISOString().split('T')[0];

            if (curso.duracion) {
              const duracionEnSemanas = curso.duracion;
              const fechaInicio = new Date(this.fechaInicio);
              const fechaFinal = new Date(fechaInicio.getTime() + duracionEnSemanas * 7 * 24 * 60 * 60 * 1000);
              this.fechaFinal = fechaFinal.toISOString().split('T')[0];
            } else {
              console.error('El curso no tiene duración especificada.');
              this.fechaFinal = '';
            }
          } else {
            console.error('El curso no tiene turnos.');
            this.selectedCursoNombreTurno = '';
            this.selectedCursoCod = undefined;
            this.selectedTurnoCod = undefined;
            this.fechaInicio = '';
            this.fechaFinal = '';
          }
        },
        (error) => {
          console.error('Error al obtener el curso:', error);
          this.selectedCursoNombreTurno = '';
          this.selectedCursoCod = undefined;
          this.selectedTurnoCod = undefined;
          this.fechaInicio = '';
          this.fechaFinal = '';
        }
      );
    } else {
      this.selectedCursoNombreTurno = '';
      this.selectedCursoCod = undefined;
      this.selectedTurnoCod = undefined;
      this.fechaInicio = '';
      this.fechaFinal = '';
    }
  }

  validarNombre(input: string): boolean {
    return isNaN(Number(input));
  }

  validarApellido(input: string): boolean {
    return this.validarNombre(input);
  }

  validarLongitudMaxima(input: string, maxLength: number): boolean {
    return input.length <= maxLength;
  }

  validarDNI(dni: string): boolean {
    const numericDNI = Number(dni);
    return !isNaN(numericDNI) && dni.length >= 8 && dni.length <= 12;
  }

  validarTelefono(telefono: string): boolean {
    const numericTelefono = Number(telefono);
    return !isNaN(numericTelefono) && telefono.length >= 9 && telefono.length <= 15;
  }

  validarEmail(email: string): boolean {
    const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    return emailRegex.test(email) && email.length <= 50;
  }

  limpiarNombre() {
    this.nombre = '';
    const nombreInput = document.getElementById('nombre') as HTMLInputElement;
    nombreInput.focus();
  }

  limpiarPaterno() {
    this.apellidoPaterno = '';
    const paternoInput = document.getElementById('apellidoPaterno') as HTMLInputElement;
    paternoInput.focus();
  }

  limpiarMaterno() {
    this.apellidoMaterno = '';
    const maternoInput = document.getElementById('apellidoMaterno') as HTMLInputElement;
    maternoInput.focus();
  }

  limpiarDni() {
    this.dni = '';
    const dniInput = document.getElementById('dni') as HTMLInputElement;
    dniInput.focus();
  }

  limpiarEmail() {
    this.email = "";
    const emailInput = document.getElementById('email') as HTMLInputElement;
    emailInput.focus();
  }

  limpiarTelefono() {
    this.telefono = '';
    const telefonoInput = document.getElementById('telefono') as HTMLInputElement;
    telefonoInput.focus();
  }

  limpiarEmailYDni() {
    this.dni = '';
    this.email = '';
    const dniInput = document.getElementById('dni') as HTMLInputElement;
    dniInput.focus();
  }

  limpiarCampos(): void {
    this.nombre = '';
    this.apellidoPaterno = '';
    this.apellidoMaterno = '';
    this.dni = '';
    this.telefono = '';
    this.email = '';
    this.selectedCurso = undefined
    this.selectedCursoNombreTurno = ''
    this.fechaInicio = ''
    this.fechaFinal = ''
    const today = new Date();
    this.fechaInscripcion = today.toISOString().split('T')[0];
    const nombreInput = document.getElementById('nombre') as HTMLInputElement;
    nombreInput.focus();
  }

  onSubmit(): void {
    if (!this.nombre || !this.apellidoPaterno || !this.apellidoMaterno || !this.dni || !this.telefono || !this.email || !this.selectedCursoCod || !this.selectedTurnoCod || !this.fechaInicio || !this.fechaFinal || !this.fechaInscripcion) {
      Swal.fire('Error', 'Por favor, complete todos los campos correctamente.', 'error').then(() => {
        this.limpiarCampos();
      });
      return;
    }

    if (!this.validarNombre(this.nombre)) {
      Swal.fire('Error', 'Por favor, ingrese un nombre válido sin caracteres numéricos.', 'error').then(() => {
        this.limpiarCampos();
      });
      return;
    }

    if (!this.validarApellido(this.apellidoPaterno)) {
      Swal.fire('Error', 'El apellido paterno no puede contener caracteres numéricos.', 'error').then(() => {
        this.limpiarCampos();
      });
      return;
    }

    if (!this.validarApellido(this.apellidoMaterno)) {
      Swal.fire('Error', 'El apellido materno no puede contener caracteres numéricos.', 'error').then(() => {
        this.limpiarCampos();
      });
      return;
    }

    if (!this.validarDNI(this.dni)) {
      Swal.fire('Error', 'Por favor, ingrese un DNI válido de entre 8 y 12 dígitos numéricos.', 'error').then(() => {
        this.limpiarDni();
      });
      return;
    }

    if (!this.validarTelefono(this.telefono)) {
      Swal.fire('Error', 'Por favor, ingrese un número de teléfono válido de entre 9 y 15 dígitos numéricos.', 'error').then(() => {
        this.limpiarTelefono();
      });
      return;
    }

    if (!this.validarEmail(this.email)) {
      Swal.fire('Error', 'Por favor, ingrese una dirección de correo electrónico válida.', 'error').then(() => {
        this.limpiarEmail();
      });
      return;
    }

    const alumnoData = {
      nombre: this.nombre,
      apellidoPaterno: this.apellidoPaterno,
      apellidoMaterno: this.apellidoMaterno,
      dni: this.dni,
      telefono: this.telefono,
      email: this.email,
      codCurso: this.selectedCursoCod,
      codTurno: this.selectedTurnoCod,
      fechaInicio: this.fechaInicio,
      fechaFinal: this.fechaFinal,
      fechaInscripcion: this.fechaInscripcion
    };

    this.authService.registroAlumno(alumnoData).subscribe(
      (response) => {
        console.log('Alumno registrado con éxito', response);
        Swal.fire('Éxito', 'Alumno registrado con éxito', 'success').then(() => {
          this.limpiarCampos();
        });
        return;
      },
      (error) => {
        console.error('Error al registrar al alumno', error);
        Swal.fire('Error','Ups! El DNI o E-Mail ingresado ya se encuentran registrados. Intente de nuevo.', 'error').then(() => {
          this.limpiarEmailYDni();
        });
        return;
      });
  }
}