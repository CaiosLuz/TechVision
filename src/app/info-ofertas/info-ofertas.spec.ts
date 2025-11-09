import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InfoOfertas } from './info-ofertas';

describe('InfoOfertas', () => {
  let component: InfoOfertas;
  let fixture: ComponentFixture<InfoOfertas>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [InfoOfertas]
    })
    .compileComponents();

    fixture = TestBed.createComponent(InfoOfertas);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
