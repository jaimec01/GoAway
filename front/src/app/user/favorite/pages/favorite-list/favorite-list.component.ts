import { Component, Inject, PLATFORM_ID, OnInit } from '@angular/core';
import { CommonModule, isPlatformBrowser } from '@angular/common';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';
import { ImageConfigService } from '@app/core/services/image-config.service'; // Importar el servicio

interface Advertisement {
  id: string;
  title: string;
  description: string;
  advertisementCategory: string;
  photoUrls: string[]; // Cambiar a array de strings
  advertisementCondition: string;
  userEmail: string;
  price: number | null;
  createdAt: string;
  isFavorite?: boolean;
}

@Component({
  selector: 'app-favorite-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './favorite-list.component.html',
  styleUrls: ['./favorite-list.component.scss'],
})
export class FavoriteListComponent implements OnInit {
  favoriteAdvertisements: Advertisement[] = [];
  loading = true;
  errorMessage = '';

  constructor(
    private http: HttpClient,
    private router: Router,
    private imageConfigService: ImageConfigService, // Inyectar el servicio
    @Inject(PLATFORM_ID) private platformId: Object
  ) {}

  ngOnInit(): void {
    if (isPlatformBrowser(this.platformId)) {
      this.fetchFavoriteAdvertisements();
    }
  }

  get noAdsMessage(): string {
    if (this.favoriteAdvertisements.length === 0) {
      return '¡Aún no tienes anuncios en favoritos!';
    }
    return '';
  }

  fetchFavoriteAdvertisements(): void {
    const token = sessionStorage.getItem('token');
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
    });

    this.http.get<Advertisement[]>('/api/favorites', { headers }).subscribe({
      next: (data) => {
        this.favoriteAdvertisements = data.map((ad) => ({
          ...ad,
          photoUrls: Array.isArray(ad.photoUrls) ? ad.photoUrls : [ad.photoUrls], // Convertir a array si no lo es
          createdAt: new Date(ad.createdAt).toLocaleDateString('es-ES'),
          isFavorite: true,
        }));
        this.loading = false;
      },
      error: () => {
        this.errorMessage = 'Error al cargar los anuncios favoritos.';
        this.loading = false;
      },
    });
  }

  // Método para obtener la URL completa de la imagen
  getImageUrl(relativePath: string): string {
    if (!relativePath) {
      return 'assets/images/noImages.png'; 
    }
    return `${this.imageConfigService.imageBaseUrl}/${relativePath}`;
  }

  toggleFavorite(advertisementId: string): void {
    const token = sessionStorage.getItem('token');
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      Authorization: `Bearer ${token}`,
    });

    this.http.post('/api/favorites', { advertisementId }, { headers }).subscribe({
      next: () => {
        this.favoriteAdvertisements = this.favoriteAdvertisements.filter(
          (ad) => ad.id !== advertisementId
        );
      },
      error: (error) => {
        console.error('Error al alternar favorito:', error);
      },
    });
  }

  onMyAdsClick(): void {
    this.router.navigate(['/advertisements/my-ads']);
  }

  onLogoutClick(): void {
    sessionStorage.removeItem('token');
    this.router.navigate(['/']);
  }

  onRentClick(advertisementId: string): void {
    this.router.navigate([`/transaction/new/${advertisementId}`]);
  }

  onTransactionsClick(): void {
    this.router.navigate(['/transaction/my-transactions']);
  }

  onCreateAdvertisementClick(): void {
    this.router.navigate(['/advertisements/new']);
  }

  onFavoritesClick(): void {
    this.router.navigate(['/advertisements/favorites']);
  }

  goToAdvertisements(): void {
    this.router.navigate(['/advertisements']);
  }

  onAdvertisementClick(advertisementId: string): void {
    this.router.navigate([`/advertisement/${advertisementId}`], {
      queryParams: { returnUrl: this.router.url } 
    });
  }
}