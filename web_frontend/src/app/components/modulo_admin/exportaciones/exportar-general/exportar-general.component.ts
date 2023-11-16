import { Component, OnInit } from '@angular/core';
import { StorageService } from 'src/app/services/storage.service';

@Component({
  selector: 'app-exportar-general',
  templateUrl: './exportar-general.component.html',
  styleUrls: ['./exportar-general.component.css']
})
export class ExportarGeneralComponent implements OnInit {
  constructor(private storageService: StorageService) {}
  ngOnInit(): void {
    this.storageService.comprobarSesion();
    this.storageService.denegarAcceso("ALUMNOyDOCENTE");
  }
}
