import { TestBed } from '@angular/core/testing';

import { ImageConfigService } from './image-config.service';

describe('ImageConfigService', () => {
  let service: ImageConfigService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ImageConfigService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
