import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { ViewBillingDetailPage } from './view-billing-detail.page';

const routes: Routes = [
  {
    path: '',
    component: ViewBillingDetailPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ViewBillingDetailPageRoutingModule {}
