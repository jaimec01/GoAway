import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';

interface Advertisement {
  id: string;
  title: string;
  description: string;
  advertisementCategory: string;
  photoUrls: string;
  advertisementCondition: string;
  price: number | null;
  createdAt: string;
}

@Component({
  selector: 'app-my-ads',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './my-ads.component.html',
  styleUrls: ['./my-ads.component.scss'],
})
export class MyAdsComponent implements OnInit {
  myAds: Advertisement[] = [];
  loading = true;
  errorMessage = '';

  constructor(
    private http: HttpClient,
    private router: Router) {}

  ngOnInit(): void {
    this.loadMyAds();
  }

  loadMyAds(): void {
    this.http.get<Advertisement[]>('/api/advertisements/myAdvertisements').subscribe({
      next: (data) => {
        console.log("Anuncios recibidos:", data);
        this.myAds = data.map((ad) => ({
          ...ad,
          createdAt: new Date(ad.createdAt).toLocaleDateString('es-ES'),
        }));
        this.loading = false;
      },
      error: (error) => {
        console.error('Error al cargar mis anuncios:', error);
        this.errorMessage = 'Error al cargar los anuncios. Inténtalo de nuevo.';
        this.loading = false;
      },
    });
  }

  deleteAd(adId: string): void {
    if (confirm('¿Estás seguro de que quieres eliminar este anuncio?')) {
      this.http.delete(`/api/advertisements/myAdvertisements`, {
        body: { advertisementId: adId },
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${sessionStorage.getItem('token')}`
        }
      }).subscribe({
        next: () => {
          console.log("Anuncio eliminado:", adId);
          this.myAds = this.myAds.filter(ad => ad.id !== adId);
        },
        error: (error) => {
          console.error("Error al eliminar anuncio:", error);
  
          if (error.status === 400) {
            alert("⚠️ " + error.error || "Este anuncio tiene una transacción abierta y no puede eliminarse.");
          } else if (error.status === 500) {
            alert("❌ Error inesperado al intentar eliminar el anuncio.");
          } else {
            alert("⚠️ No se pudo eliminar el anuncio. Inténtalo de nuevo.");
          }
        }
      });
    }
  }

  onLogoutClick(): void {
    console.log('🔴 Cerrando sesión...');
    sessionStorage.removeItem('token');
    this.router.navigate(['/']);
  }
  
  onMyAdsClick(): void {
    console.log('🔗 Redirigiendo a Mis Anuncios...');
    this.router.navigate(['/advertisements/my-ads']);
  }
  
  
  editAd(adId: string): void {
    this.router.navigate([`/advertisements/edit/${adId}`], {
      queryParams: { returnUrl: this.router.url }
    });
  }

  goToAdvertisements(): void {
    this.router.navigate(['/advertisements']);
  }


  onCreateAdvertisementClick(): void {
    this.router.navigate(['/advertisements/new'], {
      queryParams: { returnUrl: this.router.url }
    });
  }


  onFavoritesClick(): void {
    console.log('⭐ Redirigiendo a la lista de favoritos...');
    this.router.navigate(['/advertisements/favorites']);
  }
  
  onTransactionsClick(): void {
    console.log('💳 Redirigiendo a mis transacciones...');
    this.router.navigate(['/transaction/my-transactions']);
  }
}