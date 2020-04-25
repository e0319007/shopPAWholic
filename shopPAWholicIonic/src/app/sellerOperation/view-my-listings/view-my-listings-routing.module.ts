import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { ViewMyListingsPage } from './view-my-listings.page';

const routes: Routes = [
  {
    path: '',
    component: ViewMyListingsPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ViewMyListingsPageRoutingModule {}
