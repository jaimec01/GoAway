import { Routes } from '@angular/router';
import { AdvertisementListComponent } from './modules/advertisement/pages/advertisement-list/advertisement-list.component';
import { LoginComponent } from './modules/user/pages/login/login.component';
import { RegisterComponent } from './modules/user/pages/register/register.component';
import { CreateAdvertisementComponent } from './modules/advertisement/pages/create-advertisement/create-advertisement.component'; 
import { ModifyAdvertisementComponent } from './modules/advertisement/pages/modify-advertisement/modify-advertisement.component'; 
import { MyAdsComponent } from './modules/advertisement/pages/my-ads/my-ads.component';
import { CreateTransactionComponent } from './modules/transaction/pages/create-transaction/create-transaction.component';

export const routes: Routes = [
  { path: '', redirectTo: 'advertisements', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'advertisements', component: AdvertisementListComponent },
  { path: 'advertisements/new', component: CreateAdvertisementComponent },
  { path: 'advertisements/edit/:id', component: ModifyAdvertisementComponent },
  { path: 'advertisements/my-ads', component: MyAdsComponent },
  { path: 'transaction/new/:id', component: CreateTransactionComponent },
];
