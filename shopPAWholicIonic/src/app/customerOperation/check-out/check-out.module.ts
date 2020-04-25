import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { CheckOutPageRoutingModule } from './check-out-routing.module';

import { CheckOutPage } from './check-out.page';

@NgModule({
  imports: [
    CommonModule,
    ReactiveFormsModule, 
    FormsModule,
    IonicModule,
    CheckOutPageRoutingModule
  ],
  declarations: [CheckOutPage]
})
export class CheckOutPageModule {}
