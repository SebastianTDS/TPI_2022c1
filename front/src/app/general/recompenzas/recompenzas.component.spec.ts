import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RecompenzasComponent } from './recompenzas.component';

describe('RecompenzasComponent', () => {
  let component: RecompenzasComponent;
  let fixture: ComponentFixture<RecompenzasComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RecompenzasComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RecompenzasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
