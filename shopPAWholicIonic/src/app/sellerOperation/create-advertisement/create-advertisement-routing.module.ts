import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { CreateAdvertisementPage } from './create-advertisement.page';

const routes: Routes = [
  {
    path: '',
    component: CreateAdvertisementPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class CreateAdvertisementPageRoutingModule {}
