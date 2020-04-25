import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { CreateOrderSuccessPagePage } from './create-order-success-page.page';

const routes: Routes = [
  {
    path: '',
    component: CreateOrderSuccessPagePage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class CreateOrderSuccessPagePageRoutingModule {}
