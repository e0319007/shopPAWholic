import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { ViewAllAdvertisementsPage } from './view-all-advertisements.page';

const routes: Routes = [
  {
    path: '',
    component: ViewAllAdvertisementsPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ViewAllAdvertisementsPageRoutingModule {}
