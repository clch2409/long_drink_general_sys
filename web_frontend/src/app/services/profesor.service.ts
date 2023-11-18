import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Profesor } from '../models/profesor.model';
const API = 'http://localhost:8080/api/v1';
const httpOptions = {
  headers: new HttpHeaders({'Content-Type' : 'application/json'})
};
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

  getProfesorCod(cod: number | any): Observable<Profesor>{
    return this.http.get<Profesor>(API+`/profesor/por_cod?cod=${cod}`);
  }

  getProfesoresDisponibles(): Observable<Profesor[]>{
    return this.http.get<Profesor[]>(API+`/profesor/disponibles`);
  }

  editarProfesor(carga: any): Observable<any>{
    return this.http.put(API+`/profesor`,carga,httpOptions);
  }
}
