import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { CreateNewListingPage } from './create-new-listing.page';

const routes: Routes = [
  {
    path: '',
    component: CreateNewListingPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class CreateNewListingPageRoutingModule {}
