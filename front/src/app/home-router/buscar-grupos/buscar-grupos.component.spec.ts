import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BuscarGruposComponent } from './buscar-grupos.component';

describe('BuscarGruposComponent', () => {
  let component: BuscarGruposComponent;
  let fixture: ComponentFixture<BuscarGruposComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BuscarGruposComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BuscarGruposComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
