import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { ViewAllListingsPageRoutingModule } from './view-all-listings-routing.module';

import { ViewAllListingsPage } from './view-all-listings.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    ViewAllListingsPageRoutingModule
  ],
  declarations: [ViewAllListingsPage]
})
export class ViewAllListingsPageModule {}
