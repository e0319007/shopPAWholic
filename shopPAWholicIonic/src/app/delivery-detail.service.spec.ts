import { TestBed } from '@angular/core/testing';

import { DeliveryDetailService } from './delivery-detail.service';

describe('DeliveryDetailService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: DeliveryDetailService = TestBed.get(DeliveryDetailService);
    expect(service).toBeTruthy();
  });
});
