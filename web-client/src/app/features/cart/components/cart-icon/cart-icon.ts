import { ChangeDetectionStrategy, Component, inject } from '@angular/core';
import { CartService } from '../../data-access/cart';

@Component({
  selector: 'app-cart-icon',
  changeDetection: ChangeDetectionStrategy.OnPush,
  templateUrl: './cart-icon.html',
  styleUrl: './cart-icon.scss',
})
export class CartIcon {
  protected readonly cart = inject(CartService);
}
