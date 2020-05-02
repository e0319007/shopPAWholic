import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { OrderEntity } from 'src/app/order-entity';
import { OrderEntityService } from 'src/app/order-entity.service';
import { BillingDetail } from 'src/app/billing-detail';

@Component({
  selector: 'app-view-billing-details',
  templateUrl: './view-billing-details.page.html',
  styleUrls: ['./view-billing-details.page.scss'],
})
export class ViewBillingDetailsPage implements OnInit {

  constructor(private router: Router,
              private orderService: OrderEntityService,
              private activatedRoute: ActivatedRoute) {
                this.orderId = parseInt(this.activatedRoute.snapshot.paramMap.get('orderId'));
              }
  orderId: number;
  orders: OrderEntity[];
  orderToView: OrderEntity;
  billingDetail: BillingDetail;
  billingDetailId: number;
  billingPrice: number;
  newCcNum: string;
  


  ngOnInit() {
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
        console.log("Billing date: " + this.orderToView.billingDetail.billingDate);
        console.log("Order found with id : " + this.orderToView.orderId);
        this.billingDetail = this.orderToView.billingDetail;
        this.billingDetailId = this.billingDetail.billingDetailId;
        console.log("BILLING DETAIL ID: " + this.billingDetailId);
        console.log("orginal ccnum: " + this.billingDetail.creditCardDetail);
        this.newCcNum = "**** **** **** " + this.billingDetail.creditCardDetail.substring(14);
        console.log(this.newCcNum);
        this.billingDetail.creditCardDetail = this.newCcNum;
        this.billingPrice = this.orderToView.totalPrice;
      },
      error => {
        console.log('********** ViewAllOrdersPage.ts: ' + error)
      }
    );
  }

  back() {
    this.router.navigate(["/viewOrderDetails/" + this.orderId]);
  }

  parseDate(d: Date) {
    return d.toString().replace('[UTC]','');
  }
  
}
