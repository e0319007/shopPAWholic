import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { CreateAdvertisementPageRoutingModule } from './create-advertisement-routing.module';

import { CreateAdvertisementPage } from './create-advertisement.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    CreateAdvertisementPageRoutingModule
  ],
  declarations: [CreateAdvertisementPage]
})
export class CreateAdvertisementPageModule {}
