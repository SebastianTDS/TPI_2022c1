import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GruposRouterComponent } from './grupos-router.component';

describe('GruposRouterComponent', () => {
  let component: GruposRouterComponent;
  let fixture: ComponentFixture<GruposRouterComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GruposRouterComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(GruposRouterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
