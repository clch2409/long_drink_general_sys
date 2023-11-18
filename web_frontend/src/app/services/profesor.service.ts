import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Profesor } from '../models/profesor.model';
const API = 'http://localhost:8080/api/v1';
@Injectable({
  providedIn: 'root'
})
export class ProfesorService {

  constructor(private http: HttpClient) { }

  getProfesores(): Observable<Profesor[]>{
    return this.http.get<Profesor[]>(API+'/profesor');
  }

  getProfesoresActivos(): Observable<Profesor[]>{
    return this.http.get<Profesor[]>(API+'/profesor/activos');
  }

  getProfesorDNI(dni: string): Observable<any>{
    return this.http.get<any>(API+'/profesor/'+dni);
  }

  getProfesoresDisponibles(): Observable<Profesor[]>{
    return this.http.get<Profesor[]>(API+`/profesor/disponibles`);
  }
}
