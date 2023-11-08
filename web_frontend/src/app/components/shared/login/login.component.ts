import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';
import { AuthService } from 'src/app/services/auth.service';
import { StorageService } from 'src/app/services/storage.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  sesionIniciada = false;
  intentoFallido = false;
  mensajeError = '';
  rol = '';
  loginData = {
    nombreUsuario: '',
    contrasena: ''
  };

  constructor(private authService: AuthService, private storageService: StorageService,private router: Router) { }

  ngOnInit(): void {
      if(this.storageService.sesionIniciada()){
          this.sesionIniciada = true;
          this.rol = this.storageService.obtenerUsuario().rol;
          this.router.navigate(['/dashboard']); //Testear....!
      }
      if(this.sesionIniciada == true && this.rol === 'ADMINISTRADOR'){
        this.router.navigate(['/dashboard']);
      }
      if(this.sesionIniciada == true && this.rol === 'DOCENTE'){
        this.router.navigate(['/dashboard']);
        //this.intentoFallido = true;
        //this.mensajeError = 'Error! Su cuenta no tiene los permisos necesarios para iniciar sesión en esta área. Comuniquese con administración.'
        //this.mostrarMensaje(this.mensajeError);
        //this.storageService.limpiarCredenciales();
      }
      if(this.sesionIniciada == true && this.rol === 'ALUMNO'){
        this.router.navigate(['/dashboard']);
        //this.intentoFallido = true;
        //this.mensajeError = 'Error! Su cuenta no tiene los permisos necesarios para iniciar sesión en esta área. Usted debe iniciar sesión desde su App Movil "Long Drink".'
        //this.mostrarMensaje(this.mensajeError);
        //this.storageService.limpiarCredenciales();
      }
  }

  onSubmit() {
    this.authService.login(this.loginData.nombreUsuario,this.loginData.contrasena).subscribe({
      next : data =>{
        this.storageService.guardarUsuario(data);
        this.intentoFallido = false;
        this.sesionIniciada = true;
        this.rol = this.storageService.obtenerUsuario().rol;
        this.recargarPagina();
      },
      error: err =>{
        this.mensajeError = err.error.mensaje;
        this.intentoFallido = true;
        this.mostrarMensaje(this.mensajeError);
      }
    });
  }  
  limpiarCampos() {
    this.loginData.nombreUsuario = '';
    this.loginData.contrasena = '';
    document.getElementById('nombreUsuario')?.focus();
  }

  recargarPagina(): void{
    window.location.reload();
  }

  mostrarMensaje(mensaje: string): void{
    Swal.fire({
      icon: 'error',
      title: 'Ups! Intento de inicio de sesión fallido',
      text: mensaje,
      showConfirmButton: true,
      confirmButtonText: 'OK'
    }).then((result) => {
      if (result.isConfirmed) {
        this.limpiarCampos();
      }
    });
  }

  enProgreso(): void{
    Swal.fire('Ups!',"Trabajo en progreso",'info');
  }
  
  imagenesLogin = [
    "https://i.imgur.com/EjSEAEc.jpg",
    "https://i.imgur.com/AdyE4jy.jpg",
    "https://i.imgur.com/PkOSD0J.jpg",
    "https://i.imgur.com/ybFGJt8.jpg",
    "https://i.imgur.com/6xFoaQJ.jpg",
    "https://i.imgur.com/iHD2exV.jpg",
    "https://i.imgur.com/APbzr19.jpg",
    "https://i.imgur.com/NMsWNJy.jpg",
    "https://i.imgur.com/3TSkszZ.jpg",
    "https://i.imgur.com/E7p3wQB.jpg"
  ]

  enlaceImagen = this.imagenesLogin[Math.floor(Math.random() * this.imagenesLogin.length)];
  
}