import { Component, Inject, PLATFORM_ID, OnInit } from '@angular/core';
import { CommonModule, isPlatformBrowser } from '@angular/common';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';

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
  imports: [CommonModule],
  templateUrl: './advertisement-list.component.html',
  styleUrls: ['./advertisement-list.component.scss'],
})
export class AdvertisementListComponent implements OnInit {
  advertisements: Advertisement[] = [];
  loading = true;
  errorMessage = '';
  isAuthenticated = false;
  showLoginPopup = false;

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

    this.http.get<Advertisement[]>('/public/advertisements', { headers }).subscribe({
      next: (data) => {
        this.advertisements = data.map((ad) => ({
          ...ad,
          createdAt: new Date(ad.createdAt).toLocaleDateString('es-ES'),
        }));
        this.loading = false;
      },
      error: () => {
        this.errorMessage = 'Error al cargar los anuncios.';
        this.loading = false;
      },
    });
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
