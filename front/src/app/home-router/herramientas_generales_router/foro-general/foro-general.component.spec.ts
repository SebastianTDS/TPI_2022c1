import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ForoGeneralComponent } from './foro-general.component';

describe('ForoGeneralComponent', () => {
  let component: ForoGeneralComponent;
  let fixture: ComponentFixture<ForoGeneralComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ForoGeneralComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ForoGeneralComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
