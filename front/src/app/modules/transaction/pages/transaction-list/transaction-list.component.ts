import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';  
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';

interface Advertisement {
  id: string;
  title: string;
  description: string;
  price: number;
}

interface Transaction {
  id: string;
  startDate: string;
  endDate: string;
  totalPrice: number;
  paymentMethod: string;
  status: string;
  ownerConfirmation: string;
  tenantEmail?: string;
  ownerEmail?: string;
  advertisementId: string;
  advertisement?: Advertisement; 
}

@Component({
  selector: 'app-transaction-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './transaction-list.component.html',
  styleUrls: ['./transaction-list.component.scss'],
})
export class TransactionListComponent implements OnInit {
  transactions: Transaction[] = [];
  isOwnerView = true;
  loading = true;
  errorMessage = '';
  successMessage = '';

  // Estado del popup de contacto
  showContactPopup = false;
  selectedTransaction: Transaction | null = null;
  contactMessage = '';

  constructor(private http: HttpClient, private router: Router) {}

  ngOnInit(): void {
    this.fetchTransactions();
  }

  setView(view: 'owner' | 'tenant'): void {
    this.isOwnerView = view === 'owner';
    this.fetchTransactions();
  }

  fetchTransactions(): void {
    this.loading = true;
    const endpoint = this.isOwnerView ? '/api/transactions/owner' : '/api/transactions/tenant';

    this.http.get<Transaction[]>(endpoint).subscribe({
      next: (data) => {
        this.transactions = data;
        this.loading = false;

        this.transactions.forEach(transaction => {
          this.fetchAdvertisement(transaction);
        });
      },
      error: (error) => {
        console.error('Error al obtener transacciones:', error);
        this.errorMessage = 'No se pudieron cargar las transacciones.';
        this.loading = false;
      },
    });
  }

  /**
   * 📌 Obtiene los detalles del anuncio usando el `advertisementId`
   */
  fetchAdvertisement(transaction: Transaction): void {
    this.http.get<Advertisement>(`/api/advertisements/${transaction.advertisementId}`).subscribe({
      next: (advertisement) => {
        transaction.advertisement = advertisement;
      },
      error: (error) => {
        console.error(`Error al obtener anuncio ${transaction.advertisementId}:`, error);
      }
    });
  }

    /**
   * ✅ Acepta una transacción
   */
  onAccept(transactionId: string): void {
    const token = sessionStorage.getItem('token');

    if (!token) {
      this.errorMessage = 'Error de autenticación. Inicia sesión.';
      return;
    }

    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      Authorization: `Bearer ${token}`
    });

    this.http.post(`/api/transactions/${transactionId}/accept`, {}, { headers }).subscribe({
      next: () => {
        console.log('✅ Transacción aceptada.');
        this.successMessage = '✅ Transacción aceptada correctamente.';
        this.errorMessage = '';
        this.fetchTransactions(); // Recargar lista de transacciones
      },
      error: (error) => {
        console.error('❌ Error al aceptar la transacción:', error);
        this.successMessage = '';
        this.errorMessage = error.error?.message || 'No se pudo aceptar la transacción.';
      }
    });
  }

  /**
   * ❌ Rechaza una transacción
   */
  onReject(transactionId: string): void {
    const token = sessionStorage.getItem('token');

    if (!token) {
      this.errorMessage = 'Error de autenticación. Inicia sesión.';
      return;
    }

    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      Authorization: `Bearer ${token}`
    });

    this.http.post(`/api/transactions/${transactionId}/reject`, {}, { headers }).subscribe({
      next: () => {
        console.log('❌ Transacción rechazada.');
        this.successMessage = '❌ Transacción rechazada correctamente.';
        this.errorMessage = '';
        this.fetchTransactions(); // Recargar lista de transacciones
      },
      error: (error) => {
        console.error('❌ Error al rechazar la transacción:', error);
        this.successMessage = '';
        this.errorMessage = error.error?.message || 'No se pudo rechazar la transacción.';
      }
    });
  }

  /**
   * 📩 Abrir el popup de contacto
   */
  openContactPopup(transaction: Transaction): void {
    this.selectedTransaction = transaction;
    this.contactMessage = ''; // Limpiar mensaje anterior
    this.showContactPopup = true;
  }

  /**
   * ❌ Cerrar el popup de contacto
   */
  closeContactPopup(): void {
    this.showContactPopup = false;
    this.selectedTransaction = null;
    this.contactMessage = '';
  }

  onFavoritesClick(): void {
    this.router.navigate(['/advertisements/favorites']);
  }

  onTransactionsClick(): void {
    this.router.navigate(['/transactions']);
  }

  onCreateAdvertisementClick(): void {
    this.router.navigate(['/advertisements/new']);
  }

  onLogoutClick(): void {
    sessionStorage.removeItem('token');
    this.router.navigate(['/']);
  }

  goToAdvertisements(): void {
    this.router.navigate(['/advertisements']);
  }

  onMyAdsClick(): void {
    console.log('🔗 Redirigiendo a Mis Anuncios...');
    this.router.navigate(['/advertisements/my-ads']);
  }
}
