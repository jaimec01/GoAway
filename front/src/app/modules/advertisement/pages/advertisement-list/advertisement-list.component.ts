import { Component, Inject, PLATFORM_ID, OnInit } from '@angular/core';
import { CommonModule, isPlatformBrowser } from '@angular/common';
import { HttpClient } from '@angular/common/http';
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

      this.http.get<Advertisement[]>('/public/advertisements').subscribe({
        next: (data) => {
          this.advertisements = data.map((ad) => ({
            ...ad,
            title: ad.title ? ad.title : 'Sin título',
            price: ad.price !== null ? ad.price : 0,
            createdAt: new Date(ad.createdAt).toLocaleDateString('es-ES'),
          }));
          this.loading = false;
        },
        error: (error) => {
          this.errorMessage = 'Error al cargar los anuncios. Inténtalo de nuevo.';
          this.loading = false;
        },
      });
    }
  }

  checkAuthentication(): void {
    const token = sessionStorage.getItem('token');
    this.isAuthenticated = !!token;
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

  onCreateAdvertisementClick(): void {
    if (this.isAuthenticated) {
      this.router.navigate(['/advertisements/new']);
    } else {
      this.showAuthPopup();
    }
  }

  onMyAdsClick(): void {
    if (this.isAuthenticated) {
      this.router.navigate(['/advertisements/my-ads']);
    }
  }

  onRentClick(advertisementId: string): void {
    if (this.isAuthenticated) {
      this.router.navigate([`/transaction/new/${advertisementId}`]);
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
