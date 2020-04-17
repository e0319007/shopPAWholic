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
  
  


  constructor(private cartService: CartService,
              private router: Router,
              private deliveryDetailService: DeliveryDetailService) {this.totalPrice = 0;}

  ngOnInit() {
    //deletelater
    this.cartService.initialiseCart;
    console.log("initialised cart");

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
  }

  removeListingFromCart(cartItem: CartItem) {
    let cartItems: CartItem[] = JSON.parse(sessionStorage.getItem('cartItems'));
    let quantityLeft: number;
    let itemChanged: CartItem;

    for (var i = 0; i < cartItems.length; i++) {
      if(cartItems[i].listing.name == cartItem.listing.name) {
        cartItem[i].quantity = cartItem[i].quantity - cartItem.quantity;
        quantityLeft = cartItem[i].quantity;
        itemChanged = cartItem[i];
        break;
      }
    }
    if(quantityLeft == 0) {
      cartItems = cartItems.filter(obj => obj !== itemChanged);
    }
    sessionStorage.setItem('cartItem', JSON.stringify(cartItems));
  }

  checkoutDetails() {
    this.router.navigate(["/customerOperation/checkOutPage"]);
  }
}
