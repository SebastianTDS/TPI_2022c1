import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TarjetaNotificacionComponent } from './tarjeta-notificacion.component';

describe('TarjetaNotificacionComponent', () => {
  let component: TarjetaNotificacionComponent;
  let fixture: ComponentFixture<TarjetaNotificacionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TarjetaNotificacionComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TarjetaNotificacionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
