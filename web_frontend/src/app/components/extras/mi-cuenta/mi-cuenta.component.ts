import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { StorageService } from 'src/app/services/storage.service';
import { AuthService } from 'src/app/services/auth.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-mi-cuenta',
  templateUrl: './mi-cuenta.component.html',
  styleUrls: ['./mi-cuenta.component.css']
})
export class MiCuentaComponent implements OnInit {
  sesionIniciada = false;
  rol = '';
  
  constructor(private storageService: StorageService, private router: Router, private authService: AuthService) { }
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

  cambiarCredenciales(): void {
    const contrasenaAntigua = (document.getElementById("contrasenaAntigua") as HTMLInputElement).value;
    const nuevaContrasena = (document.getElementById("nuevaContrasena") as HTMLInputElement).value;
    const nuevaContrasenaRepetir = (document.getElementById("nuevaContrasenaRepetir") as HTMLInputElement).value;
    const emailAntiguo = (document.getElementById("email") as HTMLInputElement).value;
    let emailNuevo = (document.getElementById("emailNuevo") as HTMLInputElement).value;
  
    if (nuevaContrasena !== nuevaContrasenaRepetir) {
      Swal.fire({
        icon: "error",
        title: "Error",
        text: "Las nuevas contraseñas ingresadas no coinciden."
      }).then((result) => {
        if (result.isConfirmed) {
          this.limpiarCampos();
        }
      });
      return;
    }

    if (!emailNuevo) {
      emailNuevo = emailAntiguo;
    }
  
    const datosCambio = {
      contrasenaAntigua,
      nuevaContrasena,
      emailAntiguo,
      emailNuevo
    };
  
    this.authService.cambiarCredenciales(datosCambio).subscribe(
      (response) => {
        Swal.fire({
          icon: "success",
          title: "Correcto",
          text: "Credenciales actualizadas correctamente.",
          allowOutsideClick: false
        }).then((result) => {
          if (result.isConfirmed) {
            this.storageService.cerrarSesion();
          }
        });
      },
      (error) => {
        Swal.fire({
          icon: "error",
          title: "Error",
          text: "Por favor ingrese los datos correctamente."
        }).then((result) => {
          if (result.isConfirmed) {
            this.limpiarCampos();
          }
        });        
      }
    );    
  }

  limpiarCampos(): void {
    const contrasenaAntiguaInput = document.getElementById("contrasenaAntigua") as HTMLInputElement;
    const nuevaContrasenaInput = document.getElementById("nuevaContrasena") as HTMLInputElement;
    const nuevaContrasenaRepetirInput = document.getElementById("nuevaContrasenaRepetir") as HTMLInputElement;
    const emailNuevoInput = document.getElementById("emailNuevo") as HTMLInputElement;
  
    contrasenaAntiguaInput.value = '';
    nuevaContrasenaInput.value = '';
    nuevaContrasenaRepetirInput.value = '';
    emailNuevoInput.value = '';
  }  

  comprobarSesion(): void {
    if (this.storageService.sesionIniciada()) {
      this.sesionIniciada = true;
      this.rol = this.storageService.obtenerUsuario().rol;
    }
    else {
      this.router.navigate(['/']);
    };
  }
}