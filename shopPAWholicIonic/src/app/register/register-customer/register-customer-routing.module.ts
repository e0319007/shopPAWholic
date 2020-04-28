import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { RegisterCustomerPage } from './register-customer.page';

const routes: Routes = [
  {
    path: '',
    component: RegisterCustomerPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class RegisterCustomerPageRoutingModule {}
