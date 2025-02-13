import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-create-advertisement',
  templateUrl: './create-advertisement.component.html',
  styleUrls: ['./create-advertisement.component.scss'],
  standalone: true, 
  imports: [CommonModule, ReactiveFormsModule],
})
export class CreateAdvertisementComponent {
  adForm: FormGroup;
  errorMessage: string = '';

  advertisementCategories = ['chair', 'table', 'TV', 'chestOfDrawers', 'sofa', 'bookshelf', 'other'];
  advertisementConditions = ['Good', 'Fair', 'Excellent'];  

  constructor(private fb: FormBuilder, private http: HttpClient, private router: Router) {
    this.adForm = this.fb.group({
      title: ['', Validators.required],
      description: ['', Validators.required],
      category: ['', Validators.required],
      condition: ['', Validators.required],
      price: [0, [Validators.required, Validators.min(0)]],
      photoUrls: [''],
    });
  }

  onSubmit(): void {
    if (this.adForm.valid) {
      console.log("Formulario enviado al backend:", this.adForm.value);
  
      const token = sessionStorage.getItem('token');
  
      if (!token) {
        this.errorMessage = 'Error de autenticación. Inicia sesión.';
        return;
      }
  
      const headers = new HttpHeaders({
        'Content-Type': 'application/json',
        Authorization: `Bearer ${token}`,
      });
  
      this.http.post('/api/advertisements', this.adForm.value, { headers }).subscribe({
        next: () => {
          this.router.navigate(['/advertisements']);
        },
        error: (error) => {
          console.error("Error en la respuesta del backend:", error);
          this.errorMessage = error.error?.message || 'No se pudo crear el anuncio.';
        },
      });
    }
  }

  onCancel(): void {
    this.router.navigate(['/advertisements']);
  }
}
