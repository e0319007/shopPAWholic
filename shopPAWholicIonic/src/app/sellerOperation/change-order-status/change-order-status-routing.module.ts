import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { ChangeOrderStatusPage } from './change-order-status.page';

const routes: Routes = [
  {
    path: '',
    component: ChangeOrderStatusPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ChangeOrderStatusPageRoutingModule {}
