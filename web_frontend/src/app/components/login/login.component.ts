import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  loginData = {
    nombreUsuario: '',
    contrasena: ''
  };
  respuestaDelServidor: any;

  constructor(private http: HttpClient, private router: Router) { }

  limpiarCampos() {
    this.loginData.nombreUsuario = '';
    this.loginData.contrasena = '';
    document.getElementById('nombreUsuario')?.focus();
  }

  onSubmit() {
    const url = 'http://localhost:8080/api/v1/auth/iniciar_sesion';
    this.http.post(url, this.loginData).subscribe(
      (response: any) => {
        this.respuestaDelServidor = response;
        console.log('Inicio de sesión exitoso', response);
  
        if (response.rol === 'ADMINISTRADOR') {
          Swal.fire({
            icon: 'success',
            title: '¡Bienvenido Administrador!',
            showConfirmButton: true,
            confirmButtonText: 'OK'
          }).then((result) => {
            if (result.isConfirmed) {
              this.router.navigate(['/dashboard']);
            }
          });
        } else {
          Swal.fire({
            icon: 'error',
            title: 'Lo siento',
            text: 'Usted no tiene acceso.',
            showConfirmButton: true,
            confirmButtonText: 'OK'
          }).then((result) => {
            if (result.isConfirmed) {
              this.limpiarCampos();
            }
          });
        }
      },
      (error) => {
        console.error('Error al iniciar sesión', error);
  
        Swal.fire({
          icon: 'error',
          title: 'Error',
          text: 'Credenciales incorrectas. Inténtalo de nuevo.',
          showConfirmButton: true,
          confirmButtonText: 'OK'
        }).then((result) => {
          if (result.isConfirmed) {
            this.limpiarCampos();
          }
        });
      }
    );
  }  
}