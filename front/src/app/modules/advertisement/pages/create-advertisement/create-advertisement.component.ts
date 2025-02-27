import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router, ActivatedRoute } from '@angular/router';
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
  returnUrl: string = '/advertisements'; 
  characterCount = 0; // Contador de caracteres

  // Mapeo de nombres en inglés a nombres en español
  categoryTranslations: { [key: string]: string } = {
    chair: 'Silla',
    table: 'Mesa',
    TV: 'Televisión',
    chestOfDrawers: 'Cómoda',
    sofa: 'Sofá',
    bookshelf: 'Estantería',
    other: 'Otro'
  };

  conditionTranslations: { [key: string]: string } = {
    Good: 'Buena',
    Fair: 'Regular',
    Excellent: 'Excelente'
  };

  // Listas de categorías y condiciones (usamos las claves del mapeo)
  advertisementCategories = Object.keys(this.categoryTranslations);
  advertisementConditions = Object.keys(this.conditionTranslations);

  constructor(
    private fb: FormBuilder, 
    private http: HttpClient, 
    private router: Router, 
    private route: ActivatedRoute 
  ) {
    this.adForm = this.fb.group({
      title: ['', Validators.required],
      description: ['', Validators.required],
      category: ['', Validators.required],
      condition: ['', Validators.required],
      price: [0, [Validators.required, Validators.min(0)]],
      photoUrls: [''],
    });

    this.route.queryParams.subscribe(params => {
      if (params['returnUrl']) {
        this.returnUrl = params['returnUrl']; 
        console.log("URL de retorno capturada:", this.returnUrl);
      }
    });
  }

  /**
   * 🔢 Actualizar el contador de caracteres
   */
  updateCharacterCount(): void {
    const description = this.adForm.get('description')?.value || '';
    this.characterCount = description.length;
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
          console.log("Redirigiendo a:", this.returnUrl);
          this.router.navigateByUrl(this.returnUrl); // Redirige a la URL de retorno
        },
        error: (error) => {
          console.error("Error en la respuesta del backend:", error);
          this.errorMessage = error.error?.message || 'No se pudo crear el anuncio.';
        },
      });
    }
  }

  onCancel(): void {
    console.log("Redirigiendo a:", this.returnUrl);
    this.router.navigateByUrl(this.returnUrl); // Redirige a la URL de retorno
  }
}