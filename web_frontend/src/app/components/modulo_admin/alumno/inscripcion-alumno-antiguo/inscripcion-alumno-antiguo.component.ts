import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/services/auth.service';
import { CursoService } from 'src/app/services/curso.service';
import { StorageService } from 'src/app/services/storage.service';

@Component({
  selector: 'app-inscripcion-alumno-antiguo',
  templateUrl: './inscripcion-alumno-antiguo.component.html',
  styleUrls: ['./inscripcion-alumno-antiguo.component.css']
})
export class InscripcionAlumnoAntiguoComponent implements OnInit {
  constructor(
    private cursoService: CursoService,
    private authService: AuthService,
    private storageService: StorageService
  ) { }
  ngOnInit(): void {
    this.storageService.comprobarSesion();
    this.storageService.denegarAcceso("ALUMNOyDOCENTE");
  }
}
