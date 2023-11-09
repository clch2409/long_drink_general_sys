import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { MatSidenav } from '@angular/material/sidenav';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import { StorageService } from 'src/app/services/storage.service';


@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  constructor(private storageService: StorageService,private router: Router) { }
  rol = ''
  ngOnInit(): void {
    this.storageService.comprobarSesion();
    this.rol = this.storageService.obtenerRol();
  }

  cerrarSesion(): void {
    this.storageService.cerrarSesion();
  }

}

