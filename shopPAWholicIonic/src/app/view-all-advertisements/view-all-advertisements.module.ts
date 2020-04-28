import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { ViewAllAdvertisementsPageRoutingModule } from './view-all-advertisements-routing.module';

import { ViewAllAdvertisementsPage } from './view-all-advertisements.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    ViewAllAdvertisementsPageRoutingModule
  ],
  declarations: [ViewAllAdvertisementsPage]
})
export class ViewAllAdvertisementsPageModule {}
