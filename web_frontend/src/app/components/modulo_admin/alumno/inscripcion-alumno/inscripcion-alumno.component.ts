import { Component, OnInit } from '@angular/core';
import Swal from 'sweetalert2';
import { StorageService } from 'src/app/services/storage.service';
import { SeccionService } from 'src/app/services/seccion.service';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-inscripcion-alumno',
  templateUrl: './inscripcion-alumno.component.html',
  styleUrls: ['./inscripcion-alumno.component.css']
})
export class InscripcionAlumnoComponent implements OnInit {
  cursosDisponibles: any[] = [];
  selectedCurso: any = {};
  nombre: any;
  apellidoPaterno: any;
  apellidoMaterno: any;
  dni: any;
  email: any;
  telefono: any;
  turno: any;
  seccion: any;
  fechaInicio: any;
  fechaFin: any;
  fechaInscripcion: any;

  constructor(
    private storageService: StorageService,
    private seccionService: SeccionService,
    private authService: AuthService
  ) { }

  ngOnInit(): void {
    this.storageService.comprobarSesion();
    this.storageService.denegarAcceso("ALUMNOyDOCENTE");

    this.seccionService.getDisponibles().subscribe(
      (data) => {
        this.cursosDisponibles = data;
      },
      (error) => {
        console.error('Error al obtener la lista de cursos disponibles', error);
      }
    );
    const today = new Date();
    const dd: string | number = today.getDate();
    const mm: string | number = today.getMonth() + 1;
    const yyyy: number = today.getFullYear();

    this.fechaInscripcion = `${yyyy}-${mm < 10 ? '0' + mm : mm}-${dd < 10 ? '0' + dd : dd}`;
  }

  onCursoSelectionChange(): void {
    if (this.selectedCurso && this.selectedCurso.curso) {
      const cursoSeleccionado = this.selectedCurso.curso;

      this.turno = cursoSeleccionado.turnos.length > 0 ? cursoSeleccionado.turnos[0].nombre : '';
      this.seccion = this.selectedCurso.nombre;
      this.fechaInicio = this.selectedCurso.fechaInicio;
      this.fechaFin = this.selectedCurso.fechaFinal;
    } else {
      this.turno = '';
      this.seccion = '';
      this.fechaInicio = '';
      this.fechaFin = '';
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
    this.email = '';
    this.telefono = '';
    this.selectedCurso = {};
    this.turno = '';
    this.seccion = '';
    this.fechaInicio = '';
    this.fechaFin = '';
    const nombreInput = document.getElementById('nombre') as HTMLInputElement;
    nombreInput.focus();
  }

  limpiarError() {
    this.selectedCurso = {};
    this.turno = '';
    this.seccion = '';
    this.fechaInicio = '';
    this.fechaFin = '';
    const cursoInput = document.getElementById('curso') as HTMLInputElement;
    cursoInput.focus();
  }

  onSubmit(): void {
    if (!this.nombre || !this.apellidoPaterno || !this.apellidoMaterno || !this.dni || !this.telefono || !this.email || !this.fechaInicio || !this.fechaFin || !this.fechaInscripcion) {
      Swal.fire('Error', 'Por favor, complete todos los campos correctamente.', 'error').then(() => {
        this.limpiarCampos();
      });
      return;
    }

    if (!this.validarNombre(this.nombre)) {
      Swal.fire('Error', 'Por favor, ingrese un nombre válido sin caracteres numéricos.', 'error').then(() => {
        this.limpiarNombre();
      });
      return;
    }

    if (!this.validarApellido(this.apellidoPaterno)) {
      Swal.fire('Error', 'El apellido paterno no puede contener caracteres numéricos.', 'error').then(() => {
        this.limpiarPaterno();
      });
      return;
    }

    if (!this.validarApellido(this.apellidoMaterno)) {
      Swal.fire('Error', 'El apellido materno no puede contener caracteres numéricos.', 'error').then(() => {
        this.limpiarMaterno();
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
      codSeccion: this.selectedCurso.codSeccion,
      fechaInscripcion: this.fechaInscripcion
    };
    console.log('Datos a enviar:', alumnoData);
    this.authService.registroAlumno(alumnoData).subscribe(
      (response) => {
        Swal.fire('¡Registro exitoso!', 'El alumno se ha registrado correctamente.', 'success').then(() => {
          this.limpiarCampos();
        });
      },
      (error) => {
        let errorMessage = 'Error! Ha sucedido en error en el guardado de datos.';
  
        if (error.error && error.error.mensaje) {
          errorMessage = error.error.mensaje;

          switch (errorMessage.toLowerCase()) {
            case 'error! el dni ingresado ya pertenece a una cuenta registrada.':
              Swal.fire('Error', 'El DNI ingresado ya pertenece a una cuenta registrada.', 'error').then(() => {
                this.limpiarDni();
              });
              break;
            case 'error! el e-mail ingresado ya pertenece a una cuenta registrada.':
              Swal.fire('Error', 'El E-Mail ingresado ya pertenece a una cuenta registrada.', 'error').then(() => {
                this.limpiarEmail();
              });
              break;
            case 'error! el curso seleccionado no tiene turno asignado!':
              Swal.fire('Error', 'El curso seleccionado no tiene turno asignado!', 'error').then(() => {
                this.limpiarError();
              });
              break;
            case 'error! el curso seleccionado no existe!':
              Swal.fire('Error', 'El curso seleccionado no existe!', 'error').then(() => {
                this.limpiarError();
              });
              break;
            case 'error! el curso seleccionado no tiene turno asignado!':
              Swal.fire('Error', 'El curso seleccionado no tiene turno asignado!', 'error').then(() => {
                this.limpiarError();
              });
              break;
            case 'error! los datos ingresados no cuentan con el formato requerido.':
              Swal.fire('Error', 'Los datos ingresados no cuentan con el formato requerido.', 'error').then(() => {
                this.limpiarCampos();
              });
              break;
            case 'error! el curso seleccionado no cuenta con vacantes disponibles.':
              Swal.fire('Error', 'El curso seleccionado no cuenta con vacantes disponibles.', 'error').then(() => {
                this.limpiarError();
              });
              break;
            default:
              Swal.fire('Error', errorMessage, 'error').then(() => {
                this.limpiarCampos();
              });
              break;
          }
        } else {
          Swal.fire('Error', errorMessage, 'error').then(() => {
            this.limpiarCampos();
          });
        }
      }
    );
  }  
}