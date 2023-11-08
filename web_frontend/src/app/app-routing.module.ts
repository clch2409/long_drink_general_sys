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

const routes: Routes = [
  { path: '', component: LoginComponent, title: 'Long Drink - Inicio de Sesión' },
  { path: 'dashboard', component: DashboardComponent, title: 'Long Drink - Inicio', children:[
  { path: 'cursos', component: ListadoCursosComponent, title: 'Long Drink - Listado Cursos' },
  { path: 'inscripciones', component: ListadoInscripcionesComponent, title: 'Long Drink - Listado Inscripciones' },
  { path: 'alumnos', component: ListadoAlumnosComponent, title: 'Long Drink - Listado Alumnos' },
  { path: 'docentes', component: ListadoProfesoresComponent, title: 'Long Drink - Listado Profesores' },
  { path: 'nuevo-docente', component: NuevoProfesorComponent, title: 'Long Drink - Nuevo Profesor' },
  { path: 'detalle-inscripcion/:codalum/:codcurso', component: DetalleInscripcionComponent, title: 'Long Drink - Detalle Inscripción' },
  { path: 'mi-cuenta', component: MiCuentaComponent, title: 'Long Drink - Mi Cuenta' },
  ]
  },  
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }