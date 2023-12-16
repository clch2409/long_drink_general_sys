import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NuevoPagoAlumnoComponent } from './nuevo-pago-alumno.component';

describe('NuevoPagoAlumnoComponent', () => {
  let component: NuevoPagoAlumnoComponent;
  let fixture: ComponentFixture<NuevoPagoAlumnoComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [NuevoPagoAlumnoComponent]
    });
    fixture = TestBed.createComponent(NuevoPagoAlumnoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
