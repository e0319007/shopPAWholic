import { Component, OnInit } from '@angular/core';
import { OrderStatus } from 'src/app/order-status.enum';
import { Router, ActivatedRoute } from '@angular/router';
import { OrderEntityService } from 'src/app/order-entity.service';
import { OrderEntity } from 'src/app/order-entity';

@Component({
  selector: 'app-change-order-status',
  templateUrl: './change-order-status.page.html',
  styleUrls: ['./change-order-status.page.scss'],
})
export class ChangeOrderStatusPage implements OnInit {


  orderStatusList: OrderStatus[] = new Array();
  status: OrderStatus;
  orderId: number;
  orders: OrderEntity[];
  orderToView: OrderEntity;
  errorMessage: string;
  statusString: string;

  constructor(private router: Router,
              private orderService: OrderEntityService,
              private activatedRoute: ActivatedRoute) {
                this.orderId = parseInt(this.activatedRoute.snapshot.paramMap.get('orderId'));
              }

  ngOnInit() {
    this.orderStatusList.push(OrderStatus.cancelled);
    this.orderStatusList.push(OrderStatus.completed);
    this.orderStatusList.push(OrderStatus.lost);
    this.orderStatusList.push(OrderStatus.paid);
    this.orderStatusList.push(OrderStatus.pendPayment);
    this.orderStatusList.push(OrderStatus.cancelled);
    this.orderStatusList.push(OrderStatus.refund);
    this.orderStatusList.push(OrderStatus.delivered);

    this.orderService.retrieveAllOrderByUser().subscribe(
      response => {
        this.orders = response.orders;
        console.log("****ONINIT of billing details");
        for (let order of this.orders) {
          if (order.orderId == this.orderId) {
            this.orderToView = order;
            break;
          }
        }
        this.status = this.orderToView.orderStatus;
      },
      error => {
        console.log('********** ViewAllOrdersPage.ts: ' + error)
      }
    );
  }

  translateOption() {
    if (this.statusString == "PENDINGPAYMENT") this.status = OrderStatus.pendPayment;
    else if (this.statusString == "PAID")this.status = OrderStatus.paid;
    else if (this.statusString == "CANCELLED")this.status = OrderStatus.cancelled;
    else if (this.statusString == "COMPLETED")this.status = OrderStatus.completed;
    else if (this.statusString == "DELIVERED")this.status = OrderStatus.delivered;
    else if (this.statusString == "REFUNDED")this.status = OrderStatus.refund;
    else if (this.statusString == "LOST")this.status = OrderStatus.lost;

  }

  submit() {
    console.log("status string : " + this.statusString);
    this.translateOption();
    this.orderToView.orderStatus = this.status;
    console.log("New order status: " + this.orderToView.orderStatus);
    
    this.orderService.changeOrderStatusBySeller(this.orderToView).subscribe(
      response => {
        this.back();
      },
      error => {
        this.errorMessage = "Update unsuccessful..."
        console.log("Error while changing status : " + error);
      }
    )
  }

  back() {
    this.router.navigate(["/viewOrderDetails/" + this.orderId]);
  }

}
