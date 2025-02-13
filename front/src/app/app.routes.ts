import { Routes } from '@angular/router';
import { AdvertisementListComponent } from './modules/advertisement/pages/advertisement-list/advertisement-list.component';
import { LoginComponent } from './modules/user/pages/login/login.component';
import { RegisterComponent } from './modules/user/pages/register/register.component';
import { CreateAdvertisementComponent } from './modules/advertisement/pages/create-advertisement/create-advertisement.component'; 

export const routes: Routes = [
  { path: '', redirectTo: 'advertisements', pathMatch: 'full' },
  { path: 'advertisements', component: AdvertisementListComponent },
  { path: 'advertisements/create', component: CreateAdvertisementComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
];
