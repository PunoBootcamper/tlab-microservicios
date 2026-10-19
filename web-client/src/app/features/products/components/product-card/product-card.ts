import { CurrencyPipe } from '@angular/common';
import { ChangeDetectionStrategy, Component, input, output } from '@angular/core';
import { Product } from '../../models/product.model';

@Component({
  imports: [CurrencyPipe],
  selector: 'app-product-card',
  changeDetection: ChangeDetectionStrategy.OnPush,
  templateUrl: './product-card.html',
  styleUrl: './product-card.scss',
})
export class ProductCard {
  readonly product = input.required<Product>();
  readonly add = output<Product>();

  protected readonly placeholder = 'assets/placeholder.svg';
}
