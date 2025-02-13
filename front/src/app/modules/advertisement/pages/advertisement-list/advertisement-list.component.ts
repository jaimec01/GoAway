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
  isAuthenticated = false; // Verifica si el usuario tiene token
  showLoginPopup = false; // Estado del pop-up

  constructor(
    private http: HttpClient,
    private router: Router,
    @Inject(PLATFORM_ID) private platformId: Object
  ) {
    console.log('✅ AdvertisementListComponent inicializado');
  }

  ngOnInit(): void {
    if (isPlatformBrowser(this.platformId)) {
      this.checkAuthentication(); // Comprobar autenticación al cargar la página

      this.http.get<Advertisement[]>('/public/advertisements').subscribe({
        next: (data) => {
          console.log('📌 Datos recibidos del backend:', data);
          this.advertisements = data.map((ad) => ({
            ...ad,
            title: ad.title ? ad.title : 'Sin título',
            price: ad.price !== null ? ad.price : 0,
            createdAt: new Date(ad.createdAt).toLocaleDateString('es-ES'),
          }));
          this.loading = false;
        },
        error: (error) => {
          console.error('❌ Error al obtener los anuncios:', error);
          this.errorMessage = 'Error al cargar los anuncios. Inténtalo de nuevo.';
          this.loading = false;
        },
      });
    }
  }

  // ✅ Verificar autenticación
  checkAuthentication(): void {
    const token = sessionStorage.getItem('token');
    this.isAuthenticated = !!token;
  }

  // ✅ Redirigir a login
  onLoginClick(): void {
    console.log('🔄 Redirigiendo a login...');
    this.router.navigate(['/login']);
  }

  // ✅ Redirigir a registro
  onSignUpClick(): void {
    console.log('🔄 Redirigiendo a registro...');
    this.router.navigate(['/register']);
  }

  // ✅ Cerrar sesión
  onLogoutClick(): void {
    console.log('🔴 Cerrando sesión...');
    sessionStorage.removeItem('token');
    this.isAuthenticated = false;
    this.router.navigate(['/']);
  }

  // ✅ Redirigir a la página de creación de anuncios o mostrar pop-up
  onCreateAdvertisementClick(): void {
    if (this.isAuthenticated) {
      console.log('➕ Redirigiendo a creación de anuncio...');
      this.router.navigate(['/advertisements/new'], {
        queryParams: { returnUrl: this.router.url }
      });
    } else {
      console.log('⚠️ Usuario no autenticado, mostrando pop-up.');
      this.showLoginPopup = true;
    }
  }

  // ✅ Redirigir a la página de "Mis Anuncios"
  onMyAdsClick(): void {
    if (this.isAuthenticated) {
      console.log('🔗 Redirigiendo a Mis Anuncios...');
      this.router.navigate(['/advertisements/my-ads']);
    }
  }


  // ✅ Cerrar pop-up de inicio de sesión
  closeLoginPopup(): void {
    this.showLoginPopup = false;
  }
}