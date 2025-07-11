import { Routes } from '@angular/router';

export const appRoutes: Routes = [
  {
    path: '',
    redirectTo: 'login',
    pathMatch: 'full',
  },
  {
    path: 'login',
    loadComponent: () => import('./auth/login/login.component').then(m => m.LoginComponent),
  },
  {
    path: 'signup',
    loadComponent: () => import('./auth/signup/signup.component').then(m => m.SignupComponent),
  },
  {
    path: 'register',
    loadComponent: () => import('./components/student-registration/student-registration.component').then(m => m.StudentRegistrationComponent),
  },
  {
    path: 'select-course',
    loadComponent: () => import('./components/course-selection/course-selection.component').then(m => m.CourseSelectionComponent),
  },
  {
    path: 'confirmation',
    loadComponent: () => import('./components/confirmation/confirmation.component').then(m => m.ConfirmationComponent),
  },
  {
    path: '**',
    redirectTo: 'login'
  }
];
