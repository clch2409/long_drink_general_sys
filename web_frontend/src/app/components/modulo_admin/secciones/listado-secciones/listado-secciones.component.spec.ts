import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListadoSeccionesComponent } from './listado-secciones.component';

describe('ListadoSeccionesComponent', () => {
  let component: ListadoSeccionesComponent;
  let fixture: ComponentFixture<ListadoSeccionesComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ListadoSeccionesComponent]
    });
    fixture = TestBed.createComponent(ListadoSeccionesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
