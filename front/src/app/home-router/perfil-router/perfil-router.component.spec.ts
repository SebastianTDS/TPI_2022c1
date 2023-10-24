import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PerfilRouterComponent } from './perfil-router.component';

describe('PerfilRouterComponent', () => {
  let component: PerfilRouterComponent;
  let fixture: ComponentFixture<PerfilRouterComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PerfilRouterComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PerfilRouterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
