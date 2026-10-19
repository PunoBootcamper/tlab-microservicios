import { ChangeDetectionStrategy, Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { Header } from './core/layout/header/header';
import { CartPanel } from './features/cart/components/cart-panel/cart-panel';

@Component({
  imports: [RouterOutlet, Header, CartPanel],
  selector: 'app-root',
  changeDetection: ChangeDetectionStrategy.OnPush,
  template: `
    <app-header />
    <main class="container"><router-outlet /></main>
    <app-cart-panel />
  `,
  styles: `
    .container {
      max-width: 1200px;
      margin: 0 auto;
      padding: 1.5rem 1rem;
    }
  `,
})
export class App {}
