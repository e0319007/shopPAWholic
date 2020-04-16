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
    path:'viewAllOrders', 
    loadChildren: './view-all-orders/view-all-orders.module#ViewAllOrdersPageModule', canActivate: [AuthGuard]
  },
  { 
    path: 'viewAllListings', 
    loadChildren: './view-all-listings/view-all-listings.module#ViewAllListingsPageModule', canActivate: [AuthGuard] 
  },
  {
    path: 'viewListingDetails/:listingId', 
    loadChildren: './view-listing-details/view-listing-details.module#ViewListingDetailsPageModule', canActivate: [AuthGuard]
  },
  {
    path: 'viewListingDetails', 
    loadChildren: './view-listing-details/view-listing-details.module#ViewListingDetailsPageModule', canActivate: [AuthGuard] 
  },
  {
    path: 'delete-listing',
    loadChildren: () => import('./sellerOperation/delete-listing/delete-listing.module').then( m => m.DeleteListingPageModule)
  },
  {
    path: 'update-listing',
    loadChildren: () => import('./sellerOperation/update-listing/update-listing.module').then( m => m.UpdateListingPageModule)
  },
  {
    path: 'createNewListing',
    loadChildren:'./sellerOperation/create-new-listing/create-new-listing.module#CreateNewListingPageModule', canActivate:[AuthGuard]
  },
  {
    path: 'view-cart',
    loadChildren: () => import('./customerOperation/view-cart/view-cart.module').then( m => m.ViewCartPageModule)
  },

 
 
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes, { preloadingStrategy: PreloadAllModules })
  ],
  exports: [RouterModule]
})
export class AppRoutingModule {}
