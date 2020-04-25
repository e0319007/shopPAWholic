import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient, HttpErrorResponse } from '@angular/common/http';
import { UtilityService } from './utility.service';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Seller } from './seller';

const httpOptions = {
	headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class SellerService {

  baseUrl: string;

  constructor(private httpClient: HttpClient, private utilityService: UtilityService) {
    this.baseUrl = utilityService.getRootPath() + 'Seller';
  }

  //sellerRegister
  sellerRegister(seller: Seller, name: string, email: string, password: string, contactNumber: string, accCreatedDate: Date) {
    let sellerCreateNewReq = {
      "seller" : seller,
      "name" : name,
      "email" : email,
      "password" : password,
      "contactNumber" : contactNumber,
      "accCreatedDate" : accCreatedDate,
    }
    return this.httpClient.put<any>(this.baseUrl, sellerCreateNewReq, httpOptions).pipe(catchError(this.handleError));
  }
    
  handleError(error: HttpErrorResponse) {
    let errorMessage: string;
    if(error.error instanceof ErrorEvent) {
      errorMessage = "An unknown error occured: " + error.error.message;
    } else {
      errorMessage = "A HTTP error occured: " + `HTTP ${error.status}: ${error.error.message}`;
    }
    console.error(errorMessage);
    return throwError(errorMessage);
  }
	
}
