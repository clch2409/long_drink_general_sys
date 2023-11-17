import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InscripcionAlumnoAntiguoComponent } from './inscripcion-alumno-antiguo.component';

describe('InscripcionAlumnoAntiguoComponent', () => {
  let component: InscripcionAlumnoAntiguoComponent;
  let fixture: ComponentFixture<InscripcionAlumnoAntiguoComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [InscripcionAlumnoAntiguoComponent]
    });
    fixture = TestBed.createComponent(InscripcionAlumnoAntiguoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
