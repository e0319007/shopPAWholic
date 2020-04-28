import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient, HttpErrorResponse } from '@angular/common/http';
import { UtilityService } from './utility.service';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Listing } from './listing';

const httpOptions = {
	headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class ListingService {

  baseUrl: string;

  constructor(private httpClient: HttpClient, private utilityService: UtilityService) {
    this.baseUrl = utilityService.getRootPath() + 'Listing';
    console.log("Listing base url: " + this.baseUrl)
  }

  retrieveAllListings(): Observable<any> {
    return this.httpClient.get<any>(this.baseUrl + "/retrieveAllListings").pipe(catchError(this.handleError));
  }

  retrieveListingById(listingId: number): Observable<any> {
    return this.httpClient.get<any>(this.baseUrl + "/retrieveListing/" + listingId).pipe(catchError(this.handleError));
  }

  createListing(listing: Listing, categoryId: number, tagIds: number[]): Observable<any> {
    let listingCreateReq = {
      "email": this.utilityService.getEmail(),
      "password": this.utilityService.getPassword(),
      "listing": listing,
      "categoryId": categoryId,
      "tagIds": tagIds,
    }
    return this.httpClient.put<any>(this.baseUrl, listingCreateReq, httpOptions).pipe(catchError(this.handleError));
  }

  updateListing(listing: Listing, categoryId: number, tagIds: number[]): Observable<any> {
    let listingUpdateReq = {
      "email": this.utilityService.getEmail(),
      "password": this.utilityService.getPassword(),
      "listing": listing,
      "categoryId": categoryId,
      "tagIds": tagIds,
    }
    return this.httpClient.post<any>(this.baseUrl, listingUpdateReq, httpOptions).pipe(catchError(this.handleError));
  }

  deleteListing(listingId: number): Observable<any> {
    return this.httpClient.delete<any>(this.baseUrl + "/" + listingId + "?email=" + 
    this.utilityService.getEmail() + "&password=" + this.utilityService.getPassword()).pipe(
      catchError(this.handleError)
    );
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
