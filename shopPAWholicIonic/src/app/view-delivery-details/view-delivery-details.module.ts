import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { ViewDeliveryDetailsPageRoutingModule } from './view-delivery-details-routing.module';

import { ViewDeliveryDetailsPage } from './view-delivery-details.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    ViewDeliveryDetailsPageRoutingModule
  ],
  declarations: [ViewDeliveryDetailsPage]
})
export class ViewDeliveryDetailsPageModule {}
