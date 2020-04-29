import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient, HttpErrorResponse } from '@angular/common/http';
import { UtilityService } from './utility.service';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Advertisement } from './advertisement';

const httpOptions = {
	headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class AdvertisementService {

  baseUrl: string;

  constructor(private httpClient: HttpClient, private utilityService: UtilityService) {
    this.baseUrl = utilityService.getRootPath() + 'Advertisement';
  }

  retrieveAllAdvertisements(): Observable<any> {
    let O: Observable<any> = this.httpClient.get<any>(this.baseUrl + "/retrieveAllAdvertisements").pipe(catchError(this.handleError));
    console.log(O);
    return O;
  }

  retrieveAdvertisementById(advertisementId: string) : Observable<any> {
    return this.httpClient.get<any>(this.baseUrl + "/retrieveListing/" + advertisementId + "?email:" 
    + this.utilityService.getEmail + "&password:" + this.utilityService.getPassword).pipe(catchError(this.handleError));
  }

  createAdvertisement(advertisement: Advertisement, startDate: Date, startYear: number, startMth: number, startDay: number,
                      endDate: Date, endYear: number, endMth: number, endDay: number, ccNum: string, 
                      picture: string, url: string, description: string, price: number): Observable<any> {
      console.log(advertisement.picture);
      let advertisementCreateReq = {
          "advertisement": advertisement,
          "email": this.utilityService.getEmail(),
          "password": this.utilityService.getPassword(),
          "startDate" : startDate,
          "startYear" : startYear,
          "startMth" : startMth,
          "startDay" : startDay,
          "endDate" : endDate,
          "endYear" : endYear,
          "endMth" : endMth,
          "endDay" : endDay,
          "ccNum" : ccNum,
          "pictures" : picture,
          "url" : url,
          "description" : description,
          "price" : price,
      }
      return this.httpClient.put<any>(this.baseUrl, advertisementCreateReq, httpOptions).pipe(catchError(this.handleError));
  }

  updateAdvertisement(advertisement: Advertisement, ccNum: string, startDate: Date, endDate: Date): Observable<any> {
    let advertisementUpdateReq = {
       "advertisement": advertisement,
        "email": this.utilityService.getEmail,
        "password": this.utilityService.getPassword,
        "ccNum" : ccNum,
        "startDate": startDate,
        "endDate": endDate,
    }
    return this.httpClient.put<any>(this.baseUrl, advertisementUpdateReq, httpOptions). pipe(catchError(this.handleError));
}

  deleteAdvertisement(advertisementId: number): Observable<any> {
    return this.httpClient.delete<any>(this.baseUrl + "/" + advertisementId + "?email=" + 
    this.utilityService.getEmail() + "&password=" + this.utilityService.getPassword).pipe(catchError(this.handleError));
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
