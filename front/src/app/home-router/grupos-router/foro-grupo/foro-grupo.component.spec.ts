import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ForoGrupoComponent } from './foro-grupo.component';

describe('ForoGrupoComponent', () => {
  let component: ForoGrupoComponent;
  let fixture: ComponentFixture<ForoGrupoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ForoGrupoComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ForoGrupoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
