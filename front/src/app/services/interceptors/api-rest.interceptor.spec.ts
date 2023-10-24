import { TestBed } from '@angular/core/testing';

import { ApiRestInterceptor } from './api-rest.interceptor';

describe('ApiRestInterceptor', () => {
  beforeEach(() => TestBed.configureTestingModule({
    providers: [
      ApiRestInterceptor
      ]
  }));

  it('should be created', () => {
    const interceptor: ApiRestInterceptor = TestBed.inject(ApiRestInterceptor);
    expect(interceptor).toBeTruthy();
  });
});
