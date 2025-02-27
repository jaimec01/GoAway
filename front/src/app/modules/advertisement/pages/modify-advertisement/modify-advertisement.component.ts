import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router, ActivatedRoute } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-modify-advertisement',
  templateUrl: './modify-advertisement.component.html',
  styleUrls: ['./modify-advertisement.component.scss'],
  standalone: true, 
  imports: [CommonModule, ReactiveFormsModule],
})
export class ModifyAdvertisementComponent implements OnInit {
  adForm: FormGroup;
  errorMessage: string = '';
  successMessage: string = '';  
  advertisementId: string = '';
  returnUrl: string = '/advertisements/my-ads';  
  characterCount: number = 0; // Contador de caracteres

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

  // Listas de categorías y condiciones
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
  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      if (params['id']) {
        this.advertisementId = params['id'];
        this.loadAdvertisement();
      }
    });

    this.route.queryParams.subscribe(params => {
      if (params['returnUrl']) {
        this.returnUrl = params['returnUrl'];
      }
    });

    // Escuchar cambios en la descripción para actualizar el contador
    this.adForm.get('description')?.valueChanges.subscribe(() => this.updateCharacterCount());
  }

  /**
   * 🔢 Actualizar el contador de caracteres
   */
  updateCharacterCount(): void {
    const description = this.adForm.get('description')?.value || '';
    this.characterCount = description.length;
  }

  /**
   * ✅ Cargar los datos del anuncio
   */
  loadAdvertisement(): void {
    const token = sessionStorage.getItem('token');

    if (!token) {
      this.errorMessage = 'Error de autenticación. Inicia sesión.';
      return;
    }

    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });

    this.http.get<any>(`/public/advertisements/${this.advertisementId}`, { headers }).subscribe({
      next: (data) => {
        console.log("📌 Datos del anuncio obtenidos:", data);

        this.adForm.patchValue({
          title: data.title,
          description: data.description,
          category: data.advertisementCategory, 
          condition: data.advertisementCondition,  
          price: data.price,
          photoUrls: data.photoUrls
        });

        // Actualizar el contador de caracteres al cargar el anuncio
        this.updateCharacterCount();
      },
      error: (error) => {
        console.error("❌ Error al obtener el anuncio:", error);
        this.errorMessage = "Error al cargar el anuncio. Inténtalo de nuevo.";
      }
    });
  }

  /**
   * ✅ Enviar el formulario
   */
  onSubmit(): void {
    if (this.adForm.valid) {
      console.log("📤 Enviando datos al backend:", this.adForm.value);
  
      const token = sessionStorage.getItem('token');
  
      if (!token) {
        this.errorMessage = 'Error de autenticación. Inicia sesión.';
        return;
      }
  
      const headers = new HttpHeaders({
        'Content-Type': 'application/json',
        Authorization: `Bearer ${token}`,
      });
  
      const requestBody = {
        id: this.advertisementId,
        title: this.adForm.value.title,
        description: this.adForm.value.description,
        category: this.adForm.value.category,  
        condition: this.adForm.value.condition,  
        price: this.adForm.value.price,
        photoUrls: this.adForm.value.photoUrls
      };
  
      this.http.put(`/api/advertisements/${this.advertisementId}`, requestBody, { headers }).subscribe({
        next: () => {
          console.log("✅ Anuncio modificado correctamente.");
          
          this.successMessage = "✅ El anuncio se ha modificado correctamente.";
          this.errorMessage = '';
  
          setTimeout(() => {
            this.router.navigateByUrl(this.returnUrl);
          }, 2000);
        },
        error: (error) => {
          console.error("❌ Error en la respuesta del backend:", error);
          this.successMessage = '';  
          this.errorMessage = error.error?.message || 'No se pudo modificar el anuncio.';
        },
      });
    } else {
      console.warn("⚠️ Formulario inválido, revisa los campos.");
    }
  }

  /**
   * ✅ Cancelar y redirigir
   */
  onCancel(): void {
    console.log("🔄 Redirigiendo a:", this.returnUrl);
    this.router.navigateByUrl(this.returnUrl);
  }
}