import { HttpClient, provideHttpClient, withInterceptors } from '@angular/common/http';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';
import { apiErrorInterceptor } from './api-error-interceptor';

describe('apiErrorInterceptor', () => {
  it('rethrows http errors', () => {
    TestBed.configureTestingModule({
      providers: [
        provideHttpClient(withInterceptors([apiErrorInterceptor])),
        provideHttpClientTesting(),
      ],
    });
    vi.spyOn(console, 'error').mockImplementation(() => undefined);
    const http = TestBed.inject(HttpClient);
    const ctrl = TestBed.inject(HttpTestingController);

    let status = 0;
    http.get('/x').subscribe({ error: (e) => (status = e.status) });
    ctrl.expectOne('/x').flush('err', { status: 500, statusText: 'Server Error' });

    expect(status).toBe(500);
  });
});
