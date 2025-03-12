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
  dateError: boolean = false; // Para controlar el error de fechas

  constructor(
    private fb: FormBuilder,
    private http: HttpClient,
    private router: Router,
    private route: ActivatedRoute
  ) {
    this.transactionForm = this.fb.group({
      startDate: ['', Validators.required],
      endDate: ['', Validators.required],
      totalPrice: [0, [Validators.required, Validators.min(0)]], 
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

    // Escuchar cambios en las fechas para recalcular el precio
    this.transactionForm.get('startDate')?.valueChanges.subscribe(() => {
      this.validateDates();
      this.calculateTotalPrice();
    });

    this.transactionForm.get('endDate')?.valueChanges.subscribe(() => {
      this.validateDates();
      this.calculateTotalPrice();
    });
  }

  /**
   * ✅ Valida que la fecha de fin no sea anterior a la fecha de inicio
   */
  validateDates(): void {
    const startDate = new Date(this.transactionForm.get('startDate')?.value);
    const endDate = new Date(this.transactionForm.get('endDate')?.value);

    if (startDate && endDate && endDate < startDate) {
      this.dateError = true;
    } else {
      this.dateError = false;
    }
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

    this.http.get<any>(`/public/advertisements/${this.advertisementId}`, { headers }).subscribe({
      next: (data) => {
        this.advertisementPrice = data.price;
        this.calculateTotalPrice(); // Calcular el precio inicial
      },
      error: (error) => {
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

      // Establecer el precio calculado como valor inicial
      this.transactionForm.patchValue({ totalPrice });
    }
  }

  /**
   * ✅ Envía la transacción al backend
   */
  onSubmit(): void {
    if (this.transactionForm.valid && !this.dateError) {
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
          this.successMessage = "✅ La transacción se ha creado correctamente.";
          this.errorMessage = '';

          setTimeout(() => {
            this.router.navigateByUrl(this.returnUrl);
          }, 2000);
        },
        error: (error) => {
          this.successMessage = '';
          this.errorMessage = error.error?.message || 'No se pudo crear la transacción.';
        },
      });
    } else {

      if (this.transactionForm.get('totalPrice')?.value < 0) {
        this.errorMessage = 'El precio debe ser positivo (0 o mayor).';
      } else {
        this.errorMessage = 'Por favor, corrige los errores en el formulario.';
      }
    }
  }

  /**
   * ✅ Cancela y redirige a la página de anuncios
   */
  onCancel(): void {
    this.router.navigateByUrl(this.returnUrl);
  }
}