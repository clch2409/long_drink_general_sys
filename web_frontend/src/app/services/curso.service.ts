import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, map } from 'rxjs';
import { Curso } from '../models/curso.model';

const API = 'http://localhost:8080/api/v1';

@Injectable({
  providedIn: 'root'
})
export class CursoService {

  constructor(private http: HttpClient) { }

  getCursos(): Observable<Curso[]>{
    return this.http.get<Curso[]>(API+'/curso');
  }

  getCursosActivos(): Observable<Curso[]>{
    return this.http.get<Curso[]>(API+'/curso/activos');
  }

  getCurso(cod?: number): Observable<Curso>{
    return this.http.get<Curso>(API+'/curso/'+cod);
  }
  
}
