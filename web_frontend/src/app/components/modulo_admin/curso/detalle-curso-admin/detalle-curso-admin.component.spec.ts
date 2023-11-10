import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DetalleCursoAdminComponent } from './detalle-curso-admin.component';

describe('DetalleCursoAdminComponent', () => {
  let component: DetalleCursoAdminComponent;
  let fixture: ComponentFixture<DetalleCursoAdminComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DetalleCursoAdminComponent]
    });
    fixture = TestBed.createComponent(DetalleCursoAdminComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
