import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { ViewBillingDetailsPageRoutingModule } from './view-billing-details-routing.module';

import { ViewBillingDetailsPage } from './view-billing-details.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    ViewBillingDetailsPageRoutingModule
  ],
  declarations: [ViewBillingDetailsPage]
})
export class ViewBillingDetailsPageModule {}
