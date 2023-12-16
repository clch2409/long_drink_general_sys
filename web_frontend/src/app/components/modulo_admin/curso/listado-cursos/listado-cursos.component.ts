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
  cursos: Curso[] = [];
  columnas: string[] = ['codCurso','nombre','descripcion','mensualidad','duracion','turnos','acciones']
  constructor(private storageService: StorageService,private router: Router,private cursoService: CursoService) { }
  ngOnInit(): void {
      this.storageService.comprobarSesion();
      this.storageService.denegarAcceso('ALUMNOyDOCENTE');
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

}
