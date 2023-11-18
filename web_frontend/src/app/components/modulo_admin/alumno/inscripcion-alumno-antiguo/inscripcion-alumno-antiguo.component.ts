import { Component, OnInit } from '@angular/core';
import { Curso } from 'src/app/models/curso.model';
import { CursoService } from 'src/app/services/curso.service';
import { AlumnoService } from 'src/app/services/alumno.service';
import Swal from 'sweetalert2';
import { StorageService } from 'src/app/services/storage.service';
import { InscripcionService } from 'src/app/services/inscripcion.service';

@Component({
  selector: 'app-inscripcion-alumno-antiguo',
  templateUrl: './inscripcion-alumno-antiguo.component.html',
  styleUrls: ['./inscripcion-alumno-antiguo.component.css']
})
export class InscripcionAlumnoAntiguoComponent implements OnInit {
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
  codAlumno: number = 0;

  constructor(
    private cursoService: CursoService,
    private storageService: StorageService,
    private alumnoService: AlumnoService,
    private inscripcionService: InscripcionService
  ) { }

  ngOnInit(): void {
    this.storageService.comprobarSesion();
    this.storageService.denegarAcceso("ALUMNOyDOCENTE");
    this.cursoService.getCursosActivos().subscribe(
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
    const dniInput = document.getElementById('alumnoDni') as HTMLInputElement;
    dniInput.value = ''
    dniInput.focus();
  }

  limpiarError() {
    this.selectedCurso = undefined
    this.selectedCursoNombreTurno = ''
    this.fechaInicio = ''
    this.fechaFinal = ''
    const today = new Date();
    this.fechaInscripcion = today.toISOString().split('T')[0];
  }

  onSubmit(): void {
    if (!this.codAlumno || !this.selectedCursoCod || !this.fechaInicio || !this.fechaFinal || !this.fechaInscripcion || !this.fechaFinal) {
      Swal.fire('Error', 'Debe seleccionar un alumno y escoger un curso', 'error');
      return;
    }
  
    const alumnoData = {
      codAlumno: this.codAlumno,
      codCurso: this.selectedCursoCod,
      estado: true,
      fechaInicio: this.fechaInicio,
      fechaFinal: this.fechaFinal,
      fechaInscripcion: this.fechaInscripcion,
      fechaTerminado: this.fechaFinal
    };
  
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
                this.limpiarError();
              });
              break;
            case 'ups! curso no encontrado!':
              Swal.fire('Error', 'Curso no encontrado.', 'error').then(() => {
                this.limpiarError();
              });
              break;
            case 'ups! alumno deshabilitado.':
              Swal.fire('Error', 'Alumno deshabilitado.', 'error').then(() => {
                this.limpiarError();
              });
              break;
            case 'error! el curso seleccionado no cuenta con vacantes disponibles.':
              Swal.fire('Error', 'El curso seleccionado no tiene vacantes disponibles.', 'error').then(() => {
                this.limpiarError();
              });
              break;
            case 'error! el alumno seleccionado debe terminar sus cursos en proceso.':
              Swal.fire('Error', 'El alumno seleccionado debe terminar sus cursos en proceso.', 'error').then(() => {
                this.limpiarError();
              });
              break;
            case 'error! el curso seleccionado no tiene turnos asignados.':
              Swal.fire('Error', 'El curso seleccionado no tiene turnos asignados.', 'error').then(() => {
                this.limpiarError();
              });
              break;
            case 'error! ha sucedido en error en el guardado de datos.':
              Swal.fire('Error', 'Ha ocurrido un error en el guardado de datos.', 'error').then(() => {
                this.limpiarError();
              });
              break;
            default:
              Swal.fire('Error', errorMessage, 'error').then(() => {
                this.limpiarError();
              });
              break;
          }
        } else {
          Swal.fire('Error', errorMessage, 'error').then(() => {
            this.limpiarError();
          });
        }
      }
    );
  }  
}