import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router, ActivatedRoute } from '@angular/router';
import { CommonModule } from '@angular/common';
import { Observable, forkJoin } from 'rxjs';
import { ImageConfigService } from '@app/core/services/image-config.service';

@Component({
  selector: 'app-modify-advertisement',
  templateUrl: './modify-advertisement.component.html',
  styleUrls: ['./modify-advertisement.component.scss'],
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
})
export class ModifyAdvertisementComponent implements OnInit {
  @ViewChild('fileInput') fileInput: any;
  adForm: FormGroup;
  errorMessage: string = '';
  successMessage: string = '';
  advertisementId: string = '';
  returnUrl: string = '/advertisements/my-ads';
  characterCount: number = 0;
  fileError: string = '';
  selectedFiles: File[] = [];
  isDragging: boolean = false;
  existingPhotos: { id: string; photoUrl: string }[] = [];
  photosMarkedForDeletion: string[] = [];

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

  advertisementCategories = Object.keys(this.categoryTranslations);
  advertisementConditions = Object.keys(this.conditionTranslations);

  constructor(
    private fb: FormBuilder,
    private http: HttpClient,
    private router: Router,
    private route: ActivatedRoute,
    private imageConfigService: ImageConfigService
  ) {
    this.adForm = this.fb.group({
      title: ['', Validators.required],
      description: ['', Validators.required],
      category: ['', Validators.required],
      condition: ['', Validators.required],
      price: [0, [Validators.required, Validators.min(0)]],
      active: [true],
    });
  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      if (params['id']) {
        this.advertisementId = params['id'];
        this.loadAdvertisement();
      }
    });

    this.route.queryParams.subscribe(params => {
      if (params['returnUrl']) {
        this.returnUrl = params['returnUrl'];
      }
    });

    this.adForm.get('description')?.valueChanges.subscribe(() => this.updateCharacterCount());
  }

  updateCharacterCount(): void {
    const description = this.adForm.get('description')?.value || '';
    this.characterCount = description.length;
  }

  openFileInput(): void {
    this.fileInput.nativeElement.click();
  }

  onFileChange(event: any): void {
    const files = Array.from(event.target.files) as File[];
    this.handleFiles(files);
  }

  onDragOver(event: DragEvent): void {
    event.preventDefault();
    this.isDragging = true;
  }

  onDragLeave(): void {
    this.isDragging = false;
  }

  onDrop(event: DragEvent): void {
    event.preventDefault();
    this.isDragging = false;
    if (event.dataTransfer?.files) {
      const files = Array.from(event.dataTransfer.files) as File[];
      this.handleFiles(files);
    }
  }

  handleFiles(files: File[]): void {
    // Calcular cuántas imágenes quedarán después de eliminar las marcadas para eliminación
    const remainingExistingPhotos = this.existingPhotos.length - this.photosMarkedForDeletion.length;
    const totalPhotosAfterAdding = remainingExistingPhotos + this.selectedFiles.length + files.length;

    if (totalPhotosAfterAdding > 6) {
      this.fileError = `No puedes tener más de 6 imágenes en total. Actualmente tienes ${remainingExistingPhotos} imágenes existentes y estás intentando añadir ${this.selectedFiles.length + files.length} más.`;
      return;
    }

    this.selectedFiles = [...this.selectedFiles, ...files];
    this.validateFiles();
  }

  validateFiles(): void {
    this.fileError = '';
    const invalidFiles = this.selectedFiles.filter(
      (file) => !['image/jpeg', 'image/png'].includes(file.type)
    );
    if (invalidFiles.length > 0) {
      this.fileError = 'Solo se permiten archivos .jpg y .png.';
      this.selectedFiles = this.selectedFiles.filter(
        (file) => ['image/jpeg', 'image/png'].includes(file.type)
      );
    }
  }

  getImagePreview(file: File): string {
    return URL.createObjectURL(file);
  }

  getImageUrl(photoUrl: string): string {
    if (!photoUrl) {
      return 'assets/images/placeholder.png';
    }
    return `${this.imageConfigService.imageBaseUrl}/${photoUrl}`;
  }

  removeImage(index: number): void {
    this.selectedFiles.splice(index, 1);
    this.fileError = '';
  }

  markPhotoForDeletion(photoId: string, event: Event): void {
    event.stopPropagation();
    console.log('Marcando foto para eliminación - ID recibido:', photoId);
    if (photoId && !this.photosMarkedForDeletion.includes(photoId)) {
      this.photosMarkedForDeletion.push(photoId);
      console.log('Fotos marcadas para eliminación:', this.photosMarkedForDeletion);
    } else if (this.photosMarkedForDeletion.includes(photoId)) {
      this.photosMarkedForDeletion = this.photosMarkedForDeletion.filter(id => id !== photoId);
      console.log('Fotos marcadas para eliminación después de desmarcar:', this.photosMarkedForDeletion);
    }
  }

  isMarkedForDeletion(photoId: string): boolean {
    return this.photosMarkedForDeletion.includes(photoId);
  }

  loadAdvertisement(): void {
    const token = sessionStorage.getItem('token');
    if (!token) {
      this.errorMessage = 'Error de autenticación. Inicia sesión.';
      return;
    }

    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });

    this.http.get<any>(`/public/advertisements/${this.advertisementId}`, { headers }).subscribe({
      next: (data) => {
        console.log("📌 Datos del anuncio obtenidos:", data);
        this.adForm.patchValue({
          title: data.title,
          description: data.description,
          category: data.advertisementCategory,
          condition: data.advertisementCondition,
          price: data.price,
          active: data.active
        });

        if (data.photoUrls && Array.isArray(data.photoUrls)) {
          this.existingPhotos = data.photoUrls.map((photo: any) => {
            console.log('Mapeando foto:', photo);
            return {
              id: photo.id || 'unknown-id',
              photoUrl: photo.photoUrl || ''
            };
          });
        } else {
          this.existingPhotos = [];
          console.warn('photoUrls no es un array o está ausente:', data.photoUrls);
        }
        console.log("📸 Fotos existentes:", this.existingPhotos);
        this.updateCharacterCount();
      },
      error: (error) => {
        console.error("❌ Error al obtener el anuncio:", error);
        this.errorMessage = "Error al cargar el anuncio. Inténtalo de nuevo.";
      }
    });
  }

  deletePhotos(): Observable<any>[] {
    const token = sessionStorage.getItem('token');
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });

    console.log("🗑️ Fotos a eliminar (antes de filtrar):", this.photosMarkedForDeletion);
    const validPhotoIds = this.photosMarkedForDeletion.filter(photoId => photoId && photoId !== 'undefined');
    if (validPhotoIds.length === 0) {
      console.warn('No hay IDs válidos para eliminar');
      return [];
    }

    return validPhotoIds.map(photoId => {
      console.log(`Enviando DELETE para photoId: ${photoId}`);
      return this.http.delete(`/api/advertisements/${this.advertisementId}/photos/${photoId}`, {
        headers,
        responseType: 'text'
      });
    });
  }

  onSubmit(): void {
    if (this.adForm.valid) {
      console.log("📤 Enviando datos al backend:", this.adForm.value);

      const token = sessionStorage.getItem('token');
      if (!token) {
        this.errorMessage = 'Error de autenticación. Inicia sesión.';
        return;
      }

      if (this.photosMarkedForDeletion.length > 0) {
        const deleteRequests = this.deletePhotos();
        if (deleteRequests.length > 0) {
          forkJoin(deleteRequests).subscribe({
            next: (responses) => {
              console.log("✅ Respuestas de eliminación:", responses);
              const allSuccessful = responses.every((response: string) =>
                response === 'Foto eliminada correctamente.'
              );
              if (allSuccessful) {
                this.existingPhotos = this.existingPhotos.filter(
                  photo => !this.photosMarkedForDeletion.includes(photo.id)
                );
                this.photosMarkedForDeletion = [];
                this.successMessage = 'Fotos eliminadas correctamente.';
                this.errorMessage = '';
                this.updateAdvertisement();
              } else {
                this.errorMessage = 'Algunas fotos no se pudieron eliminar correctamente.';
              }
            },
            error: (error) => {
              console.error("❌ Error al eliminar fotos:", error);
              this.errorMessage = 'No se pudieron eliminar las fotos. Inténtalo de nuevo.';
            },
            complete: () => {
              console.log("🔔 Eliminación completada");
            }
          });
        } else {
          this.updateAdvertisement();
        }
      } else {
        this.updateAdvertisement();
      }
    } else {
      console.warn("⚠️ Formulario inválido, revisa los campos.");
    }
  }

  updateAdvertisement(): void {
    const token = sessionStorage.getItem('token');
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });

    const formData = new FormData();
    const advertisementData = {
      id: this.advertisementId,
      title: this.adForm.get('title')?.value,
      description: this.adForm.get('description')?.value,
      category: this.adForm.get('category')?.value,
      condition: this.adForm.get('condition')?.value,
      price: this.adForm.get('price')?.value,
      active: this.adForm.get('active')?.value,
      existingPhotoIds: this.existingPhotos
        .filter(photo => !this.photosMarkedForDeletion.includes(photo.id))
        .map(photo => photo.id) // Enviar los IDs de las imágenes que queremos conservar
    };
    console.log("📤 Enviando advertisementData:", advertisementData);
    formData.append('advertisement', new Blob([JSON.stringify(advertisementData)], {
      type: 'application/json',
    }));

    this.selectedFiles.forEach((file) => {
      formData.append('photos', file, file.name);
    });

    this.http.put(`/api/advertisements/${this.advertisementId}`, formData, { headers }).subscribe({
      next: () => {
        console.log("✅ Anuncio modificado correctamente.");
        this.successMessage = "✅ El anuncio se ha modificado correctamente.";
        this.errorMessage = '';
        setTimeout(() => {
          this.router.navigateByUrl(this.returnUrl);
        }, 2000);
      },
      error: (error) => {
        console.error("❌ Error en la respuesta del backend:", error);
        this.successMessage = '';
        this.errorMessage = error.error?.message || 'No se pudo modificar el anuncio.';
      }
    });
  }

  onCancel(): void {
    console.log("🔄 Redirigiendo a:", this.returnUrl);
    this.router.navigateByUrl(this.returnUrl);
  }
}