import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MisCursosProfesorComponent } from './mis-cursos-profesor.component';

describe('MisCursosProfesorComponent', () => {
  let component: MisCursosProfesorComponent;
  let fixture: ComponentFixture<MisCursosProfesorComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [MisCursosProfesorComponent]
    });
    fixture = TestBed.createComponent(MisCursosProfesorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
