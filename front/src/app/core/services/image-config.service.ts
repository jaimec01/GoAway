// src/app/core/services/image-config.service.ts
import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class ImageConfigService {
  imageBaseUrl: string;

  constructor() {
    this.imageBaseUrl = environment.imageBaseUrl;
  }
}