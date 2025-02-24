import { Component, Inject, PLATFORM_ID, OnInit } from '@angular/core';
import { CommonModule, isPlatformBrowser } from '@angular/common';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';  // Importar FormsModule para el [(ngModel)]

interface Advertisement {
  id: string;
  title: string;
  description: string;
  advertisementCategory: string;
  photoUrls: string;
  advertisementCondition: string;
  userEmail: string;
  price: number | null;
  createdAt: string;
  isFavorite?: boolean;
}

@Component({
  selector: 'app-advertisement-list',
  standalone: true,
  imports: [CommonModule, FormsModule], // Importar FormsModule
  templateUrl: './advertisement-list.component.html',
  styleUrls: ['./advertisement-list.component.scss'],
})
export class AdvertisementListComponent implements OnInit {
  advertisements: Advertisement[] = [];
  loading = true;
  errorMessage = '';
  isAuthenticated = false;
  showLoginPopup = false;

  // Filtros
  categories: string[] = ['chair', 'table', 'TV', 'chestOfDrawers', 'sofa', 'bookshelf', 'other'];
  conditions: string[] = ['Good', 'Fair', 'Excellent'];  
  selectedCategory: string = '';
  selectedCondition: string = '';

  constructor(
    private http: HttpClient,
    private router: Router,
    @Inject(PLATFORM_ID) private platformId: Object
  ) {}

  ngOnInit(): void {
    if (isPlatformBrowser(this.platformId)) {
      this.checkAuthentication();
      this.fetchAdvertisements();
    }
  }

  checkAuthentication(): void {
    const token = sessionStorage.getItem('token');
    this.isAuthenticated = !!token;
  }

  fetchAdvertisements(): void {
    const token = sessionStorage.getItem('token');
    const headers = token
      ? new HttpHeaders({ Authorization: `Bearer ${token}` })
      : undefined;

    let url = `/public/advertisements`;

    // Aplicar filtros en la URL si están seleccionados
    const params = [];
    if (this.selectedCategory) {
      params.push(`category=${this.selectedCategory}`);
    }
    if (this.selectedCondition) {
      params.push(`condition=${this.selectedCondition}`);
    }

    if (params.length) {
      url += '?' + params.join('&');
    }

    this.http.get<Advertisement[]>(url, { headers }).subscribe({
      next: (data) => {
        this.advertisements = data.map((ad) => ({
          ...ad,
          createdAt: new Date(ad.createdAt).toLocaleDateString('es-ES'),
        }));

        if (this.advertisements.length === 0) {
          this.errorMessage = 'No hay anuncios para estos filtros.';
        } else {
          this.errorMessage = ''; 
        }


        this.loading = false;
      },
      error: () => {
        this.errorMessage = 'Error al cargar los anuncios.';
        this.loading = false;
      },
    });
  }

  applyFilters(): void {
    this.fetchAdvertisements(); // Vuelve a cargar los anuncios con los filtros aplicados
  }

  toggleFavorite(advertisementId: string): void {
    if (!this.isAuthenticated) {
      this.showAuthPopup();
      return;
    }

    const adIndex = this.advertisements.findIndex((ad) => ad.id === advertisementId);
    if (adIndex === -1) return;

    const advertisement = this.advertisements[adIndex];
    const token = sessionStorage.getItem('token');
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      Authorization: `Bearer ${token}`,
    });

    this.http.post('/api/favorites', { advertisementId }, { headers }).subscribe({
      next: (response: any) => {
        advertisement.isFavorite = response.message.includes('añadido');
      },
      error: (error) => {
        console.error('Error al alternar favorito:', error);
      },
    });
  }

  // Métodos de navegación y autenticación
  onLoginClick(): void {
    this.router.navigate(['/login']);
  }

  onSignUpClick(): void {
    this.router.navigate(['/register']);
  }

  onLogoutClick(): void {
    sessionStorage.removeItem('token');
    this.isAuthenticated = false;
    this.router.navigate(['/']);
  }

  onMyAdsClick(): void {
    if (this.isAuthenticated) {
      this.router.navigate(['/advertisements/my-ads']);
    } else {
      this.showAuthPopup();
    }
  }

  onRentClick(advertisementId: string): void {
    if (this.isAuthenticated) {
      this.router.navigate([`/transaction/new/${advertisementId}`]);
    } else {
      this.showAuthPopup();
    }
  }

  onCreateAdvertisementClick(): void {
    if (this.isAuthenticated) {
      this.router.navigate(['/advertisements/new']);
    } else {
      this.showAuthPopup();
    }
  }

  onFavoritesClick(): void {
    if (this.isAuthenticated) {
      this.router.navigate(['/advertisements/favorites']);
    } else {
      this.showAuthPopup();
    }
  }

  onTransactionsClick(): void {
    if (this.isAuthenticated) {
      this.router.navigate(['/transaction/my-transactions']);
    } else {
      this.showAuthPopup();
    }
  }

  showAuthPopup(): void {
    this.showLoginPopup = true;
  }

  closeLoginPopup(): void {
    this.showLoginPopup = false;
  }
}
