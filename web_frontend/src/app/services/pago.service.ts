import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Pago } from '../models/pago.model';

const API = 'http://localhost:8080/api/v1';
const httpOptions = {
  headers: new HttpHeaders({'Content-Type' : 'application/json'})
};

@Injectable({
  providedIn: 'root'
})
export class PagoService {

  constructor(private http: HttpClient) { }

  getPagosAlumno(codAlumno?: number): Observable<Pago[]>{
    return this.http.get<Pago[]>(API+`/pago?codAlumno=${codAlumno}`);
  }

  nuevoPago(datosPago: any): Observable<any>{
    return this.http.post(
      API+'/pago',
      datosPago,
      httpOptions
    );
  }
}
