import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, map } from 'rxjs';
import { Inscripcion } from '../models/inscripcion.model';
import { DetalleInscripcion } from '../models/detalle.inscripcion.model';

const API = 'http://localhost:8080/api/v1';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type' : 'application/json'})
}; 

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

  confirmarInscripcion(codCurso?: number, codAlumno?: number): Observable<any>{
    return this.http.post(API+`/inscripcion/confirmar_inscripcion?codAlumno=${codAlumno}&codCurso=${codCurso}`,httpOptions);
  }

  detalleInscripcion(codCurso?: number, codAlumno?: number): Observable<DetalleInscripcion>{
    return this.http.get<DetalleInscripcion>(API+`/inscripcion/detalle?codAlumno=${codAlumno}&codCurso=${codCurso}`,httpOptions);
  }

}
