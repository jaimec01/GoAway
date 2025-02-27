import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';

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
    private router: Router 
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
    this.http.get<any>(`/public/advertisements/${id}`)
      .subscribe({
        next: (data) => {
          this.advertisement = data;
          this.loading = false;
        },
        error: () => {
          this.error = 'No se pudo cargar el anuncio.';
          this.loading = false;
        }
      });
  }

  /**
   * Método para volver a la ruta anterior
   */
  goBack(): void {
    console.log("Redirigiendo a:", this.returnUrl);
    this.router.navigateByUrl(this.returnUrl); 
  }
}