import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { HttpClient, HttpResponse, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { UtilityService } from './utility.service';
import { catchError } from 'rxjs/operators';
import { Cart } from './cart';
import { Listing } from './listing';
import { CartItem } from './cart-item';

const httpOptions = {
	headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class CartService {

  baseUrl: string;

  constructor(private httpClient: HttpClient, private utilityService: UtilityService) {
    this.baseUrl = this.utilityService.getRootPath() + 'Cart';
  }

  getCartItems(): CartItem {
    return JSON.parse(sessionStorage.cartItems);
  }

  addListingToCart(cartItem: CartItem) {
    let cartItems: CartItem[] = JSON.parse(sessionStorage.cartItems);
    let exist: boolean = false;
    for (var i = 0; i < cartItems.length; i++) {
      if(cartItems[i].listing.name == cartItem.listing.name) {
        cartItems[i].quantity + cartItem.quantity;
        exist = true;
        break;
      }
    }
    if (!exist) {
      cartItems.push(cartItem);
    }
    sessionStorage.cartItems = JSON.stringify(cartItems);
  }

  removeListingFromCart(cartItem: CartItem) {
    let cartItems: CartItem[] = JSON.parse(sessionStorage.cartItems);
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

    sessionStorage.cartItems = JSON.stringify(cartItems);
  }

  initialiseCart() {
    let cart: Cart = new Cart();
    this.retrieveCartByCustomerId().subscribe(
      response => {
        cart = response.cart;
      },
      error => {
        console.log("unable to retrieve cart...")
      }
    )
    sessionStorage.cartId = cart.cartId;
    let cartItems: CartItem[] = new Array();
    let exist: boolean = false;
    for(var i = 0; i < cart.listings.length; i++) {
      for(var j = 0; j < cartItems.length; j++) {
        if(cart.listings[i] == cartItems[j].listing) {
          cartItems[j].quantity = cartItems[j].quantity + 1; 
          exist = true;
        }
      }
      if (!exist) {
        cartItems.push(new CartItem(cart.listings[i], 1));
      }
    }
    sessionStorage.originalCart = JSON.stringify(cart);
    sessionStorage.cartItems = JSON.stringify(cartItems);
  }

  retrieveCartByCustomerId(): Observable<any> {
   return this.httpClient.get<any>(this.baseUrl + "/retrieveCart?email=" + 
   this.utilityService.getEmail() + "&password=" + this.utilityService.getPassword).pipe(
     catchError(this.handleError)
   );
 }
  
 saveCartToDatabase(): Observable<any> {
   
   let listings: Listing[] = new Array();
   let cartItems: CartItem[] = JSON.parse(sessionStorage.cartItems);
   for (var i = 0; i < cartItems.length; i++) {
     for (var j = 0; j < cartItems[i].quantity; i++) {
      listings.push(cartItems[i].listing);
     }
   }
   
   let totalPrice: number = 0;
   for(let l of listings) {
     totalPrice += l.unitPrice;
   }

   let cart: Cart = sessionStorage.originalCart;
   cart.listings = listings;
   cart.totalPrice = totalPrice;
   cart.totalQuantity = listings.length;
   
   let cartUpdateReq = {
     "email": this.utilityService.getEmail(),
     "password": this.utilityService.getPassword(),
     "cart": cart,
   };
   return this.httpClient.post<any>(this.baseUrl, cartUpdateReq, httpOptions).pipe(catchError(this.handleError));
 }

 handleError(error: HttpErrorResponse) {
   let errorMessage: string = "";
   if (error.error instanceof ErrorEvent) {
     errorMessage = "An unknown error has occured: " + error.error.message;
    } else {
      errorMessage = "An HTTP error has occured: " + `HTTP ${error.status}: ${error.error.message}`;
    } 
    console.error(errorMessage);
    return throwError(errorMessage);
 }
}
