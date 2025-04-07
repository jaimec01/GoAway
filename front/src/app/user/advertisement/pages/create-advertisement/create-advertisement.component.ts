import { Component, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router, ActivatedRoute } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-create-advertisement',
  templateUrl: './create-advertisement.component.html',
  styleUrls: ['./create-advertisement.component.scss'],
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
})
export class CreateAdvertisementComponent {
  @ViewChild('fileInput') fileInput: any;
  adForm: FormGroup;
  errorMessage: string = '';
  returnUrl: string = '/advertisements';
  characterCount = 0;
  titleCharacterCount = 0;
  fileError: string = '';
  selectedFiles: File[] = [];
  isDragging: boolean = false;

  categoryTranslations: { [key: string]: string } = {
    chair: 'Silla',
    table: 'Mesa',
    TV: 'Televisión',
    chestOfDrawers: 'Cómoda',
    sofa: 'Sofá',
    bookshelf: 'Estantería',
    other: 'Otro',
  };

  conditionTranslations: { [key: string]: string } = {
    Good: 'Buena',
    Fair: 'Regular',
    Excellent: 'Excelente',
  };

  advertisementCategories = Object.keys(this.categoryTranslations);
  advertisementConditions = Object.keys(this.conditionTranslations);

  constructor(
    private fb: FormBuilder,
    private http: HttpClient,
    private router: Router,
    private route: ActivatedRoute
  ) {
    this.adForm = this.fb.group({
      title: ['', Validators.required],
      description: ['', Validators.required],
      category: ['', Validators.required],
      condition: ['', Validators.required],
      price: [0, [Validators.required, Validators.min(0)]],
    });

    this.route.queryParams.subscribe((params) => {
      if (params['returnUrl']) {
        this.returnUrl = params['returnUrl'];
      }
    });

    this.updateTitleCharacterCount();
  }

  updateTitleCharacterCount(): void {
    const title = this.adForm.get('title')?.value || '';
    this.titleCharacterCount = title.length;
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
    if (this.selectedFiles.length + files.length > 6) {
      this.fileError = 'No puedes subir más de 6 imágenes.';
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

  removeImage(index: number): void {
    this.selectedFiles.splice(index, 1);
    this.fileError = '';
  }

  async onSubmit(): Promise<void> {
    if (this.adForm.valid) {
      const token = sessionStorage.getItem('token');
      if (!token) {
        this.errorMessage = 'Error de autenticación. Inicia sesión.';
        return;
      }

      const headers = new HttpHeaders({
        Authorization: `Bearer ${token}`,
      });

      const formData = new FormData();
      const advertisementData = {
        title: this.adForm.get('title')?.value,
        description: this.adForm.get('description')?.value,
        category: this.adForm.get('category')?.value,
        condition: this.adForm.get('condition')?.value,
        price: this.adForm.get('price')?.value,
      };
      formData.append('advertisement', new Blob([JSON.stringify(advertisementData)], {
        type: 'application/json',
      }));

      // Comprimir y añadir imágenes si las hay
      if (this.selectedFiles.length > 0) {
        try {
          const compressedFiles = await Promise.all(this.selectedFiles.map(file => this.compressImage(file)));
          compressedFiles.forEach((file, i) => {
            formData.append('photos', file, this.selectedFiles[i].name);
          });
        } catch (error) {
          this.errorMessage = 'Error al comprimir las imágenes.';
          console.error('Compression error:', error);
          return;
        }
      }

      // Enviar una sola solicitud
      this.http.post('/api/advertisements', formData, { headers }).subscribe({
        next: () => {
          this.router.navigateByUrl(this.returnUrl);
        },
        error: (error) => {
          console.log('Error del backend:', error);
          this.errorMessage = error.error?.message || 'No se pudo crear el anuncio.';
        },
      });
    } else {
      if (this.adForm.get('price')?.value < 0) {
        this.errorMessage = 'El precio debe ser positivo (0 o mayor).';
      } else {
        this.errorMessage = 'Por favor, completa todos los campos obligatorios.';
      }
    }
  }

  onCancel(): void {
    this.router.navigateByUrl(this.returnUrl);
  }

  compressImage(file: File): Promise<Blob> {
    return new Promise((resolve) => {
      const reader = new FileReader();
      reader.onload = (event: any) => {
        const img = new Image();
        img.src = event.target.result;
        img.onload = () => {
          const canvas = document.createElement('canvas');
          const MAX_WIDTH = 800;
          const scaleSize = MAX_WIDTH / img.width;
          canvas.width = MAX_WIDTH;
          canvas.height = img.height * scaleSize;
          const ctx = canvas.getContext('2d');
          ctx?.drawImage(img, 0, 0, canvas.width, canvas.height);
          canvas.toBlob((blob) => {
            if (blob) resolve(blob);
          }, 'image/jpeg', 0.7); // calidad 70%
        };
      };
      reader.readAsDataURL(file);
    });
  }
}