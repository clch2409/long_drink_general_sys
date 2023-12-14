import { Component, OnInit } from '@angular/core';
import { AlumnoService } from 'src/app/services/alumno.service';
import Swal from 'sweetalert2';
import { StorageService } from 'src/app/services/storage.service';
import { InscripcionService } from 'src/app/services/inscripcion.service';
import { SeccionService } from 'src/app/services/seccion.service';
import { Seccion } from 'src/app/models/seccion.model';

@Component({
  selector: 'app-inscripcion-alumno-antiguo',
  templateUrl: './inscripcion-alumno-antiguo.component.html',
  styleUrls: ['./inscripcion-alumno-antiguo.component.css']
})
export class InscripcionAlumnoAntiguoComponent implements OnInit {

  cursosDisponibles: Seccion[] = [];
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
  codAlumno: any;

  constructor(
    private seccionService: SeccionService,
    private storageService: StorageService,
    private alumnoService: AlumnoService,
    private inscripcionService: InscripcionService
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
      this.turno = this.selectedCurso.turno.nombre;//cursoSeleccionado.turnos.length > 0 ? cursoSeleccionado.turnos[0].nombre : '';
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

  limpiarDniYEnfocar(): void {
    const dniInput = document.getElementById('alumnoDni') as HTMLInputElement;
    dniInput.value = '';
    dniInput.focus();
  }

  validarDni(dni: string): boolean {
    if (dni.trim() === '') {
      Swal.fire('Error', 'El DNI no puede estar vacío', 'error').then(() => {
        this.limpiarDniYEnfocar();
      });
      return false;
    } else if (!/^\d+$/.test(dni)) {
      Swal.fire('Error', 'El DNI debe contener solo números', 'error').then(() => {
        this.limpiarDniYEnfocar();
      });
      return false;
    } else if (dni.length < 8 || dni.length > 12) {
      Swal.fire('Error', 'El DNI debe tener entre 8 y 12 dígitos', 'error').then(() => {
        this.limpiarDniYEnfocar();
      });
      return false;
    }
    return true;
  }

  buscarAlumnoPorDni(): void {
    const dniInput = document.getElementById('alumnoDni') as HTMLInputElement;
    const dni = dniInput.value.trim();

    if (dni === '') {
      Swal.fire('Error', 'El DNI no puede estar vacío', 'error').then(() => {
        this.limpiarDniYEnfocar();
      });
      return;
    }

    if (!/^\d+$/.test(dni)) {
      Swal.fire('Error', 'El DNI debe contener solo números', 'error').then(() => {
        this.limpiarDniYEnfocar();
      });
      return;
    }

    if (dni.length < 8 || dni.length > 12) {
      Swal.fire('Error', 'El DNI debe tener entre 8 y 12 dígitos', 'error').then(() => {
        this.limpiarDniYEnfocar();
      });
      return;
    }

    this.alumnoService.getAlumnoDni(dni).subscribe(
      (alumno) => {
        console.log('Resultado de búsqueda:', alumno);
        this.codAlumno = alumno.codAlumno || 0;
        this.nombre = alumno.nombre || '';
        this.apellidoPaterno = alumno.apellidoPaterno || '';
        this.apellidoMaterno = alumno.apellidoMaterno || '';
        this.dni = alumno.dni || '';
        this.telefono = alumno.telefono || '';
      },
      (error) => {
        Swal.fire('Error', 'No se encontro el alumno con el dni ingresado', 'error').then(() => {
          this.limpiarDniYEnfocar();
        });
      }
    );
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
    const dniInput = document.getElementById('alumnoDni') as HTMLInputElement;
    dniInput.value = '';
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
    if (!this.codAlumno || !this.seccion || !this.fechaInscripcion) {
      Swal.fire('Error', 'Debe seleccionar un alumno y escoger un curso', 'error');
      return;
    }

    const alumnoData = {
      codAlumno: this.codAlumno,
      codSeccion: this.selectedCurso.codSeccion,
      fechaInscripcion: this.fechaInscripcion
    };
  console.log(alumnoData);
    this.inscripcionService.registroInscripcion(alumnoData).subscribe(
      (response) => {
        console.log('Alumno inscrito con éxito', response);
        Swal.fire('Éxito', 'Alumno inscrito con éxito', 'success').then(() => {
          this.limpiarCampos();
        });
      },
      (error) => {
        console.error('Error al inscribir al alumno', error);

        let errorMessage = 'Error al procesar la solicitud. Intente de nuevo por favor.';

        if (error.error && error.error.mensaje) {
          errorMessage = error.error.mensaje;

          // Manejar diferentes mensajes de error
          switch (errorMessage.toLowerCase()) {
            case 'ups! alumno no encontrado o deshabilitado.':
              Swal.fire('Error', 'Alumno no encontrado o deshabilitado.', 'error').then(() => {
                this.limpiarCampos();
              });
              break;
            case 'ups! curso no encontrado!':
              Swal.fire('Error', 'Curso no encontrado.', 'error').then(() => {
                this.limpiarError();
              });
              break;
            case 'ups! alumno deshabilitado.':
              Swal.fire('Error', 'Alumno deshabilitado.', 'error').then(() => {
                this.limpiarCampos();
              });
              break;
            case 'error! el curso seleccionado no cuenta con vacantes disponibles.':
              Swal.fire('Error', 'El curso seleccionado no tiene vacantes disponibles.', 'error').then(() => {
                this.limpiarError();
              });
              break;
            case 'error! el alumno seleccionado debe terminar sus cursos en proceso.':
              Swal.fire('Error', 'El alumno seleccionado debe terminar sus cursos en proceso.', 'error').then(() => {
                this.limpiarCampos();
              });
              break;
            case 'error! el curso seleccionado no tiene turnos asignados.':
              Swal.fire('Error', 'El curso seleccionado no tiene turnos asignados.', 'error').then(() => {
                this.limpiarError();
              });
              break;
            case 'error! ha sucedido en error en el guardado de datos.':
              Swal.fire('Error', 'Ha ocurrido un error en el guardado de datos.', 'error').then(() => {
                this.limpiarCampos();
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
