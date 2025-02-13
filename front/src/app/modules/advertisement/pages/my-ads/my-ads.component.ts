import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common'; // Importa CommonModule

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
  standalone: true, // Asegúrate de que el componente sea standalone
  imports: [CommonModule], // Importa CommonModule aquí
  templateUrl: './my-ads.component.html',
  styleUrls: ['./my-ads.component.scss'],
})
export class MyAdsComponent implements OnInit {
  myAds: Advertisement[] = [];
  loading = true;
  errorMessage = '';

  constructor(private http: HttpClient, private router: Router) {}

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

  // Método para redirigir a la creación de anuncios
  onCreateAdvertisementClick(): void {
    this.router.navigate(['/advertisements/new'], {
      queryParams: { returnUrl: this.router.url }
    });
  }

  goToAdvertisements(): void {
    this.router.navigate(['/advertisements']);
  }
  
}