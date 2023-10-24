import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TarjetaMiGrupoComponent } from './tarjeta-mi-grupo.component';

describe('TarjetaMiGrupoComponent', () => {
  let component: TarjetaMiGrupoComponent;
  let fixture: ComponentFixture<TarjetaMiGrupoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TarjetaMiGrupoComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TarjetaMiGrupoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
