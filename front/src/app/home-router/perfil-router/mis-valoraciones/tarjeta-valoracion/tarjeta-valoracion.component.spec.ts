import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TarjetaValoracionComponent } from './tarjeta-valoracion.component';

describe('TarjetaValoracionComponent', () => {
  let component: TarjetaValoracionComponent;
  let fixture: ComponentFixture<TarjetaValoracionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TarjetaValoracionComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TarjetaValoracionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
