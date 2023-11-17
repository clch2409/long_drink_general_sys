import { Component, OnInit } from '@angular/core';
import { Curso } from 'src/app/models/curso.model';
import { CursoService } from 'src/app/services/curso.service';
import { AlumnoService } from 'src/app/services/alumno.service';
import { AuthService } from 'src/app/services/auth.service';
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
    private authService: AuthService,
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

  buscarAlumnoPorDni(): void {
    const dni = (document.getElementById('alumnoDni') as HTMLInputElement).value;
    
    if (dni) {
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
          console.error('Error al buscar alumno por DNI:', error);
        }
      );
    } else {
      console.warn('Por favor, ingrese un número de DNI antes de buscar.');
    }
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
    const nombreInput = document.getElementById('nombre') as HTMLInputElement;
    nombreInput.focus();
  }

  onSubmit(): void {
    if (!this.selectedCursoCod || !this.fechaInicio || !this.fechaFinal || !this.fechaInscripcion) {
      Swal.fire('Error', 'Por favor, complete todos los campos correctamente.', 'error').then(() => {
        this.limpiarCampos();
      });
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
        return;
      },
      (error) => {
        console.error('Error al inscribir al alumno', error);
        Swal.fire('Error', 'Intente de nuevo.', 'error')
      });
    return;
  }
}