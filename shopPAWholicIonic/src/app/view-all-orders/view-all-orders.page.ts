import { Component, OnInit } from '@angular/core';
import { OrderEntity } from '../order-entity';
import { OrderEntityService } from '../order-entity.service';
import { Router } from '@angular/router';
import { UtilityService } from '../utility.service';
import { DatePipe } from '@angular/common';
import { OrderDisplay } from '../order-display';

@Component({
  selector: 'app-view-all-orders',
  templateUrl: './view-all-orders.page.html',
  styleUrls: ['./view-all-orders.page.scss'],
  providers: [DatePipe],
})
export class ViewAllOrdersPage implements OnInit {

  constructor(private orderService: OrderEntityService, 
              private router: Router, 
              private utilityService: UtilityService,
              private datePipe: DatePipe) { }

  orders: OrderEntity[];
  hasOrders: boolean = true;

  
  ngOnInit() {
    console.log("ng on init**********");
    this.refreshOrders();
  }

  viewOrderDetails(event, order)
	{
		this.router.navigate(["viewOrderDetails/" + order.orderId]);
  }
  
  ionViewWillEnter() {
    console.log("ion view will enter ***********")
    this.refreshOrders();
  }

  refreshOrders() {
    console.log("refreshing orders")
    this.orderService.retrieveAllOrderByUser().subscribe(
      response => {
        this.orders = response.orders;
        console.log("getting orders with size: " + this.orders.length);
        this.hasOrders = true;
       // for (let order of this.orders) {
       //   let dateString: string = order.orderDate.toUTCString();
       //   this.ordersDisplay.push(new OrderDisplay(order, dateString));
      //}
      },
      error => {
        console.log('********** ViewAllOrdersPage.ts: ' + error);
        this.hasOrders = false;
      }
    );
  }

  parseDate(d: Date) {
    return d.toString().replace('[UTC]','');
  }
  

}
