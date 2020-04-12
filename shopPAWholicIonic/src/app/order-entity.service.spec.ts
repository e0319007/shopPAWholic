import { TestBed } from '@angular/core/testing';

import { OrderEntityService } from './order-entity.service';

describe('OrderEntityService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: OrderEntityService = TestBed.get(OrderEntityService);
    expect(service).toBeTruthy();
  });
});
