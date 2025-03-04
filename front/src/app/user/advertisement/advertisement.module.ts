import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { AdvertisementListComponent } from './pages/advertisement-list/advertisement-list.component';

@NgModule({
  declarations: [AdvertisementListComponent], 
  imports: [CommonModule, RouterModule], 
  exports: [AdvertisementListComponent]
})
export class AdvertisementModule {}
