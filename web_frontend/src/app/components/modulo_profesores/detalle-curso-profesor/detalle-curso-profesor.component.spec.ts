import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DetalleCursoProfesorComponent } from './detalle-curso-profesor.component';

describe('DetalleCursoProfesorComponent', () => {
  let component: DetalleCursoProfesorComponent;
  let fixture: ComponentFixture<DetalleCursoProfesorComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DetalleCursoProfesorComponent]
    });
    fixture = TestBed.createComponent(DetalleCursoProfesorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
