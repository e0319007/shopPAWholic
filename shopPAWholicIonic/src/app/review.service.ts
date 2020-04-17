import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient, HttpErrorResponse } from '@angular/common/http';
import { UtilityService } from './utility.service';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Review } from './review';

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

  //retrieveReviewByListingId

  createReview(review: Review, reviewDate: Date, listingId: number){
    let createReviewReq = {
      "review": review,
      "listingId": listingId,
      "email": this.utilityService.getEmail,
      "password": this.utilityService.getPassword,
      "reviewDate": reviewDate,
    }
    return this.httpClient.put<any>(this.baseUrl, createReviewReq, httpOptions). pipe(catchError(this.handleError));
  }

  updateReview(review: Review, reviewDate: Date, listingId: number){
    let updateReviewReq = {
      "review": review,
      "listingId": listingId,
      "email": this.utilityService.getEmail,
      "password": this.utilityService.getPassword,
      "reviewDate": reviewDate,
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
