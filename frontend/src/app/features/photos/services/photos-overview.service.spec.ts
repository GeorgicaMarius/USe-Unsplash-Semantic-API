import { TestBed } from '@angular/core/testing';

import { PhotosOverviewService } from './photos-overview.service';

describe('PhotosOverviewService', () => {
  let service: PhotosOverviewService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PhotosOverviewService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
