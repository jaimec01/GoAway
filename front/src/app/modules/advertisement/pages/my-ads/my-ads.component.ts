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
      const url = '/api/advertisements/myAdvertisements';
  
      this.http.delete(url, {
        body: { advertisementId: adId }, 
        headers: { 'Content-Type': 'application/json' }
      }).subscribe({
        next: () => {
          console.log("Anuncio eliminado:", adId);
          this.router.navigateByUrl('/refresh', { skipLocationChange: true }).then(() => {
            this.router.navigate(['/advertisements/myAdvertisements']);
          });
        },
        error: (error) => {
          console.error("Error al eliminar anuncio:", error);
  
          if (error.error === "Este anuncio pertenece a una transacción.") {
            alert("Este anuncio pertenece a una transacción y no puede eliminarse.");
          }
        }
      });
    }
  }
  

  // Método para redirigir a la creación de anuncios
  onCreateAdvertisementClick(): void {
    this.router.navigate(['/advertisements/new'], {
      queryParams: { returnUrl: this.router.url }
    });
  }

  // Método para editar un anuncio
  editAd(adId: string): void {
    this.router.navigate([`/advertisements/edit/${adId}`]);
  }

  goToAdvertisements(): void {
    this.router.navigate(['/advertisements']);
  }
}