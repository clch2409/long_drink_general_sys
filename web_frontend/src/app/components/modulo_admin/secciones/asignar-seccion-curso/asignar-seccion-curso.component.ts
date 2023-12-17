import { Component, OnInit } from '@angular/core';
import { Curso } from 'src/app/models/curso.model';
import { Profesor } from 'src/app/models/profesor.model';
import { Turno } from 'src/app/models/turno.model';
import { CursoService } from 'src/app/services/curso.service';
import { ProfesorService } from 'src/app/services/profesor.service';
import { StorageService } from 'src/app/services/storage.service';
import { TurnoService } from 'src/app/services/turno.service';
import Swal from 'sweetalert2';
import { DatePipe } from '@angular/common';
import { SeccionService } from 'src/app/services/seccion.service';

@Component({
  selector: 'app-asignar-seccion-curso',
  templateUrl: './asignar-seccion-curso.component.html',
  styleUrls: ['./asignar-seccion-curso.component.css'],
  providers: [DatePipe]
})
export class AsignarSeccionCursoComponent implements OnInit {
  cursosActivos: Curso[] = [];
  profesoresDisponibles: Profesor[] = [];
  turnosDisponibles: Turno[] = [];

  selectedCurso: any = {};
  selectedProfesor: any = {};
  selectedTurno: any = {};
  selectedMaxAlumnos: any;
  selectedFechaInicio: any;
  selectedFechaFinal: any;

  constructor(
    private storageService: StorageService,
    private profesorService: ProfesorService,
    private turnoService: TurnoService,
    private cursoService: CursoService,
    private datePipe: DatePipe,
    private seccionService: SeccionService
  ) { }

  ngOnInit(): void {
    this.storageService.comprobarSesion();
    this.storageService.denegarAcceso("ALUMNOyDOCENTE");

    this.cursoService.getCursosActivos().subscribe(cursos => {
      this.cursosActivos = cursos;
    });

    this.profesorService.getProfesoresDisponibles().subscribe(profesores => {
      this.profesoresDisponibles = profesores;
    });

    this.turnoService.getTurnos().subscribe(turnos => {
      this.turnosDisponibles = turnos;
    });
  }

  onCursoChange(): void {
    console.log('Curso seleccionado:', this.selectedCurso);
    if (this.selectedCurso && this.selectedFechaInicio) {
      const duracionEnSemanas = this.selectedCurso.duracion;
      const fechaInicio = new Date(this.selectedFechaInicio);
      const fechaFinal = new Date(fechaInicio.getTime() + duracionEnSemanas * 7 * 24 * 60 * 60 * 1000);
      this.selectedFechaFinal = fechaFinal.toISOString().split('T')[0];
    }
  }

  onProfesorChange(): void {
    console.log('Profesor seleccionado:', this.selectedProfesor);
  }

  onTurnoChange(): void {
    console.log('Turno seleccionado:', this.selectedTurno);
  }

  onFechaInicioChange(): void {
    console.log('Fecha de Inicio seleccionada:', this.selectedFechaInicio);
    this.validarFechaInicio();
    if (this.selectedCurso && this.selectedFechaInicio) {
      const duracionEnSemanas = this.selectedCurso.duracion;
      const fechaInicio = new Date(this.selectedFechaInicio);
      const fechaFinal = new Date(fechaInicio.getTime() + duracionEnSemanas * 7 * 24 * 60 * 60 * 1000);
      this.selectedFechaFinal = fechaFinal.toISOString().split('T')[0];
    }
  }

  private obtenerFechaActualFormateada(): string {
    const fechaActual = new Date();
    return this.datePipe.transform(fechaActual, 'yyyy-MM-dd') || '';
  }

  private validarFechaInicio(): void {
    const fechaInicio = new Date(this.selectedFechaInicio);
    const fechaActualFormateada = this.obtenerFechaActualFormateada();
    const fechaActual = new Date(fechaActualFormateada);

    if (fechaInicio < fechaActual) {
      Swal.fire({
        icon: 'error',
        title: 'Error',
        text: `La fecha de inicio debe ser igual o posterior a la fecha actual (${fechaActualFormateada}).`,
      }).then(() => {
        this.selectedFechaInicio = null;
      });
    }
  }

  limpiarCampos(): void {
    this.selectedCurso = '';
    this.selectedProfesor = '';
    this.selectedTurno = '';
    this.selectedMaxAlumnos = '';
    this.selectedFechaInicio = '';
    this.selectedFechaFinal = '';
    const cursoSelect = document.getElementById('curso') as HTMLInputElement;
    cursoSelect.focus();
  }

  onSubmit(): void {
    if (!this.selectedCurso || !this.selectedProfesor || !this.selectedTurno || !this.selectedMaxAlumnos || !this.selectedFechaInicio || !this.selectedFechaFinal) {
      Swal.fire('Error', 'Por favor, complete todos los campos correctamente.', 'error').then(() => {
        this.limpiarCampos();
      });
      return;
    }
    const seccionData = {
      fechaInicio: this.selectedFechaInicio,
      fechaFinal: this.selectedFechaFinal,
      estado: true,
      maxAlumnos: this.selectedMaxAlumnos,
      codCurso: this.selectedCurso.codCurso,
      codProfesor: this.selectedProfesor.codProfesor,
      codTurno: this.selectedTurno.codTurno
    };
    console.log('Datos a enviar:', seccionData);
    this.seccionService.asignarSeccion(seccionData).subscribe(
      (response) => {
        Swal.fire('¡Registro exitoso!', 'Seccion asignada a curso correctamente.', 'success').then(() => {
          this.limpiarCampos();
        });
      },
      (error) => {
        let errorMessage = 'Error! Ha sucedido en error en el guardado de datos.';

        if (error.error && error.error.mensaje) {
          errorMessage = error.error.mensaje;

          switch (errorMessage.toLowerCase()) {
            case 'ups! el curso seleccionado no existe o no esta activo.':
              Swal.fire('Error', 'Ups! El curso seleccionado no existe o no esta activo.', 'error').then(() => {
                this.limpiarCampos();
              });
              break;
            case 'ups! el profesor seleccionado no existe o no esta activo.':
              Swal.fire('Error', 'Ups! El profesor seleccionado no existe o no esta activo.', 'error').then(() => {
                this.limpiarCampos();
              });
              break;
            case 'ups! el turno seleccionado no existe.':
              Swal.fire('Error', 'Ups! El turno seleccionado no existe.', 'error').then(() => {
                this.limpiarCampos();
              });
              break;
            case 'ups! ya hay una sección existente para el curso seleccionado con el mismo turno.':
              Swal.fire('Error', 'Ups! Ya hay una sección existente para el curso seleccionado con el mismo turno.', 'error').then(() => {
                this.limpiarCampos();
              });
              break;
            case 'ups! el profesor seleccionado no esta disponible.':
              Swal.fire('Error', 'Ups! El profesor seleccionado no esta disponible.', 'error').then(() => {
                this.limpiarCampos();
              });
              break;
            case 'ups! los datos ingresados no tienen el formato correcto.':
              Swal.fire('Error', 'Ups! Los datos ingresados no tienen el formato correcto.', 'error').then(() => {
                this.limpiarCampos();
              });
              break;
            case 'error! imposible asignar sección a curso. Intente nuevamente.':
              Swal.fire('Error', 'Error! Imposible asignar sección a curso. Intente nuevamente.', 'error').then(() => {
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
