import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient, HttpErrorResponse } from '@angular/common/http';
import { UtilityService } from './utility.service';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { OrderEntity } from './order-entity';
import { Seller } from './seller';
import { Listing } from './listing';

const httpOptions = {
	headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class OrderEntityService {

  baseUrl: string;
  constructor(private httpclient: HttpClient, private utilityService: UtilityService) {
    this.baseUrl = utilityService.getRootPath() + 'Order';
  }

  retrieveAllOrderByUser():Observable<any> {
    let email: string = this.utilityService.getEmail();
    console.log("retrieving in service with email: " + email)
    let url: string = this.baseUrl + "?email=" + this.utilityService.getEmail() + "&password=" + this.utilityService.getPassword();
    console.log(url);
    return this.httpclient.get<any>(url).pipe(
      catchError(this.handleError)
      );
  }

  createNewOrder(order: OrderEntity, deliveryDetailId: number, ccNum: string, seller: Seller, listings: Listing[]): Observable<any> {
    let orderCreateNewReq = {
      "deliveryDetailId": deliveryDetailId,
      "ccNum": ccNum,
      "orderEntity": order,
      "seller": seller,
      "listings": listings,
      "email": this.utilityService.getEmail(),
      "password": this.utilityService.getPassword(),
    } 
    return this.httpclient.put<any>(this.baseUrl, orderCreateNewReq, httpOptions).pipe(catchError(this.handleError));
  }

  changeOrderStatusByCustomer(order: OrderEntity): Observable<any> {
    let orderUpdateOrderReq = {
      "order": order,
      "email": this.utilityService.getEmail(),
      "password": this.utilityService.getPassword(),
    }
    return this.httpclient.post<any>(this.baseUrl + "/changeOrderStatusByCustomer", orderUpdateOrderReq, httpOptions).pipe(catchError(this.handleError));
  } 

  changeOrderStatusBySeller(order: OrderEntity): Observable<any> {
    let orderUpdateOrderReq = {
      "order": order,
      "email": this.utilityService.getEmail(),
      "password": this.utilityService.getPassword(),
    }
    return this.httpclient.post<any>(this.baseUrl + "/changeOrderStatusBySeller", orderUpdateOrderReq, httpOptions).pipe(catchError(this.handleError));
  } 

  updateOrderDeliveryStatus(order: OrderEntity): Observable<any> {
    let orderUpdateOrderReq = {
      "order": order,
      "email": this.utilityService.getEmail(),
      "password": this.utilityService.getPassword(),
    }
    console.log("***in orderservice: delivery status: " + order.deliveryDetail.statusLists )
    return this.httpclient.post<any>(this.baseUrl + "/updateDeliveryStatusOfOrder", orderUpdateOrderReq, httpOptions).pipe(catchError(this.handleError));
  }

  private handleError(error: HttpErrorResponse) {
    let errorMessage: string;
    if (error.error instanceof ErrorEvent) {
      errorMessage = "An unknown error occured: " + error.error.message;
    } else {
      errorMessage = "A HTTP error occured: " + `HTTP ${error.status}: ${error.error.message}`;
    }
    console.error(errorMessage);
    return throwError(errorMessage);
  }
}
