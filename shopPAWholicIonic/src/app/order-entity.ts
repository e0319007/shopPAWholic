import { OrderStatus } from './order-status.enum';
import { Customer } from './customer';
import { Seller } from './seller';
import { Listing } from './listing';
import { BillingDetail } from './billing-detail';

export class OrderEntity {
    orderId: number;
    totalPrice: number;
    orderDate: Date;
    orderStatus: OrderStatus;
    customer: Customer;
    seller: Seller;
    listing: Listing[];
    billingDetail: BillingDetail;

    constructor(orderId?: number, totalPrice?: number, orderDate?: Date) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
    }
    

}
