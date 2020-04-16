import { Component, OnInit } from '@angular/core';
import { OrderEntity } from '../order-entity';
import { OrderEntityService } from '../order-entity.service';
import { Router } from '@angular/router';
import { UtilityService } from '../utility.service';

@Component({
  selector: 'app-view-all-orders',
  templateUrl: './view-all-orders.page.html',
  styleUrls: ['./view-all-orders.page.scss'],
})
export class ViewAllOrdersPage implements OnInit {

  constructor(private orderService: OrderEntityService, private router: Router, private utilityService: UtilityService) { }

  orders: OrderEntity[];
  
  ngOnInit() {
    this.refreshOrders;
  }

  viewOrderDetails(event, order)
	{
		this.router.navigate(["viewOrderDetail/" + order.orderId]);
  }
  
  ionViewWillEnter() {
    this.refreshOrders();
  }

  refreshOrders() {
    console.log("refreshing orders")
    this.orderService.retrieveAllOrderByUser().subscribe(
      response => {
        console.log("getting orders with size: " );

        this.orders = response.orders;
      },
      error => {
        console.log('********** ViewAllOrdersPage.ts: ' + error)
      }
    )
  }

}
