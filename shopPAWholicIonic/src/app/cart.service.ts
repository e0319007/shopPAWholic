import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { HttpClient, HttpResponse, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { UtilityService } from './utility.service';
import { catchError } from 'rxjs/operators';
import { Cart } from './cart';

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

  retrieveCartByCustomerId(): Observable<any> {
   return this.httpClient.get<any>(this.baseUrl + "/retrieveCart?email=" + 
   this.utilityService.getEmail() + "&password=" + this.utilityService.getPassword).pipe(
     catchError(this.handleError)
   );
 }
  
 saveCartToDatabase(cart: Cart): Observable<any> {
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
