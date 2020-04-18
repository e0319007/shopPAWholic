import { Component, OnInit } from '@angular/core';
import { CartService } from 'src/app/cart.service';
import { Router } from '@angular/router';
import { CartItem } from 'src/app/cart-item';
import { Listing } from 'src/app/listing';
import { DeliveryDetail } from 'src/app/delivery-detail';
import { DeliveryDetailService } from 'src/app/delivery-detail.service';
import { OrderEntity } from 'src/app/order-entity';
import { Seller } from 'src/app/seller';
import { Cart } from 'src/app/cart';

@Component({
  selector: 'app-view-cart',
  templateUrl: './view-cart.page.html',
  styleUrls: ['./view-cart.page.scss'],
})
export class ViewCartPage implements OnInit {

  cartItems: CartItem[];
  totalPrice: number;
  emptyCart: boolean = true;
  
  


  constructor(private cartService: CartService,
              private router: Router,
              private deliveryDetailService: DeliveryDetailService) {this.totalPrice = 0;}

  ngOnInit() {
    let cart: Cart = JSON.parse(sessionStorage.getItem('originalCart'))
    console.log("Cart component: listing in cart: " + cart.listings.length);
    this.refreshCart();
    console.log("Cart component: cartItems.length: " + this.cartItems.length);
  }

  ionViewWillEnter(){
   this.refreshCart;
  }

  ionViewCanEnter(){
   this.refreshCart;
  }

  refreshCart() {
    this.cartItems = JSON.parse(sessionStorage.getItem('cartItems'));
    console.log("Cart Component: cartItems: " + this.cartItems.length);
    for (let item of this.cartItems) {
      this.totalPrice += item.quantity * item.listing.unitPrice;
    }
    if (this.cartItems == null || this.cartItems.length == 0) {
      this.emptyCart = true;
    } else {
      this.emptyCart = false;
    }
  }

  refreshPrice() {
    for (let item of this.cartItems) {
      this.totalPrice += item.quantity * item.listing.unitPrice;
    }
  }

  toListing(listingId) {
    this.router.navigate(["/viewListingDetails/" + listingId]);
  }

  removeListingFromCart(listingId: string) {
    console.log("remove item of id: " + listingId);
    let cartItems: CartItem[] = JSON.parse(sessionStorage.getItem('cartItems'));
    let longlistingId = parseInt(listingId);
    console.log("cartitems length before: " + cartItems.length)
    cartItems = cartItems.filter(x => x.listing.listingId !== longlistingId);
    console.log("cartitems length after: " + cartItems.length)
    sessionStorage.setItem('cartItems', JSON.stringify(cartItems));
    this.refreshCart();
  }

  checkoutDetails() {
    this.router.navigate(["/customerOperation/checkOutPage"]);
  }

  shop() {
    this.router.navigate(["/viewAllListings"]);
  }
}
