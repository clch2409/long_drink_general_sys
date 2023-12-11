import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MisPagosComponent } from './mis-pagos.component';

describe('MisPagosComponent', () => {
  let component: MisPagosComponent;
  let fixture: ComponentFixture<MisPagosComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [MisPagosComponent]
    });
    fixture = TestBed.createComponent(MisPagosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
