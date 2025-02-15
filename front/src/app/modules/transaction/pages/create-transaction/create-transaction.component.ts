import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router, ActivatedRoute } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-create-transaction',
  templateUrl: './create-transaction.component.html',
  styleUrls: ['./create-transaction.component.scss'],
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
})
export class CreateTransactionComponent implements OnInit {
  transactionForm: FormGroup;
  errorMessage: string = '';
  successMessage: string = ''; 
  advertisementId: string = '';
  advertisementPrice: number = 0; 
  returnUrl: string = '/advertisements';
  paymentMethods = ['Credit Card', 'PayPal', 'Bank Transfer'];

  constructor(
    private fb: FormBuilder,
    private http: HttpClient,
    private router: Router,
    private route: ActivatedRoute
  ) {
    this.transactionForm = this.fb.group({
      startDate: ['', Validators.required],
      endDate: ['', Validators.required],
      totalPrice: [0, Validators.required], 
      paymentMethod: ['', Validators.required],
      advertisementId: ['']
    });
  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      if (params['id']) {
        this.advertisementId = params['id'];
        this.transactionForm.patchValue({ advertisementId: this.advertisementId });
        this.loadAdvertisementPrice(); 
      }
    });

    this.route.queryParams.subscribe(params => {
      if (params['returnUrl']) {
        this.returnUrl = params['returnUrl'];
      }
    });

    this.transactionForm.get('startDate')?.valueChanges.subscribe(() => this.calculateTotalPrice());
    this.transactionForm.get('endDate')?.valueChanges.subscribe(() => this.calculateTotalPrice());
  }

  /**
   * ✅ Obtiene el precio del anuncio desde el backend
   */
  loadAdvertisementPrice(): void {
    const token = sessionStorage.getItem('token');

    if (!token) {
      this.errorMessage = 'Error de autenticación. Inicia sesión.';
      return;
    }

    const headers = new HttpHeaders({ 'Authorization': `Bearer ${token}` });

    this.http.get<any>(`/api/advertisements/${this.advertisementId}`, { headers }).subscribe({
      next: (data) => {
        console.log("📌 Precio del anuncio obtenido:", data.price);
        this.advertisementPrice = data.price;
        this.calculateTotalPrice(); 
      },
      error: (error) => {
        console.error("❌ Error al obtener el precio del anuncio:", error);
        this.errorMessage = "Error al obtener el precio del anuncio.";
      }
    });
  }

  /**
   * ✅ Calcula el precio proporcional basado en los días seleccionados
   */
  calculateTotalPrice(): void {
    const startDate = new Date(this.transactionForm.get('startDate')?.value);
    const endDate = new Date(this.transactionForm.get('endDate')?.value);

    if (!isNaN(startDate.getTime()) && !isNaN(endDate.getTime()) && startDate < endDate) {
      const days = Math.ceil((endDate.getTime() - startDate.getTime()) / (1000 * 60 * 60 * 24));
      const monthlyPrice = this.advertisementPrice;
      const pricePerDay = monthlyPrice / 30;
      const totalPrice = parseFloat((days * pricePerDay).toFixed(2)); 

      this.transactionForm.patchValue({ totalPrice });
    }
  }

  /**
   * ✅ Envía la transacción al backend
   */
  onSubmit(): void {
    if (this.transactionForm.valid) {
      const token = sessionStorage.getItem('token');

      if (!token) {
        this.errorMessage = 'Error de autenticación. Inicia sesión.';
        return;
      }

      const headers = new HttpHeaders({
        'Content-Type': 'application/json',
        Authorization: `Bearer ${token}`
      });

      const requestBody = {
        ...this.transactionForm.value,
        advertisementId: this.advertisementId
      };

      this.http.post('/api/transaction', requestBody, { headers }).subscribe({
        next: () => {
          console.log("✅ Transacción creada correctamente.");
          
          this.successMessage = "✅ La transacción se ha creado correctamente.";
          this.errorMessage = ''; 

          
          setTimeout(() => {
            this.router.navigateByUrl(this.returnUrl);
          }, 2000);
        },
        error: (error) => {
          console.error("❌ Error al crear la transacción:", error);
          this.successMessage = '';  
          this.errorMessage = error.error?.message || 'No se pudo crear la transacción.';
        },
      });
    } else {
      console.warn("⚠️ Formulario inválido, revisa los campos.");
    }
  }

  /**
   * ✅ Cancela y redirige a la página de anuncios
   */
  onCancel(): void {
    console.log("🔄 Redirigiendo a:", this.returnUrl);
    this.router.navigateByUrl(this.returnUrl);
  }
}
