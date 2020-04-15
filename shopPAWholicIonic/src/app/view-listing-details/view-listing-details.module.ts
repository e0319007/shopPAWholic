import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { ViewListingDetailsPageRoutingModule } from './view-listing-details-routing.module';

import { ViewListingDetailsPage } from './view-listing-details.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    ViewListingDetailsPageRoutingModule
  ],
  declarations: [ViewListingDetailsPage]
})
export class ViewListingDetailsPageModule {}
