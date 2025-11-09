import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AnexarReceita } from './anexar-receita';

describe('AnexarReceita', () => {
  let component: AnexarReceita;
  let fixture: ComponentFixture<AnexarReceita>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AnexarReceita]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AnexarReceita);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
