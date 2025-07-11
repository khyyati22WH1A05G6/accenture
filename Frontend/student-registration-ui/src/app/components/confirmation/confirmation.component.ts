// src/app/components/confirmation/confirmation.component.ts
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { HttpClient, HttpClientModule } from '@angular/common/http';

@Component({
  selector: 'app-confirmation',
  standalone: true,
  imports: [CommonModule, HttpClientModule],
  templateUrl: './confirmation.component.html',
  styleUrls: ['./confirmation.component.css'] 
})
export class ConfirmationComponent implements OnInit {
  student: any;
  courseId: number = 0;
  courseName: string = '';
  registrationId: number = 0;
  registeredDate: string = '';
  courseStartDate: string = '';
  courseEndDate: string = '';
  allCourses: any[] = [];
  isConfirmed: boolean = false;

  constructor(private router: Router, private http: HttpClient) {}

  ngOnInit() {
    const studentData = sessionStorage.getItem('student');
    const courseIdStr = sessionStorage.getItem('courseId');
    const registrationIdStr = sessionStorage.getItem('registrationId');
    const storedCourses = sessionStorage.getItem('courses');

    if (studentData && courseIdStr && registrationIdStr) {
      this.student = JSON.parse(studentData);
      this.courseId = +courseIdStr;
      this.registrationId = +registrationIdStr;

      const today = new Date();
      this.registeredDate = this.toDateString(today);
      this.courseStartDate = this.toDateString(today);

      if (storedCourses) {
        this.allCourses = JSON.parse(storedCourses);
        const selectedCourse = this.allCourses.find(c => c.courseId === this.courseId);

        if (selectedCourse) {
          this.courseName = selectedCourse.courseName;
          const durationStr = selectedCourse.duration;
          this.courseEndDate = this.calculateEndDate(today, durationStr);
        }
      }
    } else {
      alert('Missing session data. Please register again.');
      this.router.navigate(['/register']);
    }
  }

  // Convert Date object to string in YYYY-MM-DD format
  toDateString(date: Date): string {
    return date.toISOString().split('T')[0];
  }

  // Calculate end date based on course duration
  calculateEndDate(startDate: Date, duration: string): string {
    const durationMatch = duration?.match(/([\d.]+)\s*month/i);
    if (durationMatch) {
      const months = parseFloat(durationMatch[1]);
      const endDate = new Date(startDate);
      const fullMonths = Math.floor(months);
      const extraDays = Math.round((months % 1) * 30);
      endDate.setMonth(endDate.getMonth() + fullMonths);
      endDate.setDate(endDate.getDate() + extraDays);
      return this.toDateString(endDate);
    }

    const fallback = new Date(startDate);
    fallback.setMonth(fallback.getMonth() + 3);
    return this.toDateString(fallback);
  }

  // Navigate back to edit student registration
  editDetails() {
    this.router.navigate(['/register']);
  }

  // Confirm registration and post course audit
  confirm() {
    const auditPayload = {
      registrationId: this.registrationId,
      studentId: this.student?.studentId,
      courseId: this.courseId,
      courseStartDate: this.courseStartDate,
      courseEndDate: this.courseEndDate
    };

    this.http.post('http://localhost:8080/course-audits', auditPayload).subscribe({
      next: () => {
        this.isConfirmed = true;
      },
      error: (err) => {
        console.error('Audit save failed:', err);
        alert('❌ Course audit failed to save. Please check backend.');
      }
    });
  }

  // Clear session and return to login
  signOut() {
    sessionStorage.clear();
    this.router.navigate(['/login']);
  }
}
