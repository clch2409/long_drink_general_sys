import { HttpClient, HttpEvent, HttpHeaders, HttpParams, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Tema } from '../models/tema.model';
import { TemaCurso } from '../models/tema.curso.model';

const API = 'http://localhost:8080/api/v1';
const HTTP_OPTIONS = {
  headers: new HttpHeaders({'Content-Type' : 'application/json'})
};

@Injectable({
  providedIn: 'root'
})
export class TemaService {
  

  constructor(private http: HttpClient) { }

  public obtenerTemas(): Observable<TemaCurso[]>{
    return this.http.get<TemaCurso[]>(API+'/tema/temas_cursos');
  }

  public obtenerTemaPorCurso(codCurso: number): Observable<Tema[]>{
    return this.http.get<Tema[]>(API+`/tema/por_curso?codCurso=${codCurso}`);
  }

  public subirGuia(archivo: File, nombre: string): Observable<HttpEvent<any> >{
    const formData: FormData = new FormData();
    formData.append('archivo',archivo);
    formData.append('nombreGuia',nombre);
    const req = new HttpRequest('POST',`${API}/tema/subir_guia`,formData,{
      reportProgress: true,
      responseType: 'json'
    })
    return this.http.request(req);
  }

  public asignarTemasCursos(listadoCodTemas : number[], codCurso : number) : Observable<HttpEvent<any> >{
    const req = new HttpRequest("POST", `${API}/tema?codCurso=${codCurso}`, listadoCodTemas, {
      reportProgress: true,
      responseType: 'json'
    })

    return this.http.request(req)
  }
}
