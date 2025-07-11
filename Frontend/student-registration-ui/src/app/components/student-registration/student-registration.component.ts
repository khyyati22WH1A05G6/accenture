import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-student-registration',
  standalone: true,
  imports: [CommonModule, FormsModule, HttpClientModule],
  templateUrl: './student-registration.component.html',
  styleUrls: ['./student-registration.component.css']
})
export class StudentRegistrationComponent {
  student = {
    firstName: '',
    lastName: '',
    email: '',
    phone: '',
    collegeName: ''
  };

  errorMessage: string = '';

  constructor(private http: HttpClient, private router: Router) {}

  registerStudent() {
    const apiUrl = 'http://localhost:8080/students';

    this.http.post<any>(apiUrl, this.student).subscribe({
      next: (res) => {
        const savedStudent = res.student;
        const studentId = savedStudent?.studentId;

        if (studentId) {
          sessionStorage.setItem('student', JSON.stringify(savedStudent));
          sessionStorage.setItem('studentId', studentId.toString());
          this.router.navigate(['/select-course']);
        } else {
          this.errorMessage = 'Registration succeeded but ID not returned.';
        }
      },
      error: (err) => {
        this.errorMessage = err?.error?.message || 'Failed to register. Check backend.';
        console.error(err);
      }
    });
  }
}
