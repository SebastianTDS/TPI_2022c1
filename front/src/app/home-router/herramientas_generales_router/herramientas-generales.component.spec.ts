import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HerramientasGeneralesComponent } from './herramientas-generales.component';

describe('HerramientasGeneralesComponent', () => {
  let component: HerramientasGeneralesComponent;
  let fixture: ComponentFixture<HerramientasGeneralesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ HerramientasGeneralesComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(HerramientasGeneralesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
