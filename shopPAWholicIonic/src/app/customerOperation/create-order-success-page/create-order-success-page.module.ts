import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { CreateOrderSuccessPagePageRoutingModule } from './create-order-success-page-routing.module';

import { CreateOrderSuccessPagePage } from './create-order-success-page.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    CreateOrderSuccessPagePageRoutingModule
  ],
  declarations: [CreateOrderSuccessPagePage]
})
export class CreateOrderSuccessPagePageModule {}
