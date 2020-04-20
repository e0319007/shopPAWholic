import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { ChangeOrderStatusPageRoutingModule } from './change-order-status-routing.module';

import { ChangeOrderStatusPage } from './change-order-status.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    ChangeOrderStatusPageRoutingModule
  ],
  declarations: [ChangeOrderStatusPage]
})
export class ChangeOrderStatusPageModule {}
