import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { UpdateListingPageRoutingModule } from './update-listing-routing.module';

import { UpdateListingPage } from './update-listing.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    UpdateListingPageRoutingModule
  ],
  declarations: [UpdateListingPage]
})
export class UpdateListingPageModule {}
