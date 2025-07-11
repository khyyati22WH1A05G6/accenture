import { Component } from '@angular/core';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-signup',
  standalone: true,
  imports: [CommonModule, FormsModule, HttpClientModule],
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent {
  user = {
    email: '',
    password: ''
  };

  constructor(private http: HttpClient, private router: Router) {}

  signup() {
    if (!this.user.email || !this.user.password) {
      alert('Please fill out both email and password.');
      return;
    }

    this.http.post('http://localhost:8080/auth/signup', this.user).subscribe({
      next: () => {
        alert('Signup successful. Please login.');
        this.router.navigate(['/login']);
      },
      error: () => {
        alert('Signup failed. Please try again.');
      }
    });
  }

  goToLogin() {
    this.router.navigate(['/login']);
  }
}
