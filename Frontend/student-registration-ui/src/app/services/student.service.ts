import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Student {
  firstName: string;
  lastName: string;
  email: string;
  phone: string;
  collegeName: string;
}

@Injectable({
  providedIn: 'root'
})
export class StudentService {
  private apiUrl = 'http://localhost:8080/students'; // 🔁 Update with your backend endpoint

  constructor(private http: HttpClient) {}

  registerStudent(student: Student): Observable<any> {
    return this.http.post(this.apiUrl, student);
  }
}
