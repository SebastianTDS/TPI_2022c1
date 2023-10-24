import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TarjetaGrupoComponent } from './tarjeta-grupo.component';

describe('TarjetaGrupoComponent', () => {
  let component: TarjetaGrupoComponent;
  let fixture: ComponentFixture<TarjetaGrupoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TarjetaGrupoComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TarjetaGrupoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
