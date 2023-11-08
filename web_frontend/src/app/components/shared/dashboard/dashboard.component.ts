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
  rol = '';
  constructor(private storageService: StorageService,private router: Router) { }
  ngOnInit(): void {
    this.storageService.comprobarSesion();
    this.rol = this.storageService.obtenerRol();
  }

}

