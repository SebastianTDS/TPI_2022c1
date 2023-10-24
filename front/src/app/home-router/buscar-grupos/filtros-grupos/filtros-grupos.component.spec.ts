import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FiltrosGruposComponent } from './filtros-grupos.component';

describe('FiltrosGruposComponent', () => {
  let component: FiltrosGruposComponent;
  let fixture: ComponentFixture<FiltrosGruposComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FiltrosGruposComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FiltrosGruposComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
