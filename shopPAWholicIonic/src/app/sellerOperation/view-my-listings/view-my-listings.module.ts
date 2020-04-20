import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { ViewMyListingsPageRoutingModule } from './view-my-listings-routing.module';

import { ViewMyListingsPage } from './view-my-listings.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    ViewMyListingsPageRoutingModule
  ],
  declarations: [ViewMyListingsPage]
})
export class ViewMyListingsPageModule {}
