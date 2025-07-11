import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-course-selection',
  standalone: true,
  imports: [CommonModule, FormsModule, HttpClientModule],
  templateUrl: './course-selection.component.html',
  styleUrls: ['./course-selection.component.css']
})
export class CourseSelectionComponent implements OnInit {
  courses: any[] = [];
  selectedCourseId: number = 0;
  student: any;

  constructor(private http: HttpClient, private router: Router) {}

  ngOnInit() {
    this.fetchCourses();

    const stored = sessionStorage.getItem('student');
    if (stored) {
      this.student = JSON.parse(stored);
    } else {
      alert('Student not found in session. Please register again.');
      this.router.navigate(['/register']);
    }
  }

  fetchCourses() {
    this.http.get<any[]>('http://localhost:8080/courses').subscribe({
      next: (data) => {
        this.courses = data;
      },
      error: (err) => {
        console.error('Error fetching courses', err);
        alert('Failed to load courses. Try again later.');
      }
    });
  }

  submitCourse() {
    if (!this.selectedCourseId) {
      alert('Please select a course before submitting.');
      return;
    }

    const registrationData = {
      studentId: this.student.studentId,
      courseId: this.selectedCourseId
    };

    this.http.post<any>('http://localhost:8080/registrations', registrationData).subscribe({
      next: (res) => {
        const registrationId =
          res.registrationId ?? res.registration?.registrationId ?? res.id;

        if (registrationId) {
          sessionStorage.setItem('registrationId', registrationId.toString());
          sessionStorage.setItem('courseId', String(this.selectedCourseId));
          sessionStorage.setItem('courses', JSON.stringify(this.courses));
          sessionStorage.setItem('student', JSON.stringify(this.student));
          alert('Course selected successfully!');
          this.router.navigate(['/confirmation']);
        } else {
          alert('Registration created but ID could not be found.');
        }
      },
      error: (err) => {
        console.error('❌ Backend error:', err);
        alert('Failed to register course. Check backend.');
      }
    });
  }
}
