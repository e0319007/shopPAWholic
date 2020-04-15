import { NgModule } from '@angular/core';
import { PreloadAllModules, RouterModule, Routes } from '@angular/router';

import { AuthGuard } from './guards/auth.guard';

const routes: Routes = [
  {
    path: '',
    redirectTo: 'home',
    pathMatch: 'full'
  },  
  {
    path: 'home',
    loadChildren: () => import('./home/home.module').then( m => m.HomePageModule)
  },   {
    path: 'view-billing-detail',
    loadChildren: () => import('./view-billing-detail/view-billing-detail.module').then( m => m.ViewBillingDetailPageModule)
  },
  {
    path: 'view-all-orders',
    loadChildren: () => import('./view-all-orders/view-all-orders.module').then( m => m.ViewAllOrdersPageModule)
  },
  {
    path: 'view-all-listings',
    loadChildren: () => import('./view-all-listings/view-all-listings.module').then( m => m.ViewAllListingsPageModule)
  },
  {
    path: 'view-listing-details',
    loadChildren: () => import('./view-listing-details/view-listing-details.module').then( m => m.ViewListingDetailsPageModule)
  },
 
 
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes, { preloadingStrategy: PreloadAllModules })
  ],
  exports: [RouterModule]
})
export class AppRoutingModule {}
