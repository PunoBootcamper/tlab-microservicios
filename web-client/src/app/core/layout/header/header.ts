import { ChangeDetectionStrategy, Component } from '@angular/core';
import { CartIcon } from '../../../features/cart/components/cart-icon/cart-icon';

@Component({
  imports: [CartIcon],
  selector: 'app-header',
  changeDetection: ChangeDetectionStrategy.OnPush,
  templateUrl: './header.html',
  styleUrl: './header.scss',
})
export class Header {}
