import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Turno } from '../models/turno.model';
const API = 'http://localhost:8080/api/v1';
@Injectable({
  providedIn: 'root'
})
export class TurnoService {

  constructor(private http: HttpClient) { }

  public getTurnos(): Observable<Turno[]>{
    return this.http.get<Turno[]>(API+`/turno`);
  }
}
