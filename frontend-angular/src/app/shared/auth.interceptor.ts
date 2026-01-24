import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpErrorResponse } from '@angular/common/http';
import { Router } from '@angular/router';
import { catchError, throwError } from 'rxjs';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  constructor(private router: Router) {}

  intercept(req: HttpRequest<any>, next: HttpHandler) {
    const token = localStorage.getItem('token');
    const request = token
      ? req.clone({ setHeaders: { Authorization: `Bearer ${token}` } })
      : req;

    return next.handle(request).pipe(
      catchError((err: HttpErrorResponse) => {
        // If token is invalid/expired, send user back to login.
        if (err.status === 401 || err.status === 403) {
          localStorage.removeItem('token');
          this.router.navigateByUrl('/login');
        }
        return throwError(() => err);
      })
    );
  }
}
