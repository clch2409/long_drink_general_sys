import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DetalleInscripcionComponent } from './detalle-inscripcion.component';

describe('DetalleInscripcionComponent', () => {
  let component: DetalleInscripcionComponent;
  let fixture: ComponentFixture<DetalleInscripcionComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DetalleInscripcionComponent]
    });
    fixture = TestBed.createComponent(DetalleInscripcionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
