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

 saveCartToDatabase() {
   let listings: Listing[] = new Array();
   let cartItems: CartItem[] = JSON.parse(sessionStorage.getItem('cartItems'));
  //  for (var i = 0; i < cartItems.length; i++) {
  //   let quantity: number = cartItems[i].quantity;
  //   let cartItemListing : Listing = cartItems[i].listing;
  //    for (var j = 0; j < quantity; i++) {
  //     listings.push(cartItemListing);
  //    }
  //  }
  for(let ci of cartItems) {
    for(var i = 0; i < ci.quantity; i++) {
      listings.push(ci.listing);
    }
  }
   
   let totalPrice: number = 0;
   for(let l of listings) {
     totalPrice += l.unitPrice;
   }
   console.log("Total Price will be: " + totalPrice);

   let cart: Cart = JSON.parse(sessionStorage.getItem('originalCart'));
   cart.listings = listings;
   cart.totalPrice = totalPrice;
   cart.totalQuantity = listings.length;

   console.log("cart listings: " + cart.listings.length);
   console.log("cart totalPrice: " + cart.totalPrice);
   console.log("cart totalQuantity: " + cart.totalQuantity);
   
   this.save(cart).subscribe(
     response => {
       console.log("saved :))))))")
     },
     error => {
       console.log("GOT ERROR :(((");
     }
   )
   
   }

 save(cart: Cart): Observable<any> {
  let cartUpdateReq = {
    "email": this.utilityService.getEmail(),
    "password": this.utilityService.getPassword(),
    "cart": cart,
  }
  console.log("Cart email: " + cartUpdateReq.email);
  console.log("Cart password: " + cartUpdateReq.password);
  console.log("cart cart items: " + cartUpdateReq.cart.listings.length);
  console.log(this.baseUrl);
  return this.httpClient.post<any>(this.baseUrl + "/saveCartToDatabase" , cartUpdateReq, httpOptions).pipe(catchError(this.handleError));
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
