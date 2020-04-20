import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { ViewDeliveryDetailsPage } from './view-delivery-details.page';

const routes: Routes = [
  {
    path: '',
    component: ViewDeliveryDetailsPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ViewDeliveryDetailsPageRoutingModule {}
