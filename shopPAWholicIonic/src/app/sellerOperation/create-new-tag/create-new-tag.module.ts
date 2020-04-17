import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { CreateNewTagPageRoutingModule } from './create-new-tag-routing.module';

import { CreateNewTagPage } from './create-new-tag.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    CreateNewTagPageRoutingModule
  ],
  declarations: [CreateNewTagPage]
})
export class CreateNewTagPageModule {}
