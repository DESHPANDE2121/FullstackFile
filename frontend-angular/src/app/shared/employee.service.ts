import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { API } from './api';
import { map } from 'rxjs/operators';

export interface Employee {
  // Spring Boot returns "id"; some Mongo/Mongoose APIs return "_id".
  // Support both so update/delete always work.
  id?: string;
  _id?: string;
  fullName: string;
  email: string;
  department: string;
  salary: number;
}

@Injectable({ providedIn: 'root' })
export class EmployeeService {
  constructor(private http: HttpClient) {}

  private normalize(e: any): Employee {
    if (e && !e.id && e._id) e.id = e._id;
    return e as Employee;
  }

  private toPayload(e: Employee): any {
    const { id, _id, ...rest } = (e || {}) as any;
    return rest;
  }

  list() {
    return this.http.get<any[]>(API.employees).pipe(
      map((arr) => (arr || []).map((e) => this.normalize(e)))
    );
  }

  create(e: Employee) {
    return this.http.post<any>(API.employees, this.toPayload(e)).pipe(map((res) => this.normalize(res)));
  }

  update(id: string, e: Employee) {
    return this.http
      .put<any>(`${API.employees}/${id}`, this.toPayload(e))
      .pipe(map((res) => this.normalize(res)));
  }

  delete(id: string) {
    // Some backends return an empty body for DELETE which can cause JSON parse errors.
    return this.http.delete(`${API.employees}/${id}`, { responseType: 'text' });
  }
}
