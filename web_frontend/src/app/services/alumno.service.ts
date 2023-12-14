import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, map } from 'rxjs';
import { Alumno } from '../models/alumno.model';
const API = 'http://localhost:8080/api/v1';
const httpOptions = {
  headers: new HttpHeaders({'Content-Type' : 'application/json'})
};
@Injectable({
  providedIn: 'root'
})
export class AlumnoService {

  constructor(private http: HttpClient) { }

  getAlumnos(): Observable<Alumno[]>{
    return this.http.get<Alumno[]>(API+'/alumno')
  }

  getAlumnosActivos(): Observable<Alumno[]>{
    return this.http.get<Alumno[]>(API+'/alumno/activos')
  }

  getAlumnoCod(codAlumno?: number | any): Observable<Alumno>{
    return this.http.get<Alumno>(API+`/alumno/por_cod?codAlum=${codAlumno}`)
  }

  getAlumnoDni(dni: string): Observable<Alumno>{
    return this.http.get<Alumno>(API+`/alumno/${dni}`)
  }

  editarAlumno(carga: any): Observable<any>{
    return this.http.put(API+`/alumno`,carga,httpOptions);
  }

}
