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

  advertisementCategories = ['CHAIR', 'TABLE', 'TV', 'CHEST_OF_DRAWERS', 'SOFA', 'BOOKSHELF', 'OTHER'];
  advertisementConditions = ['GOOD', 'FAIR', 'EXCELLENT'];  

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
  }

  /**
   * ✅ Carga el anuncio a modificar desde el backend
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

    this.http.get<any>(`/api/advertisements/${this.advertisementId}`, { headers }).subscribe({
      next: (data) => {
        console.log("📌 Datos del anuncio obtenidos:", data);
        this.adForm.patchValue({
          title: data.title,
          description: data.description,
          category: data.advertisementCategory.toUpperCase(),
          condition: data.advertisementCondition.toUpperCase(),
          price: data.price,
          photoUrls: data.photoUrls
        });
      },
      error: (error) => {
        console.error("❌ Error al obtener el anuncio:", error);
        this.errorMessage = "Error al cargar el anuncio. Inténtalo de nuevo.";
      }
    });
  }

  /**
   * ✅ Enviar la actualización del anuncio
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
  
      // ✅ Asegurar que `category` y `condition` sean en mayúsculas
      const requestBody = {
        id: this.advertisementId,
        title: this.adForm.value.title,
        description: this.adForm.value.description,
        category: this.adForm.value.category.toUpperCase(),
        condition: this.adForm.value.condition.toUpperCase(),
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
   * ✅ Cancela y redirige a la lista de anuncios del usuario
   */
  onCancel(): void {
    console.log("🔄 Redirigiendo a:", this.returnUrl);
    this.router.navigateByUrl(this.returnUrl);
  }
}
