import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { RegisterSellerPageRoutingModule } from './register-seller-routing.module';

import { RegisterSellerPage } from './register-seller.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    RegisterSellerPageRoutingModule
  ],
  declarations: [RegisterSellerPage]
})
export class RegisterSellerPageModule {}
