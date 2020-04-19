import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { ViewBillingDetailsPage } from './view-billing-details.page';

const routes: Routes = [
  {
    path: '',
    component: ViewBillingDetailsPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ViewBillingDetailsPageRoutingModule {}
