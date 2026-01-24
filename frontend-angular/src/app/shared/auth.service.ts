import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { API } from './api';
import { tap } from 'rxjs/operators';

@Injectable({ providedIn: 'root' })
export class AuthService {
  constructor(private http: HttpClient) {}

  signup(data: any) {
    return this.http.post<any>(API.auth.signup, data).pipe(
      tap(res => localStorage.setItem('token', res.token))
    );
  }

  login(data: any) {
    return this.http.post<any>(API.auth.login, data).pipe(
      tap(res => localStorage.setItem('token', res.token))
    );
  }

  logout() {
    localStorage.removeItem('token');
  }

  isLoggedIn() {
    return !!localStorage.getItem('token');
  }
}
