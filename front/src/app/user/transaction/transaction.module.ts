import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { TransactionListComponent } from './pages/transaction-list/transaction-list.component';


@NgModule({
  declarations: [TransactionListComponent], 
  imports: [CommonModule, RouterModule],
  exports: [TransactionListComponent]
})
export class TransactionModule { }


