import { TestBed } from '@angular/core/testing';

import { PinkshopFormService } from './pinkshop-form.service';

describe('PinkshopFormService', () => {
  let service: PinkshopFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PinkshopFormService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
