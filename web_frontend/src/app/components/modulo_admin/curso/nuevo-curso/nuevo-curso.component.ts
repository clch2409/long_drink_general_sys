import { Component, OnInit } from '@angular/core';
import { StorageService } from 'src/app/services/storage.service';

@Component({
  selector: 'app-nuevo-curso',
  templateUrl: './nuevo-curso.component.html',
  styleUrls: ['./nuevo-curso.component.css']
})
export class NuevoCursoComponent implements OnInit {
  constructor(private storageService: StorageService){}
  
  ngOnInit(): void {
    this.storageService.denegarAcceso('ALUMNOyDOCENTE');
  }
}
