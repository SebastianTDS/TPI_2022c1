import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StudentImgComponent } from './student-img.component';

describe('StudentImgComponent', () => {
  let component: StudentImgComponent;
  let fixture: ComponentFixture<StudentImgComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ StudentImgComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(StudentImgComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
