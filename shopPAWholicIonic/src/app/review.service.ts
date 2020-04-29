import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient, HttpErrorResponse } from '@angular/common/http';
import { UtilityService } from './utility.service';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Review } from './review';
import {Listing} from './listing';

const httpOptions = {
	headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class ReviewService {

  baseUrl: string;

  constructor(private httpClient: HttpClient, private utilityService: UtilityService) {
    this.baseUrl = utilityService.getRootPath() + 'Review';
  }

  retrieveAllReviewsByListingId(listingId: number) {
    return this.httpClient.get<any>(this.baseUrl + "/retrieveAllReviewsByListingId/" + listingId).pipe(
      catchError(this.handleError)
    );
  }

  createReview(review: Review, rating: number, listingId: number, description: string, reviewPictures: string[]){
    console.log("****review.service.ts: " + reviewPictures[0]);
    let createReviewReq = {
      "review": review,
      "rating": rating,
      "listingId": listingId,
      "email": this.utilityService.getEmail(),
      "password": this.utilityService.getPassword(),
      "description" : description,
      "reviewPictures" : reviewPictures,
    }
    return this.httpClient.put<any>(this.baseUrl, createReviewReq, httpOptions). pipe(catchError(this.handleError));
  }

  updateReview(review: Review, listing: Listing, description: string){
    let updateReviewReq = {
      "review": review,
      "listingId": listing.listingId,
      "email": this.utilityService.getEmail,
      "password": this.utilityService.getPassword,
      "description" : description,
    }
    return this.httpClient.put<any>(this.baseUrl, updateReviewReq, httpOptions). pipe(catchError(this.handleError));
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
