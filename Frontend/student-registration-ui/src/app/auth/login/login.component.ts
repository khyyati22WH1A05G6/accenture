import { Component } from '@angular/core';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule, HttpClientModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  email = '';
  password = '';

  constructor(private http: HttpClient, private router: Router) {}

  login() {
    if (!this.email || !this.password) {
      alert('Please enter both email and password.');
      return;
    }

    this.http.post<any>('http://localhost:8080/auth/login', {
      email: this.email,
      password: this.password
    }).subscribe({
      next: (res) => {
        sessionStorage.setItem('user', JSON.stringify(res.user));
        alert('Login successful');
        this.router.navigate(['/register']);
      },
      error: (err) => {
        if (err.status === 401) {
          alert('User not found. Redirecting to signup...');
          this.router.navigate(['/signup']);
        } else {
          alert('Login failed. Please try again.');
        }
      }
    });
  }

  goToSignup() {
    this.router.navigate(['/signup']);
  }
}
