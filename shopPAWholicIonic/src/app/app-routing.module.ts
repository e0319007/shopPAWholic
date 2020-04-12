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
  { path: 'viewAllListings', loadChildren: './view-all-listings/view-all-listings.module#ViewAllListingsPageModule', canActivate: [AuthGuard] },
  { path: 'createNewListing', loadChildren: './create-new-listing/create-new-listing.module#CreateNewListingPageModule', canActivate: [AuthGuard] },
  { path: 'viewOrders', loadChildren: './view-orders/view-orders.module#ViewOrdersPageModule', canActivate: [AuthGuard] },
  { path: 'viewShoppingCart', loadChildren: './view-shopping-cart/view-shopping-cart.module#ViewShoppingCartPageModule', canActivate: [AuthGuard] },
  { path: 'viewAdvertisements', loadChildren: './view-advertisements/view-advertisements.module#ViewAdvertisementsPageModule', canActivate: [AuthGuard] },
  { path: 'viewReviews', loadChildren: './view-reviews/view-reviews.module#ViewReviewsPageModule', canActivate: [AuthGuard] }
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes, { preloadingStrategy: PreloadAllModules })
  ],
  exports: [RouterModule]
})
export class AppRoutingModule {}
