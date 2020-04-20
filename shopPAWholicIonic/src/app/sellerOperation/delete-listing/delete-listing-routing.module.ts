import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { DeleteListingPage } from './delete-listing.page';

const routes: Routes = [
  {
    path: '',
    component: DeleteListingPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class DeleteListingPageRoutingModule {}
