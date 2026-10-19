import { provideHttpClient } from '@angular/common/http';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';
import { environment } from '../../../../../environments/environment';
import { ProductList } from './product-list';

describe('ProductList', () => {
  it('renders a card per product', async () => {
    await TestBed.configureTestingModule({
      imports: [ProductList],
      providers: [provideHttpClient(), provideHttpClientTesting()],
    }).compileComponents();

    const fixture = TestBed.createComponent(ProductList);
    await fixture.whenStable();
    TestBed.inject(HttpTestingController)
      .expectOne(`${environment.apiUrl}/api/products`)
      .flush([
        { id: 1, name: 'A', description: '', price: 1, status: 'ACTIVE' },
        { id: 2, name: 'B', description: '', price: 2, status: 'ACTIVE' },
      ]);
    await fixture.whenStable();

    expect(fixture.nativeElement.querySelectorAll('app-product-card').length).toBe(2);
  });
});
