import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Seccion } from '../models/seccion.model';

const API = 'http://localhost:8080/api/v1';
const httpOptions = {
  headers: new HttpHeaders({'Content-Type' : 'application/json'})
};

@Injectable({
  providedIn: 'root'
})
export class SeccionService {

  constructor(private http: HttpClient) { }

  getSecciones(): Observable<Seccion[]>{
    return this.http.get<Seccion[]>(API+'/seccion');
  }

  getSeccion(codSeccion: number): Observable<Seccion>{
    return this.http.get<Seccion>(API+`/seccion/${codSeccion}`);
  }

  getPorTurno(codTurno: number): Observable<Seccion[]>{
    return this.http.get<Seccion[]>(API+`/seccion/por_turno?codTurno=${codTurno}`);
  }

  getPorCurso(codCurso: number): Observable<Seccion[]>{
    return this.http.get<Seccion[]>(API+`/seccion/por_curso?codCurso=${codCurso}`);
  }

  getInactivas(): Observable<Seccion[]>{
    return this.http.get<Seccion[]>(API+`/seccion/inactivas`);
  }

  getDisponibles(): Observable<Seccion[]>{
    return this.http.get<Seccion[]>(API+`/seccion/disponible`);
  }

  getDisponible(codSeccion: number): Observable<Seccion>{
    return this.http.get<Seccion>(API+`/seccion/disponible/${codSeccion}`);
  }

  getActivas(): Observable<Seccion[]>{
    return this.http.get<Seccion[]>(API+`/seccion/activas`);
  }

  asignarSeccion(seccionData: any):Observable<any> {
    return this.http.post(
      API + '/seccion/asignar_curso',
      seccionData,
      httpOptions
    );
  }

}
