import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TipoLente } from './tipo-lente';

describe('TipoLente', () => {
  let component: TipoLente;
  let fixture: ComponentFixture<TipoLente>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TipoLente]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TipoLente);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
