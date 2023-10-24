import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HeaderSinLogComponent } from './header-sin-log.component';

describe('HeaderSinLogComponent', () => {
  let component: HeaderSinLogComponent;
  let fixture: ComponentFixture<HeaderSinLogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ HeaderSinLogComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(HeaderSinLogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
