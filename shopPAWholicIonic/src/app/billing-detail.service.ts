import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient, HttpErrorResponse } from '@angular/common/http';
import { UtilityService } from './utility.service';

import { catchError } from 'rxjs/operators';
import { Observable, throwError } from 'rxjs';

const httpOptions = {
	headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class BillingDetailService {

  baseUrl: string;

  constructor(private httpClient: HttpClient, private utilityService: UtilityService) {
    this.baseUrl = this.utilityService.getRootPath() + 'BillingDetail';
   }

   retrieveAllCustomerBillingDetails() : Observable<any> {
     return this.httpClient.get<any>(this.baseUrl + "/retrieveAllCustomerBillingDetails?email=" + 
     this.utilityService.getEmail() + "&password=" + this.utilityService.getPassword).pipe(
       catchError(this.handleError)
     );
   }

   retrieveAllSellerBillingDetails() : Observable<any> {
     return this.httpClient.get<any>(this.baseUrl + "/retrieveAllSellerBillingDetails?email=" + 
     this.utilityService.getEmail() + "&password=" + this.utilityService.getPassword).pipe(
       catchError(this.handleError)
     );
   }

   private handleError (error:HttpErrorResponse) {
     let errorMessage: string = "";
     if(error.error instanceof ErrorEvent) {
       errorMessage = "An unknown error has occured: " + error.error.message;
     } else {
       errorMessage = "A HTTP error has occured: " + `HTTP ${error.status}: ${error.error.message}`;
     }
     console.error(errorMessage);
     return throwError(errorMessage);
   }
}
