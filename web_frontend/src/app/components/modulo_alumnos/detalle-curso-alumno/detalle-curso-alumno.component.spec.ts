import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DetalleCursoAlumnoComponent } from './detalle-curso-alumno.component';

describe('DetalleCursoAlumnoComponent', () => {
  let component: DetalleCursoAlumnoComponent;
  let fixture: ComponentFixture<DetalleCursoAlumnoComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DetalleCursoAlumnoComponent]
    });
    fixture = TestBed.createComponent(DetalleCursoAlumnoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
