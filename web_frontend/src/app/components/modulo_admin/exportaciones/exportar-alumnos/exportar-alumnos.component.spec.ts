import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ExportarAlumnosComponent } from './exportar-alumnos.component';

describe('ExportarAlumnosComponent', () => {
  let component: ExportarAlumnosComponent;
  let fixture: ComponentFixture<ExportarAlumnosComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ExportarAlumnosComponent]
    });
    fixture = TestBed.createComponent(ExportarAlumnosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
