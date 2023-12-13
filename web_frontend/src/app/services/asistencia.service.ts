import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Asistencia } from '../models/asistencia.model';
import { Observable } from 'rxjs';

const API = 'http://localhost:8080/api/v1';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type' : 'application/json'})
};

@Injectable({
  providedIn: 'root'
})
export class AsistenciaService {

  constructor(private http: HttpClient) { }

  marcarAsistencia(codIns: number, fechaA: string, hora: string): Observable<any>{
    return this.http.post(
      API+"/asistencia",
      {
        codInscripcion: codIns,
        fechaAsistencia: fechaA,
        horaLlegada: hora,
        estado: true
      },
      httpOptions
    );
  }

  comprobarAsistencia(codIns: number, fechaA: string): Observable<any>{
    return this.http.get(API+`/asistencia/comprobar?fechaAsistencia=${fechaA}&codInscripcion=${codIns}`);
  }
}
