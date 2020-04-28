import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { ViewReviewsByListingPageRoutingModule } from './view-reviews-by-listing-routing.module';

import { ViewReviewsByListingPage } from './view-reviews-by-listing.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    ViewReviewsByListingPageRoutingModule
  ],
  declarations: [ViewReviewsByListingPage]
})
export class ViewReviewsByListingPageModule {}
