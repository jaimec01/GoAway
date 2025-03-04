import { Routes } from '@angular/router';
import { AdvertisementListComponent } from './user/advertisement/pages/advertisement-list/advertisement-list.component';
import { LoginComponent } from './user/user/pages/login/login.component';
import { RegisterComponent } from './user/user/pages/register/register.component';
import { CreateAdvertisementComponent } from './user/advertisement/pages/create-advertisement/create-advertisement.component'; 
import { ModifyAdvertisementComponent } from './user/advertisement/pages/modify-advertisement/modify-advertisement.component'; 
import { MyAdsComponent } from './user/advertisement/pages/my-ads/my-ads.component';
import { CreateTransactionComponent } from './user/transaction/pages/create-transaction/create-transaction.component';
import { TransactionListComponent } from './user/transaction/pages/transaction-list/transaction-list.component';
import { FavoriteListComponent } from './user/favorite/pages/favorite-list/favorite-list.component';
import { SingleAdvertisementComponent } from './user/advertisement/pages/single-advertisement/single-advertisement.component';

export const routes: Routes = [
  { path: '', redirectTo: 'advertisements', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'advertisements', component: AdvertisementListComponent },
  { path: 'advertisements/new', component: CreateAdvertisementComponent },
  { path: 'advertisements/edit/:id', component: ModifyAdvertisementComponent },
  { path: 'advertisements/my-ads', component: MyAdsComponent },
  { path: 'transaction/new/:id', component: CreateTransactionComponent },
  { path: 'transaction/my-transactions', component: TransactionListComponent },
  { path: 'advertisements/favorites', component: FavoriteListComponent },
  { path: 'advertisement/:id', component: SingleAdvertisementComponent },
];
