import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Curso } from 'src/app/models/curso.model';
import { Inscripcion } from 'src/app/models/inscripcion.model';
import { Alumno } from 'src/app/models/alumno.model';
import { AlumnoService } from 'src/app/services/alumno.service';
import { InscripcionService } from 'src/app/services/inscripcion.service';
import { StorageService } from 'src/app/services/storage.service';
import { CursoService } from 'src/app/services/curso.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-listado-inscripciones',
  templateUrl: './listado-inscripciones.component.html',
  styleUrls: ['./listado-inscripciones.component.css']
})
export class ListadoInscripcionesComponent {
  sesionIniciada = false;
  rol = '';
  inscripciones: Inscripcion[] = [];
  cursos: Curso[] = [];
  comboBoxSeleccionado: string = 'porCurso';
  valorInput: any;
  filtroSeleccionado: string = 'pendientes';

  constructor(private storageService: StorageService, private router: Router, private inscripcionService: InscripcionService, private cursoService: CursoService, private alumnoService: AlumnoService) { }

  ngOnInit(): void {
    this.comprobarSesion();
    this.getInscripcionesPendientes();
  }

  getInscripcionesPendientes(): void {
    this.inscripcionService.getInscripcionesPendientes().subscribe({
      next: (data) => {
        const inscripciones = data as Inscripcion[];
        this.enriquecerInscripciones(inscripciones);
      },
      error: (err) => { console.log(err); }
    });
  }

  enriquecerInscripciones(inscripciones: Inscripcion[]): void {
    for (const inscripcion of inscripciones) {
      if (inscripcion.inscripcionPk) {
        this.getAlumnoCod(inscripcion.inscripcionPk.codAlumno).subscribe((alumno: Alumno) => {
          inscripcion.alumno = alumno;
        });
        this.getCursoCod(inscripcion.inscripcionPk.codCurso).subscribe((curso: Curso) => {
          inscripcion.curso = curso;
        });
      }
    }
    this.actualizarTabla(inscripciones);
  }

  buscarInscripciones(): void {
    const buscarPor = this.comboBoxSeleccionado;
    const valorInput = this.valorInput;
  
    if (buscarPor === 'porCurso' && valorInput) {
      this.inscripcionService.getInscripcionesPorCurso(Number(valorInput)).subscribe({
        next: (data) => {
          if (data && Array.isArray(data)) {
            const inscripciones = data as Inscripcion[];
            this.enriquecerInscripciones(inscripciones);
          }
        },
        error: (err) => {
          if (err.status === 404) {
            Swal.fire({
              icon: 'info',
              title: 'Información',
              text: 'En este curso aún no se han inscrito alumnos.',
            }).then(() => {
              this.valorInput = '';
              document.getElementById('texto')?.focus();
            });
          } else {
            Swal.fire({
              icon: 'error',
              title: 'Datos Incorrectos',
              text: 'Por favor ingrese valores numéricos.',
            }).then(() => {
              this.valorInput = '';
            });
          }
        }
      });
    } else if (buscarPor === 'porDni' && /^[0-9]{8}$/.test(valorInput)) {
      this.inscripcionService.getInscripcionesDniAlumno(valorInput).subscribe({
        next: (data) => {
          if (data && Array.isArray(data)) {
            const inscripciones = data as Inscripcion[];
            this.enriquecerInscripciones(inscripciones);
          }
        },
        error: (err) => {
          if (err.status === 404) {
            Swal.fire({
              icon: 'info',
              title: 'Información',
              text: 'El alumno con el DNI ingresado no se ha inscrito a ningún curso.',
            }).then(() => {
              this.valorInput = '';
            });
          }
        }
      });
    } else {
      Swal.fire({
        icon: 'error',
        title: 'Error',
        text: 'Ingrese los datos correctamente.',
      }).then(() => {
        this.valorInput = '';
      });
    }
  }

  actualizarTabla(resultados: Inscripcion[]): void {
    this.inscripciones = resultados;
  }

  getCursoCod(codCurso: number): any {
    return this.cursoService.getCurso(codCurso);
  }


  getInscripciones(): void {
    this.inscripcionService.getInscripciones().subscribe({
      next: (data) => {
        this.inscripciones = data;
        console.log(this.inscripciones);
      },
      error: (err) => { console.log(err); }
    });
  }

  getInscripcionesAceptadas(): void {
    this.inscripcionService.getInscripcionesAceptadas().subscribe({
      next: (data) => {
        this.inscripciones = data;
        console.log(this.inscripciones);
      },
      error: (err) => { console.log(err); }
    });
  }

  getInscripcionesAlumno(codAlumno: number): void {
    this.inscripcionService.getInscripcionesPorAlumno(codAlumno).subscribe({
      next: (data) => {
        this.inscripciones = data;
        console.log(this.inscripciones);
      },
      error: (err) => { console.log(err); }
    });
  }

  getInscripcionesCurso(codCurso: number): void {
    this.inscripcionService.getInscripcionesPorCurso(codCurso).subscribe({
      next: (data) => {
        this.enriquecerInscripciones(data);
      },
      error: (err) => { console.log(err); }
    });
  }

  getInscripcionesDniAlumno(dni: string): void {
    this.inscripcionService.getInscripcionesDniAlumno(dni).subscribe({
      next: (data) => {
        this.enriquecerInscripciones(data);
      },
      error: (err) => { console.log(err); }
    });
  }

  getAlumnoCod(codAlum: number): any {
    return this.alumnoService.getAlumnoCod(codAlum);
  }

  comprobarSesion(): void {
    if (this.storageService.sesionIniciada()) {
      this.sesionIniciada = true;
      this.rol = this.storageService.obtenerUsuario().rol;
    } else {
      this.router.navigate(['/']);
    }
  }

  filtrarInscripciones(): void {
    if (this.filtroSeleccionado === 'pendientes') {
      this.inscripcionService.getInscripcionesPendientes().subscribe({
        next: (data) => {
          const inscripciones = data as Inscripcion[];
          this.enriquecerInscripciones(inscripciones);
        },
        error: (err) => {
          console.log(err);
        }
      });
    } else if (this.filtroSeleccionado === 'aceptadas') {
      this.inscripcionService.getInscripcionesAceptadas().subscribe({
        next: (data) => {
          const inscripciones = data as Inscripcion[];
          this.enriquecerInscripciones(inscripciones);
        },
        error: (err) => {
          console.log(err);
        }
      });
    } else if (this.filtroSeleccionado === 'generales') {
      this.inscripcionService.getInscripciones().subscribe({
        next: (data) => {
          const inscripciones = data as Inscripcion[];
          this.enriquecerInscripciones(inscripciones);
        },
        error: (err) => {
          console.log(err);
        }
      });
    }
  }
}