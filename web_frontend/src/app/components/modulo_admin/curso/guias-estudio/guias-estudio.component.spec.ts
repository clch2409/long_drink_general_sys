import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GuiasEstudioComponent } from './guias-estudio.component';

describe('GuiasEstudioComponent', () => {
  let component: GuiasEstudioComponent;
  let fixture: ComponentFixture<GuiasEstudioComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [GuiasEstudioComponent]
    });
    fixture = TestBed.createComponent(GuiasEstudioComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
