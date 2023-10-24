import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CommentImgComponent } from './comment-img.component';

describe('CommentImgComponent', () => {
  let component: CommentImgComponent;
  let fixture: ComponentFixture<CommentImgComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CommentImgComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CommentImgComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
