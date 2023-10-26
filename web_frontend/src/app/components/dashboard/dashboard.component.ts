import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import { StorageService } from 'src/app/services/storage.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  sesionIniciada = false;
  rol = '';
  constructor(private storageService: StorageService,private router: Router) { }
  ngOnInit(): void {
    this.comprobarSesion();
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
