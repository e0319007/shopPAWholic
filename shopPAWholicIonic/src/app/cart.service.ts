import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { HttpClient, HttpResponse, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { UtilityService } from './utility.service';
import { catchError } from 'rxjs/operators';
import { Cart } from './cart';
import { Listing } from './listing';
import { CartItem } from './cart-item';
import { DeliveryDetail } from './delivery-detail';
import { OrderEntity } from './order-entity';

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
    return JSON.parse(sessionStorage.getItem('cartItems'));
  }


  initialiseCart() {
    let cart: Cart = new Cart();
    console.log("initialising cart")
    this.retrieveCartByCustomerId().subscribe(
      response => {
        cart = response.cart;
        console.log("initialised cart*****")
        sessionStorage.cartId = cart.cartId;
        let cartItems: CartItem[] = new Array();
        let exist: boolean = false;
        for(var i = 0; i < cart.listings.length; i++) {
          for(var j = 0; j < cartItems.length; j++) {
            if(cart.listings[i].listingId == cartItems[j].listing.listingId) {
              cartItems[j].quantity = cartItems[j].quantity + 1; 
              exist = true;
              break;
            }
          }
          if (!exist) {
            cartItems.push(new CartItem(cart.listings[i], 1));
          }
        }
        sessionStorage.setItem ('originalCart', JSON.stringify(cart));
        sessionStorage.setItem('cartItems', JSON.stringify(cartItems));
        console.log("initialised cart with : " )
        console.log('===========')
        
      },
      error => {
        console.log("unable to retrieve cart...: " + error)
      }
    )
    
  }

  retrieveCartByCustomerId(): Observable<any> {
    console.log("retrieving shopping cart")
   return this.httpClient.get<any>(this.baseUrl + "/retrieveCart?email=" + 
   this.utilityService.getEmail() + "&password=" + this.utilityService.getPassword()).pipe(
     catchError(this.handleError)
   );
 }

 saveCartToDatabase(): Observable<any> {
   let listings: Listing[] = new Array();
   let cartItems: CartItem[] = JSON.parse(sessionStorage.getItem('cartItems'));
   for (var i = 0; i < cartItems.length; i++) {
     for (var j = 0; j < cartItems[i].quantity; i++) {
      listings.push(cartItems[i].listing);
     }
   }
   
   let totalPrice: number = 0;
   for(let l of listings) {
     totalPrice += l.unitPrice;
   }

   let cart: Cart = JSON.parse(sessionStorage.getItem('originalCart'));
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

 afterCheckoutInitCart() {
  let cart: Cart = JSON.parse(sessionStorage.getItem('originalCart'));
  cart.listings = new Array();
  let cartItems: CartItem[] = new Array();
  sessionStorage.setItem ('originalCart', JSON.stringify(cart));
  sessionStorage.setItem('cartItems', JSON.stringify(cartItems));

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
