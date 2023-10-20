import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { ListadoCursosComponent } from './components/curso/listado-cursos/listado-cursos.component';
import { ListadoInscripcionesComponent } from './components/inscripcion/listado-inscripciones/listado-inscripciones.component';

const routes: Routes = [
  { path: '', component: LoginComponent, title: 'Long Drink - Inicio de Sesión' },
  { path: 'dashboard', component: DashboardComponent, title: 'Long Drink - Inicio', children:[
  { path: 'cursos', component: ListadoCursosComponent, title: 'Long Drink - Listado Cursos'},
  { path: 'inscripciones', component: ListadoInscripcionesComponent, title: 'Long Drink - Listado Inscripciones'}
  ]
  },
  
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }