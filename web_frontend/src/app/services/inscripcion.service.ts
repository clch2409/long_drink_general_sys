import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, map } from 'rxjs';
import { Inscripcion } from '../models/inscripcion.model';

const API = 'http://localhost:8080/api/v1';

@Injectable({
  providedIn: 'root'
})
export class InscripcionService {

  constructor(private http: HttpClient) { }

  getInscripciones(): Observable<Inscripcion[]>{
    return this.http.get<Inscripcion[]>(API+'/inscripcion');
  }

  getInscripcionesPendientes(): Observable<Inscripcion[]>{
    return this.http.get<Inscripcion[]>(API+'/inscripcion/pendientes');
  } 

  getInscripcionesAceptadas(): Observable<Inscripcion[]>{
    return this.http.get<Inscripcion[]>(API+'/inscripcion/aceptadas');
  } 

  getInscripcionesPorAlumno(codAlumno: number): Observable<Inscripcion[]>{
    return this.http.get<Inscripcion[]>(API+`/inscripcion/por_alumno?codAlumno=${codAlumno}`);
  } 

  getInscripcionesDniAlumno(dni: string): Observable<Inscripcion[]>{
    return this.http.get<Inscripcion[]>(API+`/inscripcion/por_dni?dni=${dni}`);
  }

  getInscripcionesPorCurso(codCurso: number): Observable<Inscripcion[]>{
    return this.http.get<Inscripcion[]>(API+`/inscripcion/por_curso?codCurso=${codCurso}`);
  } 

  
}
