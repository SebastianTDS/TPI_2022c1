import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ImgStudentNotificationComponent } from './img-student-notification.component';

describe('ImgStudentNotificationComponent', () => {
  let component: ImgStudentNotificationComponent;
  let fixture: ComponentFixture<ImgStudentNotificationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ImgStudentNotificationComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ImgStudentNotificationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
