import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient, HttpErrorResponse } from '@angular/common/http';
import { UtilityService } from './utility.service';
import { throwError, Observable } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { DeliveryDetail } from './delivery-detail';

const httpOptions = {
	headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class DeliveryDetailService {

  baseUrl: string;

  constructor(private httpClient: HttpClient, private utilityService: UtilityService ) {
    this.baseUrl = this.utilityService.getRootPath() + 'DeliveryDetail';
  }

  createDeliveryDetail(deliveryDetail: DeliveryDetail): Observable<any> {
    console.log("creating delivery detail")
    let deliveryDetailCreateNewReq = {
      "deliveryDetail": deliveryDetail,
      "email": this.utilityService.getEmail(),
      "password": this.utilityService.getPassword(), 
    }
    return this.httpClient.put<any>(this.baseUrl, deliveryDetailCreateNewReq, httpOptions).pipe(catchError(this.handleError));
  }

  updateDeliveryDetail(deliveryDetail: DeliveryDetail, status: string): Observable<any> {
    console.log("status list to pass back: " + deliveryDetail.statusLists)
    console.log("updating delivery detail")
    let deliveryDetailUpdateReq = {
      "deliveryDetail": deliveryDetail,
      "email": this.utilityService.getEmail(),
      "password": this.utilityService.getPassword(),
      "statusToAdd": status,
    }
    return this.httpClient.post<any>(this.baseUrl, deliveryDetailUpdateReq, httpOptions).pipe(catchError(this.handleError));
  }
  
  retrieveDeliveryDetailByOrderId(orderId: number): Observable<any>{
    console.log("====================");
    console.log("calling retrieve delivery detail by order id ");
    return this.httpClient.get<any>(this.baseUrl + "/retrieveDeliveryDetail/" + orderId + "?email=" + 
    this.utilityService.getEmail() + "&password=" + this.utilityService.getPassword()).pipe(
      catchError(this.handleError)
    );
  }

  private handleError(error: HttpErrorResponse) {
    let errorMessage: string = "";
    if (error.error instanceof ErrorEvent) {
      errorMessage = "An unknown error has occured: " + error.error.message;
    } else {
      errorMessage = "A HTTP error has occured: " + `HTTP ${error.status}: ${error.error.message}`
    }
    console.error(errorMessage);
    return throwError(errorMessage);
  } 
}
