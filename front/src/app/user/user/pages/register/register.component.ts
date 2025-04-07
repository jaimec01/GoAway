import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss'],
})
export class RegisterComponent {
  registerForm: FormGroup;
  errorMessage: string = '';

  constructor(
    private fb: FormBuilder,
    private http: HttpClient,
    private router: Router
  ) {
    this.registerForm = this.fb.group({
      name: ['', [Validators.required]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required]],
      contactNumber: ['']
    });
  }

  onSubmit(): void {
    if (this.registerForm.valid) {
      console.log('📤 Enviando datos al backend:', this.registerForm.value);

      this.http.post('/auth/user/register', this.registerForm.value).subscribe({
        next: (response: any) => {
          console.log('✅ Registro exitoso, respuesta completa:', response);

          // Verificar qué campos están disponibles
          if (!response.token) {
            console.error('⚠️ No se encontró "token" en la respuesta. Campos disponibles:', Object.keys(response));
            this.errorMessage = 'Registro exitoso, pero no se recibió token. Inicia sesión manualmente.';
            this.router.navigate(['/login']);
            return;
          }

          sessionStorage.setItem('token', response.token);
          console.log('🟢 Token guardado:', sessionStorage.getItem('token'));
          console.log('🟢 Registro completado. Redirigiendo a la pantalla principal...');
          this.router.navigate(['/']);
        },
        error: (error) => {
          console.error('❌ Error en el registro:', error);
          this.errorMessage = 'Error al registrar. Intenta con otro email.';
        },
      });
    }
  }

  onCancel(): void {
    this.router.navigate(['/']);
  }
}