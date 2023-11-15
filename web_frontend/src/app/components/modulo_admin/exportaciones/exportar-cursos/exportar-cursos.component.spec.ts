import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ExportarCursosComponent } from './exportar-cursos.component';

describe('ExportarCursosComponent', () => {
  let component: ExportarCursosComponent;
  let fixture: ComponentFixture<ExportarCursosComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ExportarCursosComponent]
    });
    fixture = TestBed.createComponent(ExportarCursosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
