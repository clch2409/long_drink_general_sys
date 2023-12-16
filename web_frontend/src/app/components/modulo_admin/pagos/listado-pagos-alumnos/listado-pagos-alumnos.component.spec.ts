import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListadoPagosAlumnosComponent } from './listado-pagos-alumnos.component';

describe('ListadoPagosAlumnosComponent', () => {
  let component: ListadoPagosAlumnosComponent;
  let fixture: ComponentFixture<ListadoPagosAlumnosComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ListadoPagosAlumnosComponent]
    });
    fixture = TestBed.createComponent(ListadoPagosAlumnosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
