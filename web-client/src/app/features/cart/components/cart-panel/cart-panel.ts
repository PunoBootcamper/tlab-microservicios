import { CurrencyPipe } from '@angular/common';
import { ChangeDetectionStrategy, Component, inject } from '@angular/core';
import { CartService } from '../../data-access/cart';

@Component({
  imports: [CurrencyPipe],
  selector: 'app-cart-panel',
  changeDetection: ChangeDetectionStrategy.OnPush,
  templateUrl: './cart-panel.html',
  styleUrl: './cart-panel.scss',
})
export class CartPanel {
  protected readonly cart = inject(CartService);
  protected readonly placeholder = 'assets/placeholder.svg';
}
