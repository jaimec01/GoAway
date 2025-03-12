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
  createdAt?: string;
  updatedAt?: string;
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
  sortOrder: 'asc' | 'desc' = 'desc'; // Valor por defecto: más reciente primero

  // Estado del popup de contacto
  showContactPopup = false;
  selectedTransaction: Transaction | null = null;
  contactMessage = '';
  characterCount = 0;

  constructor(private http: HttpClient, private router: Router) {}

  ngOnInit(): void {
    this.fetchTransactions();
  }

  // Mensajes cuando no hay transacciones
  get noOwnerTransactionsMessage(): string {
    return 'No tienes peticiones de alquiler.';
  }

  get noTenantTransactionsMessage(): string {
    return 'No has realizado ninguna solicitud de alquiler.';
  }

  getConfirmationIcon(status: string): string {
    switch (status) {
      case 'ACCEPTED':
        return 'assets/images/acceptedTransaction.png';
      case 'PENDING':
        return 'assets/images/pendingTransaction.png';
      case 'REJECTED':
        return 'assets/images/rejectTransaction.png';
      default:
        return 'assets/images/pendingTransaction.png'; // Por defecto
    }
  }

  getConfirmationTooltip(status: string): string {
    switch (status) {
      case 'ACCEPTED':
        return 'Transacción aceptada por el propietario';
      case 'PENDING':
        return 'Esperando confirmación del propietario';
      case 'REJECTED':
        return 'Transacción rechazada por el propietario';
      default:
        return 'Estado desconocido';
    }
  }

  setView(view: 'owner' | 'tenant'): void {
    this.isOwnerView = view === 'owner';
    this.fetchTransactions();
  }

  fetchTransactions(): void {
    this.loading = true;
    const endpoint = this.isOwnerView 
      ? `/api/transactions/owner?order=${this.sortOrder}` 
      : `/api/transactions/tenant?order=${this.sortOrder}`;
  
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

  fetchAdvertisement(transaction: Transaction): void {
    this.http.get<Advertisement>(`/public/advertisements/${transaction.advertisementId}`).subscribe({
      next: (advertisement) => {
        transaction.advertisement = advertisement;
      },
      error: (error) => {
        console.error(`Error al obtener anuncio ${transaction.advertisementId}:`, error);
      }
    });
  }

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
        this.fetchTransactions();
      },
      error: (error) => {
        console.error('❌ Error al aceptar la transacción:', error);
        this.successMessage = '';
        this.errorMessage = error.error?.message || 'No se pudo aceptar la transacción.';
      }
    });
  }

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
        this.fetchTransactions();
      },
      error: (error) => {
        console.error('❌ Error al rechazar la transacción:', error);
        this.successMessage = '';
        this.errorMessage = error.error?.message || 'No se pudo rechazar la transacción.';
      }
    });
  }

  formatDateTime(dateTime?: string): string {
    if (!dateTime) return 'No disponible';
  
    // Asegurarnos de que la fecha esté en formato UTC (agregar 'Z' si no está presente)
    const utcDateString = dateTime.endsWith('Z') ? dateTime : `${dateTime}Z`;
  
    // Crear un objeto Date a partir de la cadena de fecha/hora en UTC
    const date = new Date(utcDateString);
  
    // Verificar si la fecha es válida
    if (isNaN(date.getTime())) {
      return 'Fecha inválida';
    }
  
    // Formatear la fecha y hora según la zona horaria de Madrid
    const formatter = new Intl.DateTimeFormat('es-ES', {
      timeZone: 'Europe/Madrid', // Zona horaria de Madrid
      day: '2-digit',
      month: '2-digit',
      year: 'numeric',
      hour: '2-digit',
      minute: '2-digit',
      hour12: false, // Usar formato de 24 horas
    });
  
    return formatter.format(date);
  }

  openContactPopup(transaction: Transaction): void {
    this.selectedTransaction = transaction;
    this.contactMessage = '';
    this.characterCount = 0;
    this.showContactPopup = true;
  }

  closeContactPopup(): void {
    this.showContactPopup = false;
    this.selectedTransaction = null;
    this.contactMessage = '';
    this.characterCount = 0;
  }

  updateCharacterCount(): void {
    this.characterCount = this.contactMessage.length;
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

  onAdvertisementClick(advertisementId: string): void {
    this.router.navigate([`/advertisement/${advertisementId}`], {
      queryParams: { returnUrl: this.router.url } 
    });
  }
}