import { TestBed } from '@angular/core/testing';
import { Product } from '../../products/models/product.model';
import { CartService } from './cart';

const product = (id: number, price: number): Product => ({
  id,
  name: `P${id}`,
  description: '',
  price,
  status: 'ACTIVE',
});

describe('CartService', () => {
  let service: CartService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CartService);
  });

  it('adds products and groups repeated ones', () => {
    service.add(product(1, 10));
    service.add(product(1, 10));
    service.add(product(2, 5));

    expect(service.items().length).toBe(2);
    expect(service.count()).toBe(3);
    expect(service.total()).toBe(25);
  });

  it('removes an item when quantity reaches zero', () => {
    service.add(product(1, 10));
    service.decrease(1);

    expect(service.items()).toEqual([]);
  });

  it('removes and clears', () => {
    service.add(product(1, 10));
    service.add(product(2, 5));
    service.remove(1);
    expect(service.items().length).toBe(1);

    service.clear();
    expect(service.count()).toBe(0);
  });
});
