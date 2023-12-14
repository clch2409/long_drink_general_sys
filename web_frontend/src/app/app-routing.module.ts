import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './components/shared/login/login.component';
import { DashboardComponent } from './components/shared/dashboard/dashboard.component';
import { ListadoCursosComponent } from './components/modulo_admin/curso/listado-cursos/listado-cursos.component';
import { ListadoInscripcionesComponent } from './components/modulo_admin/inscripcion/listado-inscripciones/listado-inscripciones.component';
import { ListadoAlumnosComponent } from './components/modulo_admin/alumno/listado-alumnos/listado-alumnos.component';
import { ListadoProfesoresComponent } from './components/modulo_admin/profesor/listado-profesores/listado-profesores.component';
import { NuevoProfesorComponent } from './components/modulo_admin/profesor/nuevo-profesor/nuevo-profesor.component';
import { DetalleInscripcionComponent } from './components/modulo_admin/inscripcion/detalle-inscripcion/detalle-inscripcion.component';
import { MiCuentaComponent } from './components/shared/mi-cuenta/mi-cuenta.component';
import { MisCursosComponent } from './components/modulo_alumnos/mis-cursos/mis-cursos.component';
import { DetalleCursoAlumnoComponent } from './components/modulo_alumnos/detalle-curso-alumno/detalle-curso-alumno.component';
import { MisCursosProfesorComponent } from './components/modulo_profesores/mis-cursos-profesor/mis-cursos-profesor.component';
import { DetalleCursoProfesorComponent } from './components/modulo_profesores/detalle-curso-profesor/detalle-curso-profesor.component';
import { ExportarCursosComponent } from './components/modulo_admin/exportaciones/exportar-cursos/exportar-cursos.component';
import { ExportarAlumnosComponent } from './components/modulo_admin/exportaciones/exportar-alumnos/exportar-alumnos.component';
import { ExportarDocentesComponent } from './components/modulo_admin/exportaciones/exportar-docentes/exportar-docentes.component';
import { ExportarInscripcionesComponent } from './components/modulo_admin/exportaciones/exportar-inscripciones/exportar-inscripciones.component';
import { ExportarGeneralComponent } from './components/modulo_admin/exportaciones/exportar-general/exportar-general.component';
import { InscripcionAlumnoComponent } from './components/modulo_admin/alumno/inscripcion-alumno/inscripcion-alumno.component';
import { GuiasEstudioComponent } from './components/modulo_admin/curso/guias-estudio/guias-estudio.component';
import { SetGuiasEstudioComponent } from './components/modulo_admin/curso/set-guias-estudio/set-guias-estudio.component';
import { InscripcionAlumnoAntiguoComponent } from './components/modulo_admin/alumno/inscripcion-alumno-antiguo/inscripcion-alumno-antiguo.component';
import { NuevoCursoComponent } from './components/modulo_admin/curso/nuevo-curso/nuevo-curso.component';
import { EditarProfesorComponent } from './components/modulo_admin/profesor/editar-profesor/editar-profesor.component';
import { EditarAlumnoComponent } from './components/modulo_admin/alumno/editar-alumno/editar-alumno.component';
import { EditarCursoComponent } from './components/modulo_admin/curso/editar-curso/editar-curso.component';
import { RecuperarCuentaComponent } from './components/shared/recuperar-cuenta/recuperar-cuenta.component';
import { MisPagosComponent } from './components/modulo_alumnos/mis-pagos/mis-pagos.component';
import { MarcarAsistenciaComponent } from './components/modulo_alumnos/marcar-asistencia/marcar-asistencia.component';
import { ListadoAsistenciasComponent } from './components/modulo_admin/asistencias/listado-asistencias/listado-asistencias.component';
import { ListadoSeccionesComponent } from './components/modulo_admin/secciones/listado-secciones/listado-secciones.component';
import { AsignarSeccionCursoComponent } from './components/modulo_admin/secciones/asignar-seccion-curso/asignar-seccion-curso.component';

const routes: Routes = [
  { path: '', component: LoginComponent, title: 'Long Drink - Inicio de Sesión' },
  { path: 'recuperar-cuenta', component: RecuperarCuentaComponent, title: 'Long Drink - Recuperación de Cuenta' },
  { path: 'dashboard', component: DashboardComponent, title: 'Long Drink - Inicio', children:[
  { path: 'cursos', component: ListadoCursosComponent, title: 'Long Drink - Listado Cursos' },
  { path: 'inscripciones', component: ListadoInscripcionesComponent, title: 'Long Drink - Listado Inscripciones' },
  { path: 'alumnos', component: ListadoAlumnosComponent, title: 'Long Drink - Listado Alumnos' },
  { path: 'docentes', component: ListadoProfesoresComponent, title: 'Long Drink - Listado Profesores' },
  { path: 'nuevo-docente', component: NuevoProfesorComponent, title: 'Long Drink - Nuevo Profesor' },
  { path: 'detalle-inscripcion/:codins', component: DetalleInscripcionComponent, title: 'Long Drink - Detalle Inscripción' },
  { path: 'mi-cuenta', component: MiCuentaComponent, title: 'Long Drink - Mi Cuenta' },
  { path: 'exp/cursos', component: ExportarCursosComponent, title: 'Long Drink - Reporte de Cursos'},
  { path: 'exp/alumnos', component: ExportarAlumnosComponent, title: 'Long Drink - Reporte de Alumnos'},
  { path: 'exp/profesores', component: ExportarDocentesComponent, title: 'Long Drink - Reporte de Profesores'},
  { path: 'exp/inscripciones', component: ExportarInscripcionesComponent, title: 'Long Drink - Reporte de Inscripciones'},
  { path: 'exp/general', component: ExportarGeneralComponent, title: 'Long Drink - Reportes Generales' },
  { path: 'nuevo-alumno', component: InscripcionAlumnoComponent, title: 'Long Drink - Inscripción: Nuevo Alumno' },
  { path: 'guias-estudio', component: GuiasEstudioComponent, title: 'Long Drink - Gestión de Guías de Estudio' },
  { path: 'definir-ge', component: SetGuiasEstudioComponent, title: 'Long Drink - Configurar Guías de Estudio' },
  { path: 'antiguo-alumno', component: InscripcionAlumnoAntiguoComponent, title: 'Long Drink - Inscripción: Alumno Existente' },
  { path: 'nuevo-curso', component: NuevoCursoComponent, title: 'Long Drink - Nuevo Curso'},
  { path: 'editar-profesor/:codpro', component: EditarProfesorComponent, title: 'Long Drink - Gestión de Profesor' },
  { path: 'editar-alumno/:codalum', component: EditarAlumnoComponent, title: 'Long Drink - Gestión de Alumno' },
  { path: 'editar-curso/:codcur', component: EditarCursoComponent, title: 'Long Drink - Gestión de Curso' },
  { path: 'asistencias', component: ListadoAsistenciasComponent, title: 'Long Drink - Gestión de Asistencias' },
  { path: 'secciones', component: ListadoSeccionesComponent, title: 'Long Drink - Listado de Secciones' },
  { path: 'c/asignar-secciones', component: AsignarSeccionCursoComponent, title: 'Long Drink - Gestión de Curso: Secciones' },
  ]
  },
  { path: 'a', component: DashboardComponent, title: 'Long Drink - Área Estudiantil', children: [
    { path: 'mis-cursos', component: MisCursosComponent, title: 'Long Drink - Mis Cursos' },
    { path: 'mi-curso/:codalum/:codins/:codcurso', component: DetalleCursoAlumnoComponent, title: 'Long Drink - Curso: xxxx'},
    { path: 'mi-cuenta', component: MiCuentaComponent, title: 'Long Drink - Mi Cuenta' },
    { path: 'pagos', component: MisPagosComponent, title: 'Long Drink - Mis Pagos' },
    { path: 'asistencia', component: MarcarAsistenciaComponent, title: 'Long Drink - Marcado de Asistencia' }
  ]},
  { path: 'p', component: DashboardComponent, title: 'Long Drink - Área Docente', children: [
    { path: 'mis-cursos', component: MisCursosProfesorComponent, title: 'Long Drink - Mis Cursos' },
    { path: 'mi-curso/:codprof/:codcur', component: DetalleCursoProfesorComponent, title: 'Long Drink - Curso: xxxx'},
    { path: 'mi-cuenta', component: MiCuentaComponent, title: 'Long Drink - Mi Cuenta' }
  ]}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
