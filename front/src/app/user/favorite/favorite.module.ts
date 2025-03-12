import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FavoriteListComponent } from './pages/favorite-list/favorite-list.component';

@NgModule({
  declarations: [FavoriteListComponent], 
  imports: [CommonModule, FavoriteListComponent], 
  exports: [FavoriteListComponent]
})
export class FavoritesModule {}