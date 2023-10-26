import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './components/login/login.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { ListadoCursosComponent } from './components/curso/listado-cursos/listado-cursos.component';
import { HeaderComponent } from './components/extras/header/header.component';
import { ListadoInscripcionesComponent } from './components/inscripcion/listado-inscripciones/listado-inscripciones.component';
import { NuevoCursoComponent } from './components/curso/nuevo-curso/nuevo-curso.component';
import { ListadoAlumnosComponent } from './components/alumno/listado-alumnos/listado-alumnos.component';
import { ListadoProfesoresComponent } from './components/profesor/listado-profesores/listado-profesores.component';
import { NuevoProfesorComponent } from './components/profesor/nuevo-profesor/nuevo-profesor.component';
import { DetalleInscripcionComponent } from './components/inscripcion/detalle-inscripcion/detalle-inscripcion.component';
import { MiCuentaComponent } from './components/extras/mi-cuenta/mi-cuenta.component';

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