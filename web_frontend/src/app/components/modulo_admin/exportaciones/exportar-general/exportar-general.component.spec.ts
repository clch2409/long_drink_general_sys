import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ExportarGeneralComponent } from './exportar-general.component';

describe('ExportarGeneralComponent', () => {
  let component: ExportarGeneralComponent;
  let fixture: ComponentFixture<ExportarGeneralComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ExportarGeneralComponent]
    });
    fixture = TestBed.createComponent(ExportarGeneralComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
