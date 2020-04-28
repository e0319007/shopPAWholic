import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { ViewReviewsByListingPage } from './view-reviews-by-listing.page';

const routes: Routes = [
  {
    path: '',
    component: ViewReviewsByListingPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ViewReviewsByListingPageRoutingModule {}
