import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ExportarDocentesComponent } from './exportar-docentes.component';

describe('ExportarDocentesComponent', () => {
  let component: ExportarDocentesComponent;
  let fixture: ComponentFixture<ExportarDocentesComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ExportarDocentesComponent]
    });
    fixture = TestBed.createComponent(ExportarDocentesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
