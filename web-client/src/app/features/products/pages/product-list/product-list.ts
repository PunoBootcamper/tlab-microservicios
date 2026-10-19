import { ChangeDetectionStrategy, Component, inject } from '@angular/core';
import { toSignal } from '@angular/core/rxjs-interop';
import { catchError, map, of, startWith } from 'rxjs';
import { CartService } from '../../../cart/data-access/cart';
import { ProductCard } from '../../components/product-card/product-card';
import { ProductService } from '../../data-access/product';
import { Product } from '../../models/product.model';

interface ProductsState {
  loading: boolean;
  error: boolean;
  products: Product[];
}

@Component({
  imports: [ProductCard],
  selector: 'app-product-list',
  changeDetection: ChangeDetectionStrategy.OnPush,
  templateUrl: './product-list.html',
  styleUrl: './product-list.scss',
})
export class ProductList {
  private readonly cart = inject(CartService);

  protected readonly state = toSignal(
    inject(ProductService)
      .getAll()
      .pipe(
        map((products): ProductsState => ({ loading: false, error: false, products })),
        catchError(() => of<ProductsState>({ loading: false, error: true, products: [] })),
        startWith<ProductsState>({ loading: true, error: false, products: [] }),
      ),
    { requireSync: true },
  );

  protected addToCart(product: Product): void {
    this.cart.add(product);
  }
}
