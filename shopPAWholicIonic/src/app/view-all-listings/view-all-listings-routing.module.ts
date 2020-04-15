import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { ViewAllListingsPage } from './view-all-listings.page';

const routes: Routes = [
  {
    path: '',
    component: ViewAllListingsPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ViewAllListingsPageRoutingModule {}
