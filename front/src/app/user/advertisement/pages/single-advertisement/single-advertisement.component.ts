import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { ImageConfigService } from '@app/core/services/image-config.service';

@Component({
  selector: 'app-single-advertisement',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './single-advertisement.component.html',
  styleUrls: ['./single-advertisement.component.scss']
})
export class SingleAdvertisementComponent implements OnInit {
  advertisement: any | null = null;
  loading: boolean = true;
  error: string | null = null;
  returnUrl: string = '/advertisements'; // Ruta por defecto para volver
  currentImageIndex: number = 0; // Índice de la imagen actual

  // Mapeo de nombres en inglés a nombres en español
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

  constructor(
    private route: ActivatedRoute,
    private http: HttpClient,
    private router: Router,
    private imageConfigService: ImageConfigService
  ) {
    this.route.queryParams.subscribe(params => {
      if (params['returnUrl']) {
        this.returnUrl = params['returnUrl'];
        console.log("URL de retorno capturada:", this.returnUrl);
      }
    });
  }

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.getAdvertisement(id);
    }

    // Capturamos la URL de retorno si existe
    this.route.queryParams.subscribe(params => {
      if (params['returnUrl']) {
        this.returnUrl = params['returnUrl'];
        console.log("URL de retorno capturada:", this.returnUrl);
      }
    });
  }

  getAdvertisement(id: string): void {
    this.loading = true;
    this.error = null;

    // Obtener el token de autenticación si existe
    const token = sessionStorage.getItem('token');
    const headers = token ? new HttpHeaders({
      'Authorization': `Bearer ${token}`
    }) : new HttpHeaders();

    this.http.get<any>(`/public/advertisements/${id}`, { headers }).subscribe({
      next: (data) => {
        console.log("Datos del anuncio recibidos del servidor:", data);
        // Verificar si la respuesta tiene la estructura esperada
        if (data && typeof data === 'object') {
          this.advertisement = {
            ...data,
            photoUrls: Array.isArray(data.photoUrls) ? data.photoUrls : [] // Asegurar que photoUrls sea un array
          };
          // Reiniciar el índice si no hay imágenes
          this.currentImageIndex = this.advertisement.photoUrls.length > 0 ? 0 : -1;
        } else {
          this.advertisement = null;
          this.error = 'Datos del anuncio no válidos.';
        }
        this.loading = false;
        console.log("Anuncio procesado para mostrar:", this.advertisement);
      },
      error: (err) => {
        console.error("Error al cargar el anuncio:", err);
        this.error = err.error?.message || 'No se pudo cargar el anuncio. Inténtalo de nuevo.';
        this.loading = false;
      },
    });
  }

  // Método para obtener la URL completa de la imagen
  getImageUrl(photo: any): string {
    if (!photo || !photo.photoUrl) {
      return 'assets/images/noImages.png';
    }
    return `${this.imageConfigService.imageBaseUrl}/${photo.photoUrl}`;
  }

  // Método para retroceder en el carrusel
  prevImage(): void {
    if (this.currentImageIndex > 0 && this.advertisement?.photoUrls?.length) {
      this.currentImageIndex--;
    }
  }

  // Método para avanzar en el carrusel
  nextImage(): void {
    if (this.advertisement?.photoUrls?.length && this.currentImageIndex < this.advertisement.photoUrls.length - 1) {
      this.currentImageIndex++;
    }
  }

  // Método para ir a una imagen específica
  goToImage(index: number): void {
    if (this.advertisement?.photoUrls?.length && index >= 0 && index < this.advertisement.photoUrls.length) {
      this.currentImageIndex = index;
    }
  }

  /**
   * Método para volver a la ruta anterior
   */
  goBack(): void {
    console.log("Redirigiendo a:", this.returnUrl);
    this.router.navigateByUrl(this.returnUrl);
  }
}