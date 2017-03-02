/* tslint:disable:no-unused-variable */

import { TestBed, async, inject } from '@angular/core/testing';
import { AptitudeService } from './aptitude.service';

describe('AptitudeService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [AptitudeService]
    });
  });

  xit('should ...', inject([AptitudeService], (service: AptitudeService) => {
    expect(service).toBeTruthy();
  }));
});
