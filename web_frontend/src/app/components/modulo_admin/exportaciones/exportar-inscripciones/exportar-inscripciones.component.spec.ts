import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ExportarInscripcionesComponent } from './exportar-inscripciones.component';

describe('ExportarInscripcionesComponent', () => {
  let component: ExportarInscripcionesComponent;
  let fixture: ComponentFixture<ExportarInscripcionesComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ExportarInscripcionesComponent]
    });
    fixture = TestBed.createComponent(ExportarInscripcionesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
