import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BuscarIntegrantesComponent } from './buscar-integrantes.component';

describe('BuscarIntegrantesComponent', () => {
  let component: BuscarIntegrantesComponent;
  let fixture: ComponentFixture<BuscarIntegrantesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BuscarIntegrantesComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BuscarIntegrantesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
