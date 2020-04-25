import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { UpdateListingPage } from './update-listing.page';

const routes: Routes = [
  {
    path: '',
    component: UpdateListingPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class UpdateListingPageRoutingModule {}
