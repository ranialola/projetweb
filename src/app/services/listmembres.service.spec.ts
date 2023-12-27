import { TestBed } from '@angular/core/testing';

import { ListmembresService } from './listmembres.service';

describe('ListmembresService', () => {
  let service: ListmembresService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ListmembresService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
