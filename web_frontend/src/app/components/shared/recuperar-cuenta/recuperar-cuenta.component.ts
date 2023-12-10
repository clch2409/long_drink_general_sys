import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import { StorageService } from 'src/app/services/storage.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-recuperar-cuenta',
  templateUrl: './recuperar-cuenta.component.html',
  styleUrls: ['./recuperar-cuenta.component.css']
})
export class RecuperarCuentaComponent implements OnInit {
  sesionIniciada = false;
  rol = '';
  email = '';

  constructor(private authService: AuthService, private storageService: StorageService,private router: Router) {}

  ngOnInit(): void {
    if(this.storageService.sesionIniciada()){
      this.sesionIniciada = true;
      this.rol = this.storageService.obtenerUsuario().rol;
      this.router.navigate(['/dashboard']);
  }
  if(this.sesionIniciada == true && this.rol === 'ADMINISTRADOR'){
    this.router.navigate(['/dashboard']);
  }
  if(this.sesionIniciada == true && this.rol === 'DOCENTE'){
    this.router.navigate(['/p']);
  }
  if(this.sesionIniciada == true && this.rol === 'ALUMNO'){
    this.router.navigate(['/a']);
  }
  }

  onSubmit(){
    if(this.email.length <= 0){
      Swal.fire('Ups!','Debe llenar el campo e-mail para recuperar su cuenta.','info');
    }
    if(!this.validateEmail(this.email)){
      Swal.fire('Ups!','El e-mail debe cumplir con el formato nombre@dominio.com','info');
    }
    else{
      this.authService.recuperarCuenta(this.email).subscribe({
        next: data =>{
          this.mostrarMensaje();
        },
        error: err =>{
          this.mostrarMensaje();
        }
      })
    }
  }

  validateEmail = (correo: string) => {
    return String(correo)
      .toLowerCase()
      .match(
        /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|.(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
      );
  };

  mostrarMensaje(){
    Swal.fire('Exito!','En caso de que el e-mail ingresado este asociado a una cuenta las instrucciones para recuperarla serán enviadas.','success');
  }

}
