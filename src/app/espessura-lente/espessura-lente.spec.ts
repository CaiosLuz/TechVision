import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EspessuraLente } from './espessura-lente';

describe('EspessuraLente', () => {
  let component: EspessuraLente;
  let fixture: ComponentFixture<EspessuraLente>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EspessuraLente]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EspessuraLente);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
