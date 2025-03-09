import { Component, Inject, PLATFORM_ID, OnInit } from '@angular/core';
import { CommonModule, isPlatformBrowser } from '@angular/common';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';  // Importar FormsModule para el [(ngModel)]
import { ImageConfigService } from '@app/core/services/image-config.service'; // Usar el alias @app

interface Advertisement {
  id: string;
  title: string;
  description: string;
  advertisementCategory: string;
  photoUrls: string[];
  advertisementCondition: string;
  userEmail: string;
  price: number | null;
  createdAt: string;
  updatedAt: string;
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
  advertisements: Advertisement[] = []; // Todos los anuncios cargados
  displayedAdvertisements: Advertisement[] = []; // Anuncios mostrados en la página actual
  loading = true;
  errorMessage = '';
  isAuthenticated = false;
  showLoginPopup = false;

  // Paginación
  currentPage = 1; // Página actual
  itemsPerPage = 20; // Número de anuncios por página
  totalPages = 1; // Número total de páginas

  // Filtros
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

  // Filtros con los valores reales para el backend
  categories: string[] = Object.keys(this.categoryTranslations);
  conditions: string[] = Object.keys(this.conditionTranslations);

  selectedCategory: string = '';
  selectedCondition: string = '';
  selectedSortOrder: string = 'desc'; // Valor por defecto: más recientes primero

  constructor(
    private http: HttpClient,
    private router: Router,
    private imageConfigService: ImageConfigService, // Inyectar el servicio
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
    this.loading = true;

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
    if (this.selectedSortOrder) {
      params.push(`sortOrder=${this.selectedSortOrder}`);
    }

    if (params.length) {
      url += '?' + params.join('&');
    }

    this.http.get<Advertisement[]>(url, { headers }).subscribe({
      next: (data) => {
        this.advertisements = data.map((ad) => ({
          ...ad,
          photoUrls: Array.isArray(ad.photoUrls) ? ad.photoUrls : [ad.photoUrls], // Convertir a array si no lo es
          updatedAt: new Date(ad.updatedAt).toLocaleDateString('es-ES'),
        }));

        this.totalPages = Math.ceil(this.advertisements.length / this.itemsPerPage);
        this.updateDisplayedAdvertisements();

        this.loading = false;
      },
      error: () => {
        this.errorMessage = 'Error al cargar los anuncios.';
        this.loading = false;
      },
    });
  }

  updateDisplayedAdvertisements(): void {
    const startIndex = (this.currentPage - 1) * this.itemsPerPage;
    const endIndex = startIndex + this.itemsPerPage;
    this.displayedAdvertisements = this.advertisements.slice(startIndex, endIndex);
  }

  nextPage(): void {
    if (this.currentPage < this.totalPages) {
      this.currentPage++;
      this.updateDisplayedAdvertisements();
    }
  }

  previousPage(): void {
    if (this.currentPage > 1) {
      this.currentPage--;
      this.updateDisplayedAdvertisements();
    }
  }

  applyFilters(): void {
    this.currentPage = 1; // Reiniciar a la primera página
    this.fetchAdvertisements();
  }

  onScroll(): void {
    // Verificar si el usuario ha llegado al final de la página
    if ((window.innerHeight + window.scrollY) >= document.body.offsetHeight - 100) {
      this.fetchAdvertisements(); // Cargar más anuncios
    }
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

  onAdvertisementClick(advertisementId: string): void {
    this.router.navigate([`/advertisement/${advertisementId}`], {
      queryParams: { returnUrl: this.router.url } 
    });
  }

  get noAdsMessage(): string {
    if (this.advertisements.length === 0) {
      return '¡No hay anuncios disponibles!';
    } else if (this.displayedAdvertisements.length === 0) {
      return '¡Todavía no hay anuncios para estos filtros!';
    }
    return '';
  }

  getImageUrl(relativePath: string): string {
    if (!relativePath) {
      return 'assets/images/placeholder.png'; // Imagen de placeholder si no hay ruta
    }
    return `${this.imageConfigService.imageBaseUrl}/${relativePath}`;
  }
}