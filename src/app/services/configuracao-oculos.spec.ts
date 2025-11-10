import { TestBed } from '@angular/core/testing';

import { CofiguracaoOculos } from './configuracao-oculos';

describe('CofiguracaoOculos', () => {
  let service: CofiguracaoOculos;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CofiguracaoOculos);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
