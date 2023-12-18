import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DetalleSeccionComponent } from './detalle-seccion.component';

describe('DetalleSeccionComponent', () => {
  let component: DetalleSeccionComponent;
  let fixture: ComponentFixture<DetalleSeccionComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DetalleSeccionComponent]
    });
    fixture = TestBed.createComponent(DetalleSeccionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
