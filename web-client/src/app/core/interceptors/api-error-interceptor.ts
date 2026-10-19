import { HttpErrorResponse, HttpInterceptorFn } from '@angular/common/http';
import { catchError, throwError } from 'rxjs';

export const apiErrorInterceptor: HttpInterceptorFn = (req, next) =>
  next(req).pipe(
    catchError((error: HttpErrorResponse) => {
      console.error(`[API] ${req.method} ${req.urlWithParams} -> ${error.status}`, error.message);
      return throwError(() => error);
    }),
  );
