import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { RegisterPage } from './register.page';

const routes: Routes = [
  {
    path: '',
    component: RegisterPage
  },  {
    path: 'register-customer',
    loadChildren: () => import('./register-customer/register-customer.module').then( m => m.RegisterCustomerPageModule)
  },
  {
    path: 'register-seller',
    loadChildren: () => import('./register-seller/register-seller.module').then( m => m.RegisterSellerPageModule)
  }

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class RegisterPageRoutingModule {}
