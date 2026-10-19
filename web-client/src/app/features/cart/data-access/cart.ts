import { computed, Service, signal } from '@angular/core';
import { Product } from '../../products/models/product.model';
import { CartItem } from '../models/cart-item.model';

@Service()
export class CartService {
  private readonly _items = signal<CartItem[]>([]);
  private readonly _open = signal(false);

  readonly items = this._items.asReadonly();
  readonly open = this._open.asReadonly();
  readonly count = computed(() => this._items().reduce((sum, i) => sum + i.quantity, 0));
  readonly total = computed(() =>
    this._items().reduce((sum, i) => sum + i.product.price * i.quantity, 0),
  );

  add(product: Product): void {
    this._items.update((items) => {
      const existing = items.find((i) => i.product.id === product.id);
      return existing
        ? items.map((i) => (i === existing ? { ...i, quantity: i.quantity + 1 } : i))
        : [...items, { product, quantity: 1 }];
    });
  }

  increase(productId: number): void {
    this._items.update((items) =>
      items.map((i) => (i.product.id === productId ? { ...i, quantity: i.quantity + 1 } : i)),
    );
  }

  decrease(productId: number): void {
    this._items.update((items) =>
      items
        .map((i) => (i.product.id === productId ? { ...i, quantity: i.quantity - 1 } : i))
        .filter((i) => i.quantity > 0),
    );
  }

  remove(productId: number): void {
    this._items.update((items) => items.filter((i) => i.product.id !== productId));
  }

  clear(): void {
    this._items.set([]);
  }

  toggle(): void {
    this._open.update((v) => !v);
  }

  close(): void {
    this._open.set(false);
  }
}
