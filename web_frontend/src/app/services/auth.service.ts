import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Profesor } from '../models/profesor.model';

const AUTH_API = 'http://localhost:8080/api/v1/auth/';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type' : 'application/json'})
}; 


@Injectable({
  providedIn: 'root'
})
export class AuthService {
  constructor(private http: HttpClient) { }

  login(nombreUsuario: String, contrasena: String): Observable<any>{
    return this.http.post(
      AUTH_API + 'iniciar_sesion',{
        nombreUsuario,
        contrasena,
      },
      httpOptions
    );
  }

  registroProfesor(profesor: Profesor): Observable<any> {
    return this.http.post(
      AUTH_API + 'registro_docente',
      profesor,
      httpOptions
    );
  }

  cambiarCredenciales(datosCambio: any): Observable<any> {
    return this.http.post(
      AUTH_API + 'cambiar_credenciales',
      datosCambio,
      httpOptions
    );
  }
}