import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SetGuiasEstudioComponent } from './set-guias-estudio.component';

describe('SetGuiasEstudioComponent', () => {
  let component: SetGuiasEstudioComponent;
  let fixture: ComponentFixture<SetGuiasEstudioComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SetGuiasEstudioComponent]
    });
    fixture = TestBed.createComponent(SetGuiasEstudioComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
