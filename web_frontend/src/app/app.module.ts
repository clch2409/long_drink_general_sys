import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './components/shared/login/login.component';
import { DashboardComponent } from './components/shared/dashboard/dashboard.component';
import { ListadoCursosComponent } from './components/modulo_admin/curso/listado-cursos/listado-cursos.component';
import { HeaderComponent } from './components/shared/header/header.component';
import { ListadoInscripcionesComponent } from './components/modulo_admin/inscripcion/listado-inscripciones/listado-inscripciones.component';
import { NuevoCursoComponent } from './components/modulo_admin/curso/nuevo-curso/nuevo-curso.component';
import { ListadoAlumnosComponent } from './components/modulo_admin/alumno/listado-alumnos/listado-alumnos.component';
import { ListadoProfesoresComponent } from './components/modulo_admin/profesor/listado-profesores/listado-profesores.component';
import { NuevoProfesorComponent } from './components/modulo_admin/profesor/nuevo-profesor/nuevo-profesor.component';
import { DetalleInscripcionComponent } from './components/modulo_admin/inscripcion/detalle-inscripcion/detalle-inscripcion.component';
import { MiCuentaComponent } from './components/shared/mi-cuenta/mi-cuenta.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    DashboardComponent,
    ListadoCursosComponent,
    HeaderComponent,
    ListadoInscripcionesComponent,
    NuevoCursoComponent,
    ListadoAlumnosComponent,
    ListadoProfesoresComponent,
    NuevoProfesorComponent,
    DetalleInscripcionComponent,
    MiCuentaComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }