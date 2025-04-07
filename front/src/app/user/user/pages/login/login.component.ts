import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent {
  loginForm: FormGroup;
  errorMessage: string = '';

  constructor(
    private fb: FormBuilder,
    private http: HttpClient,
    private router: Router
  ) {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required]], 
    });
  }

  onSubmit(): void {
    if (this.loginForm.valid) {

      this.http.post('/auth/user/login', this.loginForm.value).subscribe({
        next: (response: any) => {

          if (!response || !response.token) {
            this.errorMessage = 'Error inesperado. Intenta de nuevo.';
            return;
          }

          sessionStorage.setItem('token', response.token);

          this.router.navigate(['/']);
        },
        error: (error) => {
          this.errorMessage = 'Email o contraseña incorrectos';
        },
      });
    } else {
      console.error('⛔ Formulario inválido:', this.loginForm.value);
    }
  }

  onCancel(): void {
    this.router.navigate(['/']); // Redirigir a la página principal inmediatamente
  }
}
