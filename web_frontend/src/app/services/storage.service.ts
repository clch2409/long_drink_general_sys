import { Injectable } from '@angular/core';

const USER_DATA = 'auth-user';

@Injectable({
  providedIn: 'root'
})
export class StorageService {

  constructor() { }

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
      if(this.obtenerRol() === 'ALUMNO'){ window.location.href = '/'; } //Redirigir a módulo de alumno.
      if(this.obtenerRol() === 'DOCENTE'){ window.location.href = '/'; } //Redirigir a módulo de docente.
    }
    //Caso: Denegar acceso a administradores y docentes (Módulo alumno)
    if(criterio === 'ADMINISTRADORyDOCENTE'){
      if(this.obtenerRol() === 'ADMINISTRADOR'){ window.location.href = '/'; }
      if(this.obtenerRol() === 'DOCENTE'){ window.location.href = '/'; } 
    }
    //Caso: Denegar acceso a administradores y alumnos (Módulo docente)
    if(criterio === 'ADMINISTRADORyALUMNO'){
      if(this.obtenerRol() === 'ADMINISTRADOR'){ window.location.href = '/'; }
      if(this.obtenerRol() === 'ALUMNO'){ window.location.href = '/'; }
    }
    }
  
}