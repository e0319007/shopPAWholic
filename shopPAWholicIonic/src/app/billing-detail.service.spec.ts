import { TestBed } from '@angular/core/testing';

import { BillingDetailService } from './billing-detail.service';

describe('BillingDetailService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: BillingDetailService = TestBed.get(BillingDetailService);
    expect(service).toBeTruthy();
  });
});
