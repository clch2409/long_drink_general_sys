import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Curso } from 'src/app/models/curso.model';
import { CursoService } from 'src/app/services/curso.service';
import { StorageService } from 'src/app/services/storage.service';

@Component({
  selector: 'app-listado-cursos',
  templateUrl: './listado-cursos.component.html',
  styleUrls: ['./listado-cursos.component.css']
})
export class ListadoCursosComponent implements OnInit {
  sesionIniciada = false;
  rol = '';
  cursos: Curso[] = [];
  constructor(private storageService: StorageService,private router: Router,private cursoService: CursoService) { }
  ngOnInit(): void {
      this.comprobarSesion();
      this.obtenerCursos();
  }

  obtenerCursos(): void{
    this.cursoService.getCursos().subscribe({
      next: (data) =>{
        this.cursos = data;
        console.log(this.cursos);
      },
      error: (e) => console.log(e)
    });
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