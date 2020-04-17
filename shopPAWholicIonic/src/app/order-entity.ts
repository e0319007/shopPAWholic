import { OrderStatus } from './order-status.enum';
import { Customer } from './customer';
import { Seller } from './seller';
import { Listing } from './listing';
import { BillingDetail } from './billing-detail';
import { DeliveryDetail } from './delivery-detail';

export class OrderEntity {
    orderId: number;
    totalPrice: number;
    orderDate: Date;
    orderStatus: OrderStatus;
    customer: Customer;
    seller: Seller;
    listings: Listing[];
    billingDetail: BillingDetail;
    deliveryDetail: DeliveryDetail;

    constructor(orderId?: number, totalPrice?: number, orderDate?: Date) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
        this.orderStatus = OrderStatus.paid;
    }
    

}
