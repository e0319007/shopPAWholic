import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { CreateNewTagPage } from './create-new-tag.page';

const routes: Routes = [
  {
    path: '',
    component: CreateNewTagPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class CreateNewTagPageRoutingModule {}
