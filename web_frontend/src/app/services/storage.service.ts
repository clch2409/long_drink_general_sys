import { Injectable } from '@angular/core';
import { Router } from '@angular/router';

const USER_DATA = 'auth-user';

@Injectable({
  providedIn: 'root'
})
export class StorageService {

  constructor(private router: Router) { }

  limpiarCredenciales(): void {
    window.sessionStorage.clear();
  }

  public guardarUsuario(loginWeb: any): void {
    window.sessionStorage.removeItem(USER_DATA);
    window.sessionStorage.setItem(USER_DATA, JSON.stringify(loginWeb));
  }

  public obtenerUsuario(): any {
    const usuario = window.sessionStorage.getItem(USER_DATA);
    if (usuario) {
      return JSON.parse(usuario);
    }
    return null;
  }

  public sesionIniciada(): boolean {
    const usuario = window.sessionStorage.getItem(USER_DATA);
    return !!usuario;
  }

  public cerrarSesion(): void {
    window.sessionStorage.removeItem(USER_DATA);
    window.location.href = '/';
  }

  public comprobarSesion(): void {
    if(!this.sesionIniciada()){
      window.location.href = '/';
    }
  }

  public obtenerRol(): any{
    return this.obtenerUsuario().rol;
  }

  public denegarAcceso(criterio: string): void {
    //Caso: Denegar acceso a alumnos y docentes (Módulo administrativo)
    if(criterio === 'ALUMNOyDOCENTE'){
      if(this.obtenerRol() === 'ALUMNO'){ this.router.navigate(['/a']); } //Redirigir a módulo de alumno.
      if(this.obtenerRol() === 'DOCENTE'){ this.router.navigate(['/p']); } //Redirigir a módulo de docente.
    }
    //Caso: Denegar acceso a administradores y docentes (Módulo alumno)
    if(criterio === 'ADMINISTRADORyDOCENTE'){
      if(this.obtenerRol() === 'ADMINISTRADOR'){ this.router.navigate(['/']); }
      if(this.obtenerRol() === 'DOCENTE'){ this.router.navigate(['/p']); } 
    }
    //Caso: Denegar acceso a administradores y alumnos (Módulo docente)
    if(criterio === 'ADMINISTRADORyALUMNO'){
      if(this.obtenerRol() === 'ADMINISTRADOR'){ this.router.navigate(['/']); }
      if(this.obtenerRol() === 'ALUMNO'){ this.router.navigate(['/a']); }
    }
    }
  
}