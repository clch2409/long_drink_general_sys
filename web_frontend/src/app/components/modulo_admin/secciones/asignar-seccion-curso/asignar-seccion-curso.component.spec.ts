import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AsignarSeccionCursoComponent } from './asignar-seccion-curso.component';

describe('AsignarSeccionCursoComponent', () => {
  let component: AsignarSeccionCursoComponent;
  let fixture: ComponentFixture<AsignarSeccionCursoComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AsignarSeccionCursoComponent]
    });
    fixture = TestBed.createComponent(AsignarSeccionCursoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
