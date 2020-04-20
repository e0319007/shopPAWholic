import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { OrderEntityService } from '../order-entity.service';
import { OrderEntity } from '../order-entity';
import { DeliveryDetail } from '../delivery-detail';
import { DeliveryDetailService } from '../delivery-detail.service';

@Component({
  selector: 'app-view-delivery-details',
  templateUrl: './view-delivery-details.page.html',
  styleUrls: ['./view-delivery-details.page.scss'],
})
export class ViewDeliveryDetailsPage implements OnInit {

  constructor(private router: Router,
    private orderService: OrderEntityService,
    private activatedRoute: ActivatedRoute,
    private deliveryService: DeliveryDetailService) {
      this.orderId = parseInt(this.activatedRoute.snapshot.paramMap.get('orderId'));
      console.log("this order brought over is: " + this.orderId);
      this.hasStatus = true;
    }
    
  orderId: number;
  orders: OrderEntity[];
  orderToView: OrderEntity;
  deliveryDetail: DeliveryDetail;
  deliveryDetailId: number;
  hasStatus : boolean;
  newDeliveryStatus : string;
  list: string[] = new Array();

  ngOnInit() {
    this.newDeliveryStatus = "";
    console.log("==================")
    this.deliveryService.retrieveDeliveryDetailByOrderId(this.orderId).subscribe(
      response => {
        console.log("getting delivery detail with response");
        this.deliveryDetail = response.deliveryDetail;
        this.deliveryDetailId = this.deliveryDetail.deliveryDetailId;
        console.log("delivery detail response id: " + this.deliveryDetail.deliveryDetailId);
        console.log("delivery detail with status length: " + this.deliveryDetail.statusLists.length);
        this.list = this.deliveryDetail.statusLists;
        if (this.list == null || this.list.length == 0) {
          this.hasStatus = false;
        } else {
          console.log("delivery detail with status length: " + this.deliveryDetail.statusLists.length);
          this.hasStatus = true;
        }
      },
      error => {
        console.log('********** ViewAllOrdersPage.ts: ' + error)
      }
    )
    /**this.orderService.retrieveAllOrderByUser().subscribe(
      response => {
        this.orders = response.orders;
        console.log("getting orders with size: " + this.orders.length);
        for (let order of this.orders) {
          if (order.orderId == this.orderId) {
            this.orderToView = order;
            break;
          }
        }
        console.log("this delivery detail belongs to order id: " + this.orderId)
        this.deliveryDetail = this.orderToView.deliveryDetail;
        this.deliveryDetailId = this.deliveryDetail.deliveryDetailId;
        let list: string[] = new Array();
        list = this.deliveryDetail.statusList;
        if (list == null || list.length == 0) {
          this.hasStatus = false;
        }
      },
      error => {
        console.log('********** ViewAllOrdersPage.ts: ' + error)
      }
    );**/
  }

  addStatus() {
    console.log("==================")
    console.log("adding status");
    console.log("adding " + this.newDeliveryStatus + " into list");
    this.deliveryService.updateDeliveryDetail(this.deliveryDetail, this.newDeliveryStatus).subscribe(
      response => {
        this.ngOnInit();
      },
      error => {
        console.log("error experienced while adding status for deliver: " + error);
      }
    )
  }

  back() {
    this.router.navigate(["/viewOrderDetails/" + this.orderId]);
  }
}
