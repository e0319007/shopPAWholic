import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { DeleteListingPageRoutingModule } from './delete-listing-routing.module';

import { DeleteListingPage } from './delete-listing.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    DeleteListingPageRoutingModule
  ],
  declarations: [DeleteListingPage]
})
export class DeleteListingPageModule {}
