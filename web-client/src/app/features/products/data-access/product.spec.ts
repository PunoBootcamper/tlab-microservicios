import { provideHttpClient } from '@angular/common/http';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';
import { environment } from '../../../../environments/environment';
import { ProductService } from './product';

describe('ProductService', () => {
  let service: ProductService;
  let http: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    service = TestBed.inject(ProductService);
    http = TestBed.inject(HttpTestingController);
  });

  it('should fetch products from the gateway', () => {
    let result: unknown;
    service.getAll().subscribe((p) => (result = p));

    const req = http.expectOne(`${environment.apiUrl}/api/products`);
    expect(req.request.method).toBe('GET');
    req.flush([{ id: 1 }]);

    expect(result).toEqual([{ id: 1 }]);
  });
});
