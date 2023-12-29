import { TestBed } from '@angular/core/testing';

import { IllustrationsService } from './illustrations.service';

describe('IllustrationsService', () => {
  let service: IllustrationsService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(IllustrationsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
