import { Injectable } from '@angular/core';

const USER_DATA = 'auth-user';

@Injectable({
  providedIn: 'root'
})
export class StorageService {

  constructor() { }

  limpiarCredenciales(): void{
    window.sessionStorage.clear();
  }

  public guardarUsuario(loginWeb: any): void{
    window.sessionStorage.removeItem(USER_DATA);
    window.sessionStorage.setItem(USER_DATA,JSON.stringify(loginWeb));
  }

  public obtenerUsuario(): any{
    const usuario = window.sessionStorage.getItem(USER_DATA);
    if(usuario){
      return JSON.parse(usuario);
    }
    return null;
  }

  public sesionIniciada(): boolean{
    const usuario = window.sessionStorage.getItem(USER_DATA);
    if(usuario){
      return true;
    }
    return false;
  }
}
