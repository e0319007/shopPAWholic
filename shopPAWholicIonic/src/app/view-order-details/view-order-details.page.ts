import { Component, OnInit } from '@angular/core';
import { OrderEntity } from '../order-entity';
import { OrderEntityService } from '../order-entity.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Listing } from '../listing';
import { UtilityService } from '../utility.service';
import { OrderStatus } from '../order-status.enum';

@Component({
  selector: 'app-view-order-details',
  templateUrl: './view-order-details.page.html',
  styleUrls: ['./view-order-details.page.scss'],
})
export class ViewOrderDetailsPage implements OnInit {

  orderId: number;
  orders: OrderEntity[];
  orderToView: OrderEntity;

  retrieveOrderError: boolean;
	error: boolean;
	errorMessage: string;
  resultSuccess: boolean;
  listingsBought: Listing[];
  isCustomer: boolean;
  cancelSuccessMessage: string;
  cancelled: boolean;

  constructor(private orderService: OrderEntityService,
              private activatedRoute: ActivatedRoute,
              private utilityService: UtilityService,
              private router: Router) 
  {
    this.retrieveOrderError = true;
		this.error = false;
    this.resultSuccess = false;
    this.isCustomer = this.utilityService.isCustomer();
    this.cancelSuccessMessage = null;
    this.cancelled = false;
  }

  ngOnInit() {
    this.isCustomer = this.utilityService.isCustomer();
    console.log("view order details nginit********");
    this.orderId = parseInt(this.activatedRoute.snapshot.paramMap.get('orderId'));
    console.log("*******viewing order with id: " + this.orderId);
    this.refreshOrder();
  }

  ionViewWillEnter(){
   this.refreshOrder();
  }

  refreshOrder() {
    this.orderService.retrieveAllOrderByUser().subscribe(
      response => {
        this.orders = response.orders;
        console.log("getting orders with size: " + this.orders.length);
        this.retrieveOrderError = true;
        for (let order of this.orders) {
          if (order.orderId == this.orderId) {
            this.orderToView = order;
            this.retrieveOrderError = false;
            break;
          }
        }
        console.log("order has " + this.orderToView.listings.length + " listings");
        this.listingsBought = this.orderToView.listings;
        if (this.orderToView.orderStatus == OrderStatus.cancelled) {
          this.cancelled = true;
          console.log("is this order cancelled? " + this.cancelled);
        }
        
      },
      error => {
        this.retrieveOrderError = true;
        console.log('********** ViewAllOrdersPage.ts: ' + error)
      }
    );
  }

  viewBillingDetails() {
    this.router.navigate(["/viewBillingDetails/" + this.orderId]);
  }

  back() {
    this.router.navigate(["/viewAllOrders"]);
  }

  viewDeliveryDetails() {
    this.router.navigate(["/viewDeliveryDetails/" + this.orderId]);
  }

  cancellOrder() {
    this.orderToView.orderStatus = OrderStatus.cancelled;
    this.orderService.changeOrderStatusByCustomer(this.orderToView).subscribe(
      response => {
        this.cancelSuccessMessage = "Order cancelled successfully!"
        this.cancelled = true;
      },
      error => {
        this.cancelSuccessMessage = null;
        console.log("Error while canceling : " + error);
      }
    )
  }

  changeOrderStatus() {
    this.router.navigate(["sellerOperation/changeOrderStatus/" + this.orderId]);
  }


}
