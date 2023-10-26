import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListadoProfesoresComponent } from './listado-profesores.component';

describe('ListadoProfesoresComponent', () => {
  let component: ListadoProfesoresComponent;
  let fixture: ComponentFixture<ListadoProfesoresComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ListadoProfesoresComponent]
    });
    fixture = TestBed.createComponent(ListadoProfesoresComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
