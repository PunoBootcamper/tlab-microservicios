import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ProductCard } from './product-card';

describe('ProductCard', () => {
  let fixture: ComponentFixture<ProductCard>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({ imports: [ProductCard] }).compileComponents();
    fixture = TestBed.createComponent(ProductCard);
    fixture.componentRef.setInput('product', {
      id: 1,
      name: 'Laptop',
      description: 'desc',
      price: 10,
      status: 'ACTIVE',
    });
    await fixture.whenStable();
  });

  it('renders name and placeholder image when there is no image', () => {
    const el: HTMLElement = fixture.nativeElement;
    expect(el.querySelector('h3')?.textContent).toContain('Laptop');
    expect(el.querySelector('img')?.getAttribute('src')).toContain('placeholder.svg');
  });

  it('emits add on click', () => {
    let emitted = false;
    fixture.componentInstance.add.subscribe(() => (emitted = true));
    fixture.nativeElement.querySelector('button').click();
    expect(emitted).toBe(true);
  });
});
