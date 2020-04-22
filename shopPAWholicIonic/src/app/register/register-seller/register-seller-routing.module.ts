import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { RegisterSellerPage } from './register-seller.page';

const routes: Routes = [
  {
    path: '',
    component: RegisterSellerPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class RegisterSellerPageRoutingModule {}
