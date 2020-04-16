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
  },   
  {
    path: 'view-billing-detail',
    loadChildren: () => import('./view-billing-detail/view-billing-detail.module').then( m => m.ViewBillingDetailPageModule)
  },
  {
    path:'viewAllOrders', loadChildren: './view-all-orders/view-all-orders.module#ViewAllOrdersPageModule', canActivate: [AuthGuard]
  },
  { 
    path: 'viewAllListings', loadChildren: './view-all-listings/view-all-listings.module#ViewAllListingsPageModule', canActivate: [AuthGuard] 
  },
  {
    path: 'viewListingDetails/:listingId', loadChildren: './view-listing-details/view-listing-details.module#ViewListingDetailsPageModule', canActivate: [AuthGuard]
  },
  {
    path: 'viewListingDetails', loadChildren: './view-listing-details/view-listing-details.module#ViewListingDetailsPageModule', canActivate: [AuthGuard] 
  },
 
 
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes, { preloadingStrategy: PreloadAllModules })
  ],
  exports: [RouterModule]
})
export class AppRoutingModule {}
