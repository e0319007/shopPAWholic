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
    path: 'sellerOperation/deleteListing/:listingId',
    loadChildren: './sellerOperation/delete-listing/delete-listing.module#DeleteListingPageModule',canActivate: [AuthGuard]

  },
  {
    path: 'sellerOperation/updateListing/:listingId',
    loadChildren: './sellerOperation/update-listing/update-listing.module#UpdateListingPageModule', canActivate: [AuthGuard]
  },
  {
    path: 'sellerOperation/createNewListing',
    loadChildren:'./sellerOperation/create-new-listing/create-new-listing.module#CreateNewListingPageModule', canActivate:[AuthGuard]
  },
  {
    path: 'sellerOperation/createNewAdvertisement',
    loadChildren:'./sellerOperation/create-advertisement/create-advertisement.module#CreateAdvertisementPageModule', canActivate:[AuthGuard]
  },
  {
    path: 'customerOperation/viewCart',
    loadChildren: './customerOperation/view-cart/view-cart.module#ViewCartPageModule', canActivate:[AuthGuard]
  },
  {
    path: 'customerOperation/checkOutPage',
    loadChildren: './customerOperation/check-out/check-out.module#CheckOutPageModule', canActivate:[AuthGuard]
  },
  {
    path: 'viewOrderDetails/:orderId',
    loadChildren: './view-order-details/view-order-details.module#ViewOrderDetailsPageModule', canActivate:[AuthGuard]
  },
  {
    path: 'viewDeliveryDetails/:orderId',
    loadChildren:'./view-delivery-details/view-delivery-details.module#ViewDeliveryDetailsPageModule', canActivate:[AuthGuard]
  },
  {
    path: 'viewBillingDetails/:orderId',
    loadChildren: './customerOperation/view-billing-details/view-billing-details.module#ViewBillingDetailsPageModule', canActivate:[AuthGuard]
  },
  {
    path: 'sellerOperation/changeOrderStatus/:orderId',
    loadChildren: './sellerOperation/change-order-status/change-order-status.module#ChangeOrderStatusPageModule', canActivate:[AuthGuard]
  },
  {
    path:'viewReviewsByListing/:listingId',
    loadChildren: './view-reviews-by-listing/view-reviews-by-listing.module#ViewReviewsByListingPageModule', canActivate:[AuthGuard]
  },
  {
    path:'viewAllAdvertisements',
    loadChildren: './view-all-advertisements/view-all-advertisements.module#ViewAllAdvertisementsPageModule', canActivate: [AuthGuard]
  },
  {
    path: 'customerOperation/createNewReview/:listingId',
    loadChildren: './customerOperation/create-new-review/create-new-review.module#CreateNewReviewPageModule', canActivate: [AuthGuard]
  },
  { path: 'login', loadChildren: './login/login.module#LoginPageModule' },
  { path: 'register', loadChildren: './register/register.module#RegisterPageModule' },
  { 
    path : 'register/registerCustomer',
    loadChildren: './register/register-customer/register-customer.module#RegisterCustomerPageModule'
  },
  { 
    path : 'register/registerSeller',
    loadChildren: './register/register-seller/register-seller.module#RegisterSellerPageModule'
  },
  
  {
    path: 'sellerOperation/createNewTag',
    loadChildren: './sellerOperation/create-new-tag/create-new-tag.module#CreateNewTagPageModule', canActivate:[AuthGuard]
  },
  {
    path: 'sellerOperation/viewMyListings',
    loadChildren: './sellerOperation/view-my-listings/view-my-listings.module#ViewMyListingsPageModule', canActivate:[AuthGuard]
  },
  {
    path: 'customerOperation/createOrderSuccessPage',
    loadChildren: './customerOperation/create-order-success-page/create-order-success-page.module#CreateOrderSuccessPagePageModule', canActivate:[AuthGuard]
  },
  {
    path: 'sellerOperation/viewBillingDetails',
    loadChildren: './sellerOperation/view-billing-details/view-billing-details.module#ViewBillingDetailsPageModule', canActivate:[AuthGuard]
  },
  {
    path: 'create-advertisement',
    loadChildren: () => import('./sellerOperation/create-advertisement/create-advertisement.module').then( m => m.CreateAdvertisementPageModule)
  },
  {
    path: 'create-new-review',
    loadChildren: () => import('./customerOperation/create-new-review/create-new-review.module').then( m => m.CreateNewReviewPageModule)
  },
  {
    path: 'view-reviews-by-listing',
    loadChildren: () => import('./view-reviews-by-listing/view-reviews-by-listing.module').then( m => m.ViewReviewsByListingPageModule)
  },
  {
    path: 'view-all-advertisements',
    loadChildren: () => import('./view-all-advertisements/view-all-advertisements.module').then( m => m.ViewAllAdvertisementsPageModule)
  },






 
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes, { preloadingStrategy: PreloadAllModules })
  ],
  exports: [RouterModule]
})
export class AppRoutingModule {}
