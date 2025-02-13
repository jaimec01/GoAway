import { bootstrapApplication } from '@angular/platform-browser';
import { provideRouter } from '@angular/router';
import { AppComponent } from './app/app.component';
import { importProvidersFrom } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { routes } from './app/app.routes'; // Usa las rutas desde un solo lugar
import { appConfig } from './app/app.config'; // Agregado para incluir la configuración global

bootstrapApplication(AppComponent, {
  providers: [
    provideRouter(routes),
    importProvidersFrom(HttpClientModule),
    ...appConfig.providers 
  ],
}).catch(err => console.error('Error en bootstrap:', err));
