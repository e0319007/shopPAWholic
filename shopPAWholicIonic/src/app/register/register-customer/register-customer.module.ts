import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { RegisterCustomerPageRoutingModule } from './register-customer-routing.module';

import { RegisterCustomerPage } from './register-customer.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    RegisterCustomerPageRoutingModule
  ],
  declarations: [RegisterCustomerPage]
})
export class RegisterCustomerPageModule {}
