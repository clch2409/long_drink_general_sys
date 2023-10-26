import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { StorageService } from 'src/app/services/storage.service';

@Component({
  selector: 'app-mi-cuenta',
  templateUrl: './mi-cuenta.component.html',
  styleUrls: ['./mi-cuenta.component.css']
})
export class MiCuentaComponent implements OnInit {
sesionIniciada = false;
rol = '';
constructor(private storageService: StorageService,private router: Router) { }
ngOnInit(): void {
    this.comprobarSesion();
    let username = document.getElementById("nombreUsuario") as HTMLInputElement;
    let nombre = document.getElementById("nombre") as HTMLInputElement;
    let email = document.getElementById("email") as HTMLInputElement;
    let rol = document.getElementById("rol") as HTMLInputElement;
    let usuario = this.storageService.obtenerUsuario();
    username.value = usuario.nombreUsuario;
    nombre.value = usuario.nombreCompleto;
    email.value = usuario.email;
    rol.value = usuario.rol;
}
comprobarSesion(): void{
  if(this.storageService.sesionIniciada()){
    this.sesionIniciada = true;
    this.rol = this.storageService.obtenerUsuario().rol;
}
else{
  this.router.navigate(['/']);
};
}
}
